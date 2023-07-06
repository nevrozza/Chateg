import LoginComponent.Model
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import di.Inject
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

@DelicateCoroutinesApi
class LoginComponentImpl(
    componentContext: ComponentContext,
    private val onLogin: () -> Unit
): LoginComponent, ComponentContext by componentContext {

    private val _models = MutableValue(Model())
    override val model: Value<Model> = _models
    private val authRepository: AuthRepository = Inject.instance()
    override fun onLoginClicked() {
        _models.value = _models.value.copy(isRequestInProcess = true)
        GlobalScope.launch(Dispatchers.IO) {
            val cookies: Map<String, String> = authRepository.login(_models.value.login, _models.value.password)
            val doc: Document = Jsoup.connect("https://club.chateg.club/inbox/")
                .cookies(cookies)
                .get()
            val allMessages = mutableListOf<String>()
            val countedMessages = mutableMapOf<String, Int>()
            for (i in doc.select("a[href^=/friend/?uid=]")) {
                allMessages += i.text()
                countedMessages[i.text()] = allMessages.count {it == i.text()}
            }
            if(cookies.toString().length < 40) {
                _models.value = _models.value.copy(isError = true, isRequestInProcess = false)
            } else {
//                authRepository.saveName(_models.value.login)
                authRepository.saveCookies(cookies.toString())
                MainScope().launch {
                    onLogin()
                }

            }
        }



    }

    override fun onRegisterClicked() {
        _models.value = _models.value.copy(isWebViewOpened = true)
        backCallback.isEnabled = true
    }

    override fun onBackClicked() {
        _models.value = _models.value.copy(isWebViewOpened = false)
        backCallback.isEnabled = false
    }

    private val backCallback = BackCallback {
       onBackClicked()
    }
    init {
        backHandler.register(backCallback)
        backCallback.isEnabled = false


    }
    override fun onLoginChange(login: String) {
        _models.value = _models.value.copy(login = login, isError = false)
    }

    override fun onPasswordChange(password: String) {
        _models.value = _models.value.copy(password = password, isError = false)
    }



}

class PreviewLoginComponent: LoginComponent {
    override fun onLoginChange(login: String) {}
    override fun onPasswordChange(password: String) {}
    override fun onLoginClicked() {}
    override fun onRegisterClicked() {}
    override fun onBackClicked() {}
    override val model: Value<Model> = MutableValue(Model())
}