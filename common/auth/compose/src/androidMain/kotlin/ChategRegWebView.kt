import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
actual fun ChategRegWebView(component: LoginComponent) {
    val uri = "https://club.chateg.club/registration/"

    Scaffold(Modifier.fillMaxSize()) {
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                webViewClient = object : WebViewClient() {
                    override fun doUpdateVisitedHistory(
                        view: WebView?,
                        url: String?,
                        isReload: Boolean
                    ) {
                        super.doUpdateVisitedHistory(view, url, isReload)
                        if(url == "http://club.chateg.club/main/") {
                            Toast.makeText(
                                context,
                                "Регистрация прошла успешно!",
                                Toast.LENGTH_LONG
                            ).show()
                            component.onBackClicked()
                        }
                        view!!.goBack()
                    }

                }
                loadUrl(uri)

            }
        }, update = {
            it.loadUrl(uri)
        })
    }



}