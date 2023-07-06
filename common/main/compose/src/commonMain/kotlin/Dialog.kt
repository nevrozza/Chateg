import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
expect fun Dialog(isDialogOpened: MutableState<Boolean>, component: MainComponent)