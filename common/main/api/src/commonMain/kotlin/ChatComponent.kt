import com.arkivanov.decompose.value.Value

data class Message(
    val time: String,
    val nick: String,
    val text: String
)

interface ChatComponent {
    val model: Value<Model>

    data class Model(
        val name: String = "",
        val messages: List<Message> = listOf(),
        val onlineStatus: String = ""
    )
}