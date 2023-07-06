import ChatsListComponent.Model
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import di.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                updateChats()
                delay((_models.value.seconds * 1000).toLong())
            }
        }
    }

    override fun onChatClicked(id: String, nick: String) {
        onChat(id, nick)
        GlobalScope.launch(Dispatchers.IO) {
            val onlineChats = _models.value.chats
            val updatedChats = mutableMapOf<String, Int>()
            for (i in onlineChats) {
                if (id == i.id) {
                    updatedChats[i.nick] = onlineChats[onlineChats.indexOf(i)].onlineMessagesCount
                } else {
                    updatedChats[i.nick] = onlineChats[onlineChats.indexOf(i)].savedMessagesCount
                }
            }
            mainRepository.saveMessages(updatedChats.toString())
            _models.value =
                _models.value.copy(chats = mainRepository.fetchOnlineMessagesWithoutParcing())
        }
    }

    override fun updateChats() {
        GlobalScope.launch {
            val gotChatsMap = mainRepository.fetchGotMessages()
            val onlineChats = mainRepository.fetchOnlineMessages()
            val onlineChatsMap = mutableMapOf<String, Int>()
            for (i in onlineChats) {
                onlineChatsMap[i.nick] = i.onlineMessagesCount
                if((gotChatsMap[i.nick] ?: i.onlineMessagesCount) < i.onlineMessagesCount) {
                    println("sad")
                }
            }
            mainRepository.saveGotMessages(onlineChatsMap.toString())
            _models.value = _models.value.copy(chats = onlineChats)
        }
    }

}

class PreviewChatsListComponent : ChatsListComponent {
    override fun updateChats() {}
    override fun onChatClicked(id: String, nick: String) {}
    override val model: Value<Model> = MutableValue(Model("Вход"))
}