import ChatComponent.Model
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnResume
import com.arkivanov.essenty.lifecycle.doOnStop
import di.Inject
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class ChatComponentImpl(
    componentContext: ComponentContext,
    private val id: String,
    private val nick: String,
    private val delayMessage: Int,
    private val onTimerChange: (Int) -> Unit
) : ChatComponent, ComponentContext by componentContext {
    private val _models = MutableValue(Model(nick))
    override val model: Value<Model> = _models

    private val mainRepository: MainRepository = Inject.instance()

    init {
        lifecycle.doOnResume {
            mainRepository.saveIsInChat(nick)
        }
        lifecycle.doOnDestroy {
            mainRepository.saveIsInChat("")
        }

        if (delayMessage != 0) {
            GlobalScope.launch(Dispatchers.IO) {


                _models.value = _models.value.copy(timeout = delayMessage)
                while (componentContext.lifecycle.state != Lifecycle.State.DESTROYED) {
                    while (_models.value.timeout > 0) {
                        delay(1000)
                        _models.value = _models.value.copy(timeout = _models.value.timeout - 1)
                    }
                }
            }

        }
        val mTexts = mainRepository.fetchMTexts()
        for (i in mTexts) {
            if (i.key == nick) {
                _models.value = _models.value.copy(mText = i.value)
            }
        }
        GlobalScope.launch(Dispatchers.IO) {


            while (componentContext.lifecycle.state != Lifecycle.State.DESTROYED) {
                getMessages()
                delay(20000)
            }

        }
    }

    override suspend fun getMessages() {
        _models.value =
            _models.value.copy(messages = mainRepository.getMessages(id = id, nick = nick))
    }

    override fun sendMessage() {
        GlobalScope.launch(Dispatchers.IO) {
            mainRepository.sendMessage(id, _models.value.mText)
            onMessageTextChange("")
            _models.value = _models.value.copy(timeout = 55)
            getMessages()
            while (_models.value.timeout > 0) {
                delay(1000)
                _models.value = _models.value.copy(timeout = _models.value.timeout - 1)
                onTimerChange(_models.value.timeout)
            }
        }
    }

    override fun onMessageTextChange(text: String) {
        _models.value = _models.value.copy(mText = text)
        val mTexts = mainRepository.fetchMTexts().toMutableMap()
        mTexts[nick] = text
        mainRepository.saveMTexts(mTexts.toString())
    }

    override fun newDaySet(day: String) {
        _models.value = _models.value.copy(day = day)
    }
}

class PreviewChatComponent : ChatComponent {
    override val model: Value<Model> = MutableValue(Model("Igoryok"))
    override fun onMessageTextChange(text: String) {}
    override suspend fun getMessages() {}
    override fun sendMessage() {}

    override fun newDaySet(day: String) {}
}