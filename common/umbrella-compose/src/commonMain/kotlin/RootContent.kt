import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import root.RootComponent
import root.RootComponent.Child.LoginChild
import root.RootComponent.Child.MainChild

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun RootContent(component: RootComponent) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) {

        when (val child = it.instance) {
            is LoginChild -> Scaffold(Modifier.fillMaxSize()) { padding ->
                Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                    LoginContent(child.component)
                }
            }

            is MainChild -> Scaffold(Modifier.fillMaxSize()) { padding ->
                Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                    MainContent(child.component)
                }
            }
        }
    }
}