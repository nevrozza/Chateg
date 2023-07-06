import MainComponent.Child
import MainComponent.Child.ChatChild
import MainComponent.Child.ChatsListChild
import MainComponent.Model
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnPause
import com.arkivanov.essenty.lifecycle.doOnResume
import com.arkivanov.essenty.lifecycle.doOnStop
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import di.Inject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

    private val _models = MutableValue(Model())
    override val model: Value<Model> = _models
    override fun onBackClicked() {
        navigation.pop()
    }

    init {
        val mainRepository: MainRepository = Inject.instance()
        lifecycle.doOnResume {
            mainRepository.saveLifecycle(lifecycle.state.name)
        }
        lifecycle.doOnStop {
            mainRepository.saveLifecycle(lifecycle.state.name)
        }
        lifecycle.doOnDestroy {
            mainRepository.saveLifecycle(lifecycle.state.name)
        }
    }
    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.ChatsList ->
                ChatsListChild(
                    ChatsListComponentImpl(componentContext) { id, nick ->
                        navigation.bringToFront(Config.Chat(id, nick))
                    }
                )


            is Config.Chat -> ChatChild(
                ChatComponentImpl(componentContext, config.id, config.nick, _models.value.delayMessage) {
                    _models.value = _models.value.copy(delayMessage = it)
                }
            )
        }


    private sealed interface Config : Parcelable {
        @Parcelize
        object ChatsList : Config

        @Parcelize
        data class Chat(val id: String, val nick: String) : Config
    }


}