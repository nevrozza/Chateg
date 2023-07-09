import ChatsListComponent.Model
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import di.Inject
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class ChatsListComponentImpl(
    componentContext: ComponentContext,
    private val onChat: (id: String, nick: String) -> Unit
) : ChatsListComponent, ComponentContext by componentContext {
    private val mainRepository: MainRepository = Inject.instance()
    private val _models = MutableValue(Model("Чаты"))
    override val model: Value<Model> = _models

    init {
        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                val appLifecycle = mainRepository.fetchLifecycle()
                val isInChat = mainRepository.fetchIsInChat()
                val times =
                    if (appLifecycle == Lifecycle.State.CREATED.name && isInChat != "") 30 else 60
                updateChats()
                delay((times * 1000).toLong())
            }
        }
    }

    override fun clearChats() {
        GlobalScope.launch(Dispatchers.IO) {
            _models.value = _models.value.copy(chats = mainRepository.clearChats())
        }
    }

    override fun onChatClicked(id: String, nick: String) {
        onChat(id, nick)
        GlobalScope.launch(Dispatchers.IO) {
            val onlineChats = _models.value.chats

            val updatedChats = mutableMapOf<String, String>()
            for (i in onlineChats) {
                println(id)
                println(i)
                if (id == i.id) {
                    updatedChats[i.nick] = i.onlineMessagesCount
                } else {
                    updatedChats[i.nick] = i.savedMessagesCount
                }
            }
            mainRepository.saveMessages(updatedChats.toString())

            _models.value =
                _models.value.copy(chats = mainRepository.fetchOnlineMessagesWithoutParcing())

        }
    }

    override fun updateChats() {
        GlobalScope.launch {
            val isInChat = mainRepository.fetchIsInChat()
            val gotChatsMap = mainRepository.fetchGotMessages() // {nevrozq=09.07 17:48 }
            val onlineChats = mainRepository.fetchOnlineMessages()
            val onlineChatsMap = mutableMapOf<String, String>()
            val updatedChats = mutableMapOf<String, String>()
            for (i in onlineChats) {
                if (isInChat == i.nick)
                    updatedChats[i.nick] = onlineChats[onlineChats.indexOf(i)].onlineMessagesCount
                else
                    updatedChats[i.nick] = onlineChats[onlineChats.indexOf(i)].savedMessagesCount
                onlineChatsMap[i.nick] = i.onlineMessagesCount
                if ((gotChatsMap[i.nick] ?: i.onlineMessagesCount) != i.onlineMessagesCount) {
                    val lifecycle = mainRepository.fetchLifecycle()
                    if (lifecycle != Lifecycle.State.RESUMED.name || isInChat != i.nick) {
                        kmmPrint("$isInChat ${i.nick}")
                        createNotification("Новое сообщение от ${i.nick}")
                    }
                }
            }
            mainRepository.saveGotMessages(onlineChatsMap.toString())
            mainRepository.saveMessages(updatedChats.toString())
            _models.value = _models.value.copy(chats = onlineChats)
        }
    }

}

class PreviewChatsListComponent : ChatsListComponent {
    override fun updateChats() {}
    override fun clearChats() {}
    override fun onChatClicked(id: String, nick: String) {}
    override val model: Value<Model> = MutableValue(Model("Вход"))
}