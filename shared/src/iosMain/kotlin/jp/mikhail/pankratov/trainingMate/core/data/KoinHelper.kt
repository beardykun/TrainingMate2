package jp.mikhail.pankratov.trainingMate.core.data

import jp.mikhail.pankratov.trainingMate.AppViewModel
import jp.mikhail.pankratov.trainingMate.di.AppModule
import jp.mikhail.pankratov.trainingMate.di.useCasesModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin

fun initKoin(appModule: AppModule) {
    startKoin {
        modules(useCasesModel(appModule))
    }
}

class KoinHelper : KoinComponent {
    fun getAppViewModel() = get<AppViewModel>()
}