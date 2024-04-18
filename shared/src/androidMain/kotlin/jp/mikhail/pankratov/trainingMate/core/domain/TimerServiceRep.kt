package jp.mikhail.pankratov.trainingMate.core.domain

import android.content.Context
import android.content.Intent
import android.os.Build

actual class TimerServiceRep(private val context: Context) {
    actual fun startService(initCount: Int) {
        val intent = Intent(context, TimerServiceImpl::class.java).apply {
            putExtra(INITIAL_COUNT, initCount)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    actual fun stopService() {
        val stopIntent = Intent(context, TimerServiceImpl::class.java)
        context.stopService(stopIntent)
    }
}