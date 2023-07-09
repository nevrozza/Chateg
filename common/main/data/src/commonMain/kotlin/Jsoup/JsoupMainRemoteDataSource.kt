package Jsoup

import AuthRepository
import ChatsListComponent
import di.Inject
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import settings.SettingsMainDataSource


class JsoupMainRemoteDataSource(
    private val authRepository: AuthRepository,
    private val cacheDataSource: SettingsMainDataSource
) {
    var inboxDoc: Document = Document("")
    private var cookies: Map<String, String> = authRepository.fetchCookies()

    suspend fun performChats(): Map<String, String> {
        cookies = authRepository.fetchCookies()

        inboxDoc = Jsoup.connect("https://club.chateg.club/inbox/?p=1")
            .cookies(cookies)
            .get()
        return performChatsWithoutParcing()
    }

    suspend fun clearChats() {
        val doc = Jsoup.connect("http://club.chateg.club/confirm/?del=1&m=i&act=con")
            .cookies(cookies)
            .get()

    }

    suspend fun sendMessage(request: JsoupMessageSendRequest) {
        val res = Jsoup.connect("http://club.chateg.club/readInbox/add/?uid=${request.id}")
            .cookies(cookies)
            .data(
                "answer",
                request.answer,
                "subj",
                request.subj,
                "do",
                request.id,
                "doAnswer",
                request.doAnswer
            )
            .method(Connection.Method.POST)
            .execute()

    }

    fun performChatsWithoutParcing(): Map<String, String> {
        var incideInboxDoc = inboxDoc
        var lastIncideBox: Document
        var numOfIterations = 0
        val mapOfNickTime = mutableMapOf<String, String>()
        val offlineNickTime = cacheDataSource.fetchGotMessages()
        var allMessagesCount = 0
        while (if(offlineNickTime.isNotEmpty()) allMessagesCount != 1  else true) {
            numOfIterations++
            if (numOfIterations != 1) {
                lastIncideBox = incideInboxDoc
                incideInboxDoc =
                    Jsoup.connect("https://club.chateg.club/inbox/?p=$numOfIterations")
                        .cookies(cookies)
                        .get()
                if(incideInboxDoc.text() == lastIncideBox.text()) break
            }


            for (i in incideInboxDoc.select("td")) { //a[href^=/friend/?uid=]
                val nick = i.select("a[href^=/friend/?uid=]").text()
                if(nick!="" && !mapOfNickTime.containsKey(nick)) {
                    val time = i.text().replace(nick, "").replace(" ", "").replace("\n", "")
                    allMessagesCount += 1
                    mapOfNickTime[nick] = time
                }
            }
            if (numOfIterations * 10 > allMessagesCount) break
        }
        return mapOfNickTime
    }



    suspend fun getMessages(id: String, nick: String): List<ChatComponent.Message> {
        val messages = mutableListOf<ChatComponent.Message>()
        var doc: Document =
            Jsoup.connect("http://club.chateg.club/readInbox/?uid=$id&p=1")
                .cookies(cookies)
                .get()
        var previousDoc: Document
        var numOfIterations = 0
        while (numOfIterations != 4) {
            numOfIterations++
            if (numOfIterations != 1) {
                previousDoc = doc
                doc =
                    Jsoup.connect("http://club.chateg.club/readInbox/?uid=$id&p=$numOfIterations")
                        .cookies(cookies)
                        .get()
                if(doc.text() == previousDoc.text()) break
            }

            val dialogElements = doc.select("div.mail_reading, div.headline2")


            for (element in dialogElements) {
                val rawMessage = element.selectFirst("div")

                val cutedForTimeMessage = rawMessage.toString().replace(
                    "<div class=\"headline2\">\n" +
                            "  ", ""
                ).replace(
                    "<div class=\"mail_reading\">\n" +
                            "  ", ""
                ).split(
                    "\n" +
                            " <br>"
                )
                val time = cutedForTimeMessage.first()
                val cutedForNameMessageMessage = cutedForTimeMessage[1].replace(
                    "<span class=\"dialog_friend\"> <a href=\"/friend/?",
                    ""
                ).split("\">")[1].split("</a>: </span> ")
                val username = cutedForNameMessageMessage.first()
                val text = cutedForNameMessageMessage.last().replace(
                    " \n" +
                            "</div>", ""
                )
                messages += ChatComponent.Message(
                    time = time,
                    isMine = username == nick,
                    text = text
                )
            }
            if (numOfIterations * 10 > messages.count()) break
        }
        return messages
    }

    fun getId(nick: String): String {
        var incideInboxDoc = inboxDoc
        var numOfIterations = 0
        while (true) {
            numOfIterations++
            if (numOfIterations != 1) incideInboxDoc =
                Jsoup.connect("https://club.chateg.club/inbox/?p=$numOfIterations")
                    .cookies(cookies)
                    .get()
            if (incideInboxDoc.toString().contains(nick)) {

                val links: List<Element> = incideInboxDoc.select("a[href*=/friend/?uid=]") // Ищем ссылки, содержащие "/friend/?uid="

                for (link in links) {
                    val href = link.attr("href") // Получаем значение атрибута href
                    val id = href.substringAfterLast("=") // Получаем айди из значения href
                    val linkText = link.text() // Получаем текст ссылки

                    if (linkText == nick) {
                        return id
                    }
                }
            } else {
                numOfIterations++
            }
        }
    }
}