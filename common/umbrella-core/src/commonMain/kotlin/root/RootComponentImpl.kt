package root

import LoginComponentImpl
import MainComponentImpl
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import root.RootComponent.*

class RootComponentImpl(
    componentContext: ComponentContext
): RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        initialConfiguration = Config.Login,
        handleBackButton = true,
        childFactory = ::child
    )
    override val childStack: Value<ChildStack<*, Child>> = stack

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Login -> Child.LoginChild(
                LoginComponentImpl(componentContext) {
                    navigation.bringToFront(Config.Main)
                }
            )
            is Config.Main -> Child.MainChild(
                MainComponentImpl(componentContext) {
                    navigation.bringToFront(Config.Login)
                }
            )

        }


    private sealed interface Config : Parcelable {
        @Parcelize
        object Login : Config

        @Parcelize
        object Main : Config
    }

}