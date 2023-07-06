import com.arkivanov.decompose.value.Value

interface LoginComponent {
    val model: Value<Model>

    fun onLoginClicked()
    fun onRegisterClicked()
    fun onBackClicked()

    fun onLoginChange(login: String)
    fun onPasswordChange(password: String)

    data class Model(
        val title: String = "Chateg.ru",
        val login: String = "",
        val password: String = "",
        val isWebViewOpened: Boolean = false,
        val isRequestInProcess: Boolean = false,
        val isError: Boolean = false
    )

}