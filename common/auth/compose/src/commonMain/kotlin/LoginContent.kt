import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import di.Inject

//import androidx.compose.desktop.ui.tooling.preview.Preview
//@Preview
//@Composable
//fun LoginContentPreview() {
//    LoginContent(PreviewLoginComponent())
//}

@ExperimentalComposeUiApi
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun LoginContent(component: LoginComponent) {
    val model by component.model.subscribeAsState()

    val uriHandler = LocalUriHandler.current
    val os: String = Inject.instance<OS>().os
    val focusManager = LocalFocusManager.current



    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(model.title, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Black, fontSize = 30.sp)

        Spacer(Modifier.height(30.dp))
        OutlinedTextField(
            modifier = Modifier.width(TextFieldDefaults.MinWidth).onKeyEvent {
                onEnterTextField(it) {
                    focusManager.moveFocus(FocusDirection.Next)
                }
                false
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (model.isError) Color.Red else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (model.isError) Color.Red else MaterialTheme.colorScheme.outline
            ),
            value = model.login,
            onValueChange = { component.onLoginChange(it) },
            leadingIcon = { Icon(imageVector = Icons.Filled.Person, "Login") },
            placeholder = { Text("Логин", fontSize = 16.sp, color = Color.Gray) },
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(autoCorrect = false, imeAction = ImeAction.Next)
        )
        Spacer(Modifier.height(20.dp))
        OutlinedTextField(
            modifier = Modifier.width(TextFieldDefaults.MinWidth).onKeyEvent {
                onEnterTextField(it) {
                    component.onLoginClicked()
                }
                false
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (model.isError) Color.Red else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (model.isError) Color.Red else MaterialTheme.colorScheme.outline
            ),
            value = model.password,
            onValueChange = { component.onPasswordChange(it) },
            leadingIcon = { Icon(imageVector = Icons.Filled.Lock, "Password") },
            placeholder = { Text("Пароль", fontSize = 16.sp, color = Color.Gray) },
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                autoCorrect = false
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    component.onLoginClicked()
                }
            )
        )
        Spacer(Modifier.height(30.dp))
         OutlinedButton(
            modifier = Modifier.width(TextFieldDefaults.MinWidth),
            onClick = { component.onLoginClicked() }
        ) {
            Text("Войти")
        }
        Spacer(Modifier.height(10.dp))
        TextButton(
            onClick = {
                if (os == OSs.PC.name) {
                    uriHandler.openUri("https://club.chateg.club/registration/")
                } else {
                    component.onRegisterClicked()
                }
            }
        ) {
            Text("Регистрация")
        }
    }
    Column {
        AnimatedVisibility(
            model.isWebViewOpened,
            enter = slideInHorizontally(initialOffsetX = { it }),
            exit = slideOutHorizontally(targetOffsetX = { it })
        ) {
            Box() {
                IconButton(onClick = { component.onBackClicked() }) {
                    Icon(Icons.Rounded.ArrowBack, "BackToLogin")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().height(45.dp)
                ) {
                    Text("Регистрация", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }
        }
        AnimatedVisibility(
            model.isWebViewOpened,
            enter = slideInHorizontally(initialOffsetX = { it }),
            exit = slideOutHorizontally(targetOffsetX = { it })
        ) {
            ChategRegWebView(component)
        }
    }

}


@ExperimentalComposeUiApi
fun onEnterTextField(it: KeyEvent, onEnterClicked: () -> Unit) {
    if (it.key.keyCode == Key.Enter.keyCode && it.type == KeyEventType.KeyDown) {
        onEnterClicked()
    }
}