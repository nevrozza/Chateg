package Jsoup

data class JsoupMessageSendRequest(
    val answer: String,
    val id: String,
    val subj: String = "Без темы",
    val doAnswer: String = "ОК"
)
