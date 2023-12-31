import com.arkivanov.essenty.lifecycle.Lifecycle

interface MainRepository {
    suspend fun clearChats(): List<ChatsListComponent.Chat>

    fun fetchOnlineMessagesWithoutParcing(): List<ChatsListComponent.Chat>
    suspend fun fetchOnlineMessages(): List<ChatsListComponent.Chat>
    suspend fun getMessages(id: String, nick: String): List<ChatComponent.Message>
    suspend fun sendMessage(id: String, text: String)
    fun fetchSavedMessages(): Map<String, String>//List<ChatsListComponent.Chat>// Map<String, Int>
    fun saveMessages(messages: String)

    fun fetchGotMessages(): Map<String, String>//List<ChatsListComponent.Chat>// Map<String, Int>
    fun saveGotMessages(messages: String)

    fun fetchMTexts(): Map<String, String>
    fun saveMTexts(mText: String)

    fun fetchLifecycle(): String
    fun saveLifecycle(lifecycle: String)

    fun fetchIsInChat(): String
    fun saveIsInChat(isInChat: String)
}