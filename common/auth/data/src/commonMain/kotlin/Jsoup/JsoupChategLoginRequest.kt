package Jsoup

data class JsoupChategLoginRequest(
    val login: String,
    val password: String,
    val action: String = "login",
    val submit: String = "Вход"
)
