import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
actual fun Dialog(isDialogOpened: MutableState<Boolean>, component: MainComponent) {

        AlertDialog(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground,
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