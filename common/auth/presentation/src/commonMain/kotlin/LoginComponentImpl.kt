import LoginComponent.Model
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class LoginComponentImpl(
    componentContext: ComponentContext,
    private val onLogin: () -> Unit
): LoginComponent, ComponentContext by componentContext {

    private val _models = MutableValue(Model("Вход"))
    override val model: Value<Model> = _models

    override fun onLoginClicked() {
        onLogin()
    }

    override fun onLoginChange(login: String) {
        _models.value = _models.value.copy(login = login)
    }

    override fun onPasswordChange(password: String) {
        _models.value = _models.value.copy(password = password)
    }



}

class PreviewLoginComponent: LoginComponent {
    override fun onLoginChange(login: String) {}

    override fun onPasswordChange(password: String) {}
    override fun onLoginClicked() {}
    override val model: Value<Model> = MutableValue(Model("Вход"))
}