import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun getColors(darkTheme: Boolean = isSystemInDarkTheme()): ColorScheme {
    return if (darkTheme) {

        darkColorScheme(
            primary = Color(0xFFBB86FC),
            secondary = Color(0xFF03DAC5)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC5)
        )
    }
}

@Composable
fun Theme(
    content: @Composable () -> Unit
) {
    val colors = getColors()

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}