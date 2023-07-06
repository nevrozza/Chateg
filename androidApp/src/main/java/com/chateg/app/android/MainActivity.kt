package com.chateg.app.android

import OS
import PlatformConfiguration
import Root
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Resources.Theme
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.arkivanov.decompose.defaultComponentContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import getColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import root.RootComponentImpl
import java.util.concurrent.TimeUnit


@ExperimentalMaterial3Api
@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {
    @SuppressLint("SourceLockedOrientationActivity", "CoroutineCreationDuringComposition")
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val periodicWorkRequest = PeriodicWorkRequestBuilder<WManager>(16, TimeUnit.MINUTES).build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "uniqueWorkName",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
        Log.e("xxx", WorkManager.isInitialized().toString())
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        PlatformSDK.init(
            PlatformConfiguration(applicationContext),
            OS(OSs.Android.name)
        )


        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())


        val root = RootComponentImpl(componentContext = defaultComponentContext())
        setContent {
            val systemUiController = rememberSystemUiController()
            val colors = getColors()
            systemUiController.setSystemBarsColor(
                color = colors.surface
            )
            val view = LocalView.current
            val isDark = isSystemInDarkTheme()
            SideEffect {
                ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = !isDark
            }
            Root(root = root)
        }
    }
}

