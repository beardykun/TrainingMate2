package jp.mikhail.pankratov.trainingMate.core.domain

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import jp.mikhail.pankratov.trainingMate.R
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.TimerDataHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

private const val ZERO = 0
private const val SECOND = 1000L
const val INITIAL_COUNT = "initial_count"

class TimerServiceImpl : LifecycleService() {

    private val notificationChannelId = "timer_channel_id"
    private val notificationChannelName = "Timer Service Channel"
    private val notificationId = 1
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private var timerJob: Job? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val initialCount = intent?.getIntExtra(INITIAL_COUNT, 60) ?: 60

        setupNotificationChannel()
        initializeNotificationBuilder()
        startForegroundServiceCompat(notificationBuilder.build())
        startTimerJob(initialCount)

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    private fun setupNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                notificationChannelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun initializeNotificationBuilder() {
        notificationBuilder = NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("Timer Running")
            .setSmallIcon(R.drawable.incline_dumbbell_flyes)
            .setOnlyAlertOnce(true) // Avoid notification sound on each update
    }

    private fun startForegroundServiceCompat(notification: Notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                notificationId,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            )
        } else {
            startForeground(notificationId, notification)
        }
    }

    private fun startTimerJob(initialCount: Int) {
        timerJob?.cancel() // Cancel any existing job
        timerJob = lifecycleScope.launch {
            startTimer(initialCount).collect { updateNotification(it) }
        }
    }

    private fun updateNotification(count: Int) {
        TimerDataHolder.postValue(count)
        notificationBuilder.setContentText("Time remaining: $count seconds")

        // Notify the NotificationManager to display the updated notification
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            .notify(notificationId, notificationBuilder.build())
    }

    private fun startTimer(initValue: Int) = flow {
        var count = initValue
        while (count >= ZERO) {
            emit(count--)
            delay(SECOND)
        }
    }.flowOn(Dispatchers.Default)
}
