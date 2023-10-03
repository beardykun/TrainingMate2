package jp.mikhail.pankratov.trainingMate.android

import android.app.Application

class TrainingMateApp : Application() {
    companion object {
        lateinit var instance: TrainingMateApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}