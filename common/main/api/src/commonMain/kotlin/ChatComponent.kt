import com.arkivanov.decompose.value.Value



interface ChatComponent {
    val model: Value<Model>

    fun onMessageTextChange(text: String)
    suspend fun getMessages()
    fun sendMessage()
    fun newDaySet(day: String)

    data class Model(
        val name: String = "Igorek",
        val messages: List<Message> = listOf(),
        val onlineStatus: String = "",
        val mText: String = "",
        val day: String = "",
        val timeout: Int = 0
    )

    data class Message(
        val time: String,
        val isMine: Boolean,
        val text: String
    )
}