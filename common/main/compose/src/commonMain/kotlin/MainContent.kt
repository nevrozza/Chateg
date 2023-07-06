import MainComponent.Child.ChatChild
import MainComponent.Child.ChatsListChild
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
fun MainContent(component: MainComponent) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) {
        when (val child = it.instance) {
            is ChatsListChild -> {}
            is ChatChild -> {}
        }
    }
}