import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface MainComponent {
    val childStack: Value<ChildStack<*, Child>>

    val model: Value<Model>
    data class Model(
        val delayMessage: Int = 0
    )
    fun onUnLoginClicked()
    fun onBackClicked()

    sealed class Child {
        class ChatsListChild(val component: ChatsListComponent): Child()
        class ChatChild(val component: ChatComponent): Child()
    }

}