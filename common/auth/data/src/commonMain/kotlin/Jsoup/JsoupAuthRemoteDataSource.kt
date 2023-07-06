package Jsoup

import org.jsoup.Connection
import org.jsoup.Jsoup

class JsoupAuthRemoteDataSource {
    suspend fun perjormChategLogin(request: JsoupChategLoginRequest): MutableMap<String, String> {
        val res = Jsoup.connect("http://club.chateg.club/index.php")
            .data("login", request.login, "pass", request.password, "action", request.action, "submit", request.submit)
            .method(Connection.Method.POST)
            .execute()
        val loginCookies = res.cookies()
        return loginCookies
    }
}