import Jsoup.JsoupMainRemoteDataSource
import Jsoup.JsoupMessageSendRequest
import settings.SettingsMainDataSource

class MainRepositoryImpl(
    private val remoteDataSource: JsoupMainRemoteDataSource,
    private val cacheDataSource: SettingsMainDataSource
): MainRepository {
    override suspend fun fetchOnlineMessages(): List<ChatsListComponent.Chat> {
        val onlineMap = remoteDataSource.performChats()
        val savedMap = cacheDataSource.fetchSavedMessages()
        val chats = mutableListOf<ChatsListComponent.Chat>()
        for (i in onlineMap) {
            chats += ChatsListComponent.Chat(nick = i.key, onlineMessagesCount = i.value, savedMessagesCount = savedMap[i.key] ?: 0, id = remoteDataSource.getId(i.key))
        }
        return chats
    }

    override fun fetchOnlineMessagesWithoutParcing(): List<ChatsListComponent.Chat> {
        val onlineMap = remoteDataSource.performChatsWithoutParcing()
        val savedMap = cacheDataSource.fetchSavedMessages()
        val chats = mutableListOf<ChatsListComponent.Chat>()
        for (i in onlineMap) {
            chats += ChatsListComponent.Chat(nick = i.key, onlineMessagesCount = i.value, savedMessagesCount = savedMap[i.key] ?: 0, id = remoteDataSource.getId(i.key))
        }

        return chats
    }

    override fun fetchSavedMessages(): Map<String, Int> {
        return cacheDataSource.fetchSavedMessages()
    }

    override fun saveMessages(messages: String) {
        cacheDataSource.saveMessages(messages)
    }

    override fun fetchGotMessages(): Map<String, Int> {
        return cacheDataSource.fetchGotMessages()
    }

    override fun saveGotMessages(messages: String) {
        cacheDataSource.saveGotMessages(messages)
    }

    override fun fetchMTexts(): Map<String, String> {
        return cacheDataSource.fetchMTexts()
    }

    override fun saveMTexts(mText: String) {
        cacheDataSource.saveMTexts(mText)
    }

    override fun fetchLifecycle(): String {
        return cacheDataSource.fetchLifecycle()
    }

    override fun saveLifecycle(lifecycle: String) {
        cacheDataSource.saveLifecycle(lifecycle)
    }

    override fun fetchIsInChat(): Boolean {
        return cacheDataSource.fetchIsInChat()
    }

    override fun saveIsInChat(isInChat: Boolean) {
        cacheDataSource.saveIsInChat(isInChat)
    }

    override suspend fun getMessages(id: String, nick: String): List<ChatComponent.Message> {
        return remoteDataSource.getMessages(id, nick)
    }

    override suspend fun sendMessage(id: String, text: String) {
        remoteDataSource.sendMessage(JsoupMessageSendRequest(text, id))
    }
}