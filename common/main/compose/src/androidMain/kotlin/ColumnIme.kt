import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
actual fun KeyboardHeight(): Dp {
    val density = LocalDensity.current
    val pxValue = WindowInsets.ime.getBottom(LocalDensity.current)
    return with(density) { pxValue.toDp() }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
actual fun ColumnIme(
    modifier: Modifier,
    verticalArrangement: Arrangement.Vertical,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier
            .fillMaxHeight().imePadding()) {
        content()
    }
}

@ExperimentalLayoutApi
@Composable
actual fun LazyColumnIme(
    modifier: Modifier,
    verticalArrangement: Arrangement.Vertical,
    content: LazyListScope.() -> Unit
) {
    LazyColumn(modifier.fillMaxSize().imePadding(),verticalArrangement = verticalArrangement, content = content)
}