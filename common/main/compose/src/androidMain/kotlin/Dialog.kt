import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity

@Composable
actual fun Dialog(isDialogOpened: MutableState<Boolean>, component: MainComponent) {

    AlertDialog(
        onDismissRequest = { isDialogOpened.value = false },
        title = { Text("Выход") },
        text = { Text("Вы уверены, что хотите выйти?\nПосле этого необходимо будет заново зайти в аккаунт") },
        confirmButton = {
            TextButton(
                onClick = {
                    component.onUnLoginClicked()
                }
            ) {
                Text("Выйти из аккаунта")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    isDialogOpened.value = false
                }
            ) {
                Text("Отмена")
            }
        }
    )
}