import ChatsListComponent.Model
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class ChatsListComponentImpl(
    componentContext: ComponentContext,
    private val onChat: (id: String) -> Unit
) : ChatsListComponent, ComponentContext by componentContext {
    override val model = MutableValue(Model("Чаты"))

    override fun onChatClicked(id: String) {
       onChat(id)
    }

}

class PreviewChatsListComponent: ChatsListComponent {
    override fun onChatClicked(id: String) {}
    override val model: Value<Model> = MutableValue(Model("Вход"))
}