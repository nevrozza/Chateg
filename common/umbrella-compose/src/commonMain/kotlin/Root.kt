import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import root.RootComponent

@Composable
fun Root(root: RootComponent) {

    Surface(modifier = Modifier.fillMaxSize()) {

        RootContent(root)
    }

}