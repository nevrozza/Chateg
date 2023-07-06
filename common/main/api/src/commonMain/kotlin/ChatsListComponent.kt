import com.arkivanov.decompose.value.Value

interface ChatsListComponent {
    val model: Value<Model>

    fun onChatClicked(id: String)

    data class Model(
        val title: String = ""
    )
}