package jp.mikhail.pankratov.trainingMate.android

import android.app.Application
import jp.mikhail.pankratov.trainingMate.di.AppModule
import jp.mikhail.pankratov.trainingMate.di.domainUseCasesModule
import jp.mikhail.pankratov.trainingMate.di.local.dataSourcesModule
import jp.mikhail.pankratov.trainingMate.di.local.exerciseUseCaseModule
import jp.mikhail.pankratov.trainingMate.di.local.summaryUseCaseModule
import jp.mikhail.pankratov.trainingMate.di.local.trainingUseCaseModule
import jp.mikhail.pankratov.trainingMate.di.local.viewModelModule
import jp.mikhail.pankratov.trainingMate.di.utilsModule
import org.koin.core.context.startKoin

class TrainingMateApp : Application() {
    companion object {
        lateinit var instance: TrainingMateApp
            private set
    }

    lateinit var appModule: AppModule
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        appModule = AppModule(context = this)
        startKoin {
            modules(
                dataSourcesModule(appModule),
                utilsModule(appModule),
                trainingUseCaseModule(),
                exerciseUseCaseModule(),
                summaryUseCaseModule(),
                domainUseCasesModule(),
                viewModelModule()
            )
        }
    }
}