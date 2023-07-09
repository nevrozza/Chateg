import android.app.NotificationManager
import android.content.Context

actual class PlatformConfiguration(
    val androidContext: Context,
    val notificationManager: NotificationManager
)