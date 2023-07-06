import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(component: LoginComponent) {
    val model by component.model.subscribeAsState()
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = model.login,
            onValueChange = { component.onLoginChange(it) },
            leadingIcon = { Icon(imageVector = Icons.Filled.Person, "Login") },
            placeholder = { Text("Логин") })

        OutlinedTextField(
            value = model.password,
            onValueChange = { component.onPasswordChange(it) },
            leadingIcon = { Icon(imageVector = Icons.Filled.Lock, "Password") },
            placeholder = { Text("Пароль") })

        Button(
            onClick = { component.onLoginClicked() }
        ) {
            Text("Войти")
        }
    }
}