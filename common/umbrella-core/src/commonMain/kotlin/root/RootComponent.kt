package root

import LoginComponent
import MainComponent
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class LoginChild(val component: LoginComponent): Child()
        class MainChild(val component: MainComponent): Child()
    }
}