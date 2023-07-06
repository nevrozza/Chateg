import MainComponent.Child.ChatChild
import MainComponent.Child.ChatsListChild
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Login
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MainContent(component: MainComponent) {
    val childStack by component.childStack.subscribeAsState()
    val title = remember { mutableStateOf("Чаты") }
    val isDialogOpened = remember { mutableStateOf(false) }

    if (isDialogOpened.value) {
        Dialog(isDialogOpened, component)
    }


    Box(Modifier.fillMaxSize().padding(top = 45.dp)) {
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) {

            when (val child = it.instance) {

                is ChatsListChild -> {
                    title.value = child.component.model.value.title
                    ChatsListContent(child.component)
                }


                is ChatChild -> {
                    if (
                        childStack.active.instance == it.instance) {
                        title.value = child.component.model.value.name
                    }
                    ChatContent(child.component)
                }
            }
        }
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            Column() {
                AnimatedVisibility(
                    title.value == "Чаты",
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    IconButton(onClick = { isDialogOpened.value = true }) {
                        Icon(Icons.Rounded.Login, null, modifier = Modifier.rotate(180f))
                    }
                }
            }
            Column {
                AnimatedVisibility(
                    title.value != "Чаты",
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    IconButton(onClick = { component.onBackClicked() }) {
                        Icon(Icons.Rounded.ArrowBack, "BackToChats")
                    }
                }
            }

            AnimatedContent(title.value) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().height(45.dp)
                ) {
                    Text(
                        it,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                }
            }
        }
    }


}