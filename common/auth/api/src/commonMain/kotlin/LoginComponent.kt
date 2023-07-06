import com.arkivanov.decompose.value.Value

interface LoginComponent {
    val model: Value<Model>

    fun onLoginClicked()

    fun onLoginChange(login: String)
    fun onPasswordChange(password: String)

    data class Model(
        val title: String = "",
        val login: String = "",
        val password: String = "",
    )

}