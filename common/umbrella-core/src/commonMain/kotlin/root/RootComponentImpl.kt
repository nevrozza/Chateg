package root

import AuthRepository
import LoginComponentImpl
import MainComponentImpl
import MainRepository
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import di.Inject
import root.RootComponent.*

class RootComponentImpl(
    componentContext: ComponentContext
): RootComponent, ComponentContext by componentContext {
    private val authRepository: AuthRepository = Inject.instance()
    private val mainRepository: MainRepository = Inject.instance()
    private val cookies: Map<String, String> = authRepository.fetchCookies()

    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        initialConfiguration =  if(cookies.isNotEmpty()) Config.Main else Config.Login, //if(cookies.isNotEmpty()) Config.Main else
        handleBackButton = true,
        childFactory = ::child
    )
    override val childStack: Value<ChildStack<*, Child>> = stack

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Login -> Child.LoginChild(
                LoginComponentImpl(componentContext) {
                    navigation.replaceAll(Config.Main)
                }
            )
            is Config.Main -> Child.MainChild(
                MainComponentImpl(componentContext) {
                    navigation.replaceAll(Config.Login)
                    mainRepository.saveMTexts("")
                    mainRepository.saveMessages("")
                    mainRepository.saveGotMessages("")
                    authRepository.saveCookies("")
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