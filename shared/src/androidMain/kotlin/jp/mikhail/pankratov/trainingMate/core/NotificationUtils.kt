package jp.mikhail.pankratov.trainingMate.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import jp.mikhail.pankratov.trainingMate.SharedRes

actual class NotificationUtils(private val context: Context) {
    actual fun sendNotification() {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var notificationBuilder: NotificationCompat.Builder? = null
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (notificationBuilder == null) {
            notificationBuilder = NotificationCompat.Builder(context, context.packageName)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                context.packageName,
                context.packageName,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = context.packageName
            notificationManager.createNotificationChannel(channel)
        }

        notificationBuilder.setSmallIcon(SharedRes.images.abs.drawableResId)
            .setContentTitle("Rest is over!")
            .setContentText("Return to your workout!")
            .setAutoCancel(true)
            .setSound(defaultSoundUri)

        notificationManager.notify(0, notificationBuilder.build())
    }
}