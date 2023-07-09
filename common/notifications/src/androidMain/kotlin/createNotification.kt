import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import di.Inject

actual fun createNotification(text: String) {
    val platformConfiguration: PlatformConfiguration = Inject.instance()
    val context = platformConfiguration.androidContext
    val notificationManager = platformConfiguration.notificationManager
    val notificationChannel: NotificationChannel
    val channelId = "com.chateg.app"

    val id = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        notificationManager.activeNotifications.size+1
    } else {
        6556
    }

    Log.e("sad", text)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        notificationChannel = NotificationChannel(channelId, "namexx", NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationChannel.enableVibration(true)
        notificationManager.createNotificationChannel(notificationChannel)

        notificationChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
    }

    val intent: Intent? = context.packageManager.getLaunchIntentForPackage("com.chateg.app.android")
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, channelId)
        .setGroup("group")
        .setAutoCancel(true)
        .setSmallIcon(com.arkivanov.decompose.R.drawable.notification_bg)
        .setContentTitle(text)
        .setContentIntent(pendingIntent)



    // FLAG_UPDATE_CURRENT specifies that if a previous
    // PendingIntent already exists, then the current one
    // will update it with the latest intent
    // 0 is the request code, using it later with the
    // same method again will get back the same pending
    // intent for future reference
    // intent passed here is to our afterNotification class

    notificationManager.notify(id, builder.build())
}