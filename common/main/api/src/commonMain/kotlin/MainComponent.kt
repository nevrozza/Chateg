import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface MainComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun onUnLoginClicked()

    sealed class Child {
        class ChatsListChild(val component: ChatsListComponent): Child()
        class ChatChild(val component: ChatComponent): Child()
    }

}