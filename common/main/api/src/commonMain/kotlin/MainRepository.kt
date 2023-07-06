import com.arkivanov.essenty.lifecycle.Lifecycle

interface MainRepository {
    fun fetchOnlineMessagesWithoutParcing(): List<ChatsListComponent.Chat>
    suspend fun fetchOnlineMessages(): List<ChatsListComponent.Chat>
    suspend fun getMessages(id: String, nick: String): List<ChatComponent.Message>
    suspend fun sendMessage(id: String, text: String)
    fun fetchSavedMessages():  Map<String, Int>//List<ChatsListComponent.Chat>// Map<String, Int>
    fun saveMessages(messages: String)

    fun fetchGotMessages():  Map<String, Int>//List<ChatsListComponent.Chat>// Map<String, Int>
    fun saveGotMessages(messages: String)

    fun fetchMTexts(): Map<String, String>
    fun saveMTexts(mText: String)

    fun fetchLifecycle(): String
    fun saveLifecycle(lifecycle: String)

    fun fetchIsInChat(): Boolean
    fun saveIsInChat(isInChat: Boolean)
}