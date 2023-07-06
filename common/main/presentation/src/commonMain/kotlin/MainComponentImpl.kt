import MainComponent.Child
import MainComponent.Child.ChatChild
import MainComponent.Child.ChatsListChild
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

class MainComponentImpl(
    componentContext: ComponentContext,
    private val onUnLogin: () -> Unit
) : MainComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        initialConfiguration = Config.ChatsList,
        handleBackButton = true,
        childFactory = ::child
    )
    override val childStack: Value<ChildStack<*, Child>> = stack
    override fun onUnLoginClicked() {
        onUnLogin()
    }

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.ChatsList -> ChatsListChild(
                ChatsListComponentImpl(componentContext) { id ->
                    navigation.bringToFront(Config.Chat(id))
                }
            )

            is Config.Chat -> ChatChild(
                ChatComponentImpl(componentContext, config.id)
            )
        }


    private sealed interface Config : Parcelable {
        @Parcelize
        object ChatsList : Config

        @Parcelize
        data class Chat(val id: String) : Config
    }


}