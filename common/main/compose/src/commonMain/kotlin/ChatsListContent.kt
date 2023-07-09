import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoDelete
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pullRefresh.PullRefreshIndicator
import pullRefresh.pullRefresh
import pullRefresh.rememberPullRefreshState



@ExperimentalComposeUiApi
@Composable
fun ChatsListContent(component: ChatsListComponent) {
    val model by component.model.subscribeAsState()

    val requester = remember { FocusRequester() }


    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1000)
        component.updateChats()
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)
    Box(Modifier
        .pullRefresh(refreshState)
        .focusRequester(requester)
        .focusable()
        .onKeyEvent {
            if(it.key == Key.F5 && it.type == KeyEventType.KeyDown) {
                refresh()
            }
            false
        }
    ) {

        LaunchedEffect(Unit) {
            requester.requestFocus()
        }

        Column(Modifier.fillMaxSize()) {
            LazyColumn(Modifier.fillMaxSize()) {
                items(model.chats) {
                    Chat(component, it)
                    if(model.chats.indexOf(it) == model.chats.lastIndex) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Spacer(Modifier.height(30.dp))
                            IconButton(
                                onClick = { component.clearChats() }
                            ) {
                                Icon(Icons.Rounded.AutoDelete, null)
                            }
                            Text("Чистите список чатов, чтобы не лагало")
                            Text("Чат \"igorek\" удалён не будет")
                        }
                    }
                }
            }
        }
        PullRefreshIndicator(
            modifier = Modifier.align(alignment = Alignment.TopCenter),
            refreshing = refreshing,
            state = refreshState,
        )
    }
}

@Composable
fun Chat(component: ChatsListComponent, it: ChatsListComponent.Chat) {
    Box(contentAlignment = Alignment.CenterStart,
        modifier = Modifier.fillMaxWidth().height(60.dp)
            .clickable { component.onChatClicked(it.id, it.nick)}) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Rounded.Person,
                "chatIcon"
            )
            Spacer(Modifier.fillMaxWidth().weight(.3f))
            Box(
                Modifier.alpha(if (it.onlineMessagesCount - it.savedMessagesCount == 0) 0f else 1f),
                contentAlignment = Alignment.Center
            ) {
                val text = it.onlineMessagesCount - it.savedMessagesCount
                Text(
                    text.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .defaultMinSize(30.dp, 0.dp)
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer,
                            shape = CircleShape
                        ),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
        Box(
            Modifier.fillMaxWidth().height(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                it.nick,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }
    }
}
