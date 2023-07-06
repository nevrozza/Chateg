import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun KeyboardHeight(): Dp = 0.dp

@Composable
actual fun ColumnIme(
    modifier: Modifier,
    verticalArrangement: Arrangement.Vertical,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier.fillMaxHeight()) {
        content
    }
}

@ExperimentalLayoutApi
@Composable
actual fun LazyColumnIme(
    modifier: Modifier,
    verticalArrangement: Arrangement.Vertical,
    content: LazyListScope.() -> Unit
) {
    LazyColumn(modifier.fillMaxSize(),verticalArrangement = verticalArrangement, content = content)
}