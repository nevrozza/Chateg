import ChatComponent.Model
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class ChatComponentImpl(
    componentContext: ComponentContext,
    id: String
) : ChatComponent, ComponentContext by componentContext {
    override val model = MutableValue(Model(""))

}

class PreviewChatComponent: ChatComponent {
    override val model: Value<Model> = MutableValue(Model("Igoryok"))
}