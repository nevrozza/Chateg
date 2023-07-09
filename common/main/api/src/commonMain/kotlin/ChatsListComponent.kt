import com.arkivanov.decompose.value.Value

interface ChatsListComponent {
    val model: Value<Model>

    fun onChatClicked(id: String, nick: String)
    fun updateChats()
    fun clearChats()



    data class Model(
        val title: String = "",
        val chats: List<Chat> = listOf(),
        val seconds: Int = 60
    )

    data class Chat(
        val nick: String = "",
        val onlineMessagesCount: String = "",
        val savedMessagesCount: String = "",
        val id: String = ""
    )
}