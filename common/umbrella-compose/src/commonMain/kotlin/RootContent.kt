import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import root.RootComponent
import root.RootComponent.Child.LoginChild
import root.RootComponent.Child.MainChild

@Composable
fun RootContent(component: RootComponent) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) {

        when (val child = it.instance) {
            is LoginChild -> LoginContent(child.component)
            is MainChild -> MainContent(child.component)
        }
    }
}