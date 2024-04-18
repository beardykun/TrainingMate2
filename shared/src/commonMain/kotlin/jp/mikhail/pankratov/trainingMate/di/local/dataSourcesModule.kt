package jp.mikhail.pankratov.trainingMate.di.local

import jp.mikhail.pankratov.trainingMate.di.AppModule
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local.ISummaryDatasource
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource
import org.koin.dsl.module

fun dataSourcesModule(appModule: AppModule) = module {
    single<IExerciseDatasource> { appModule.exerciseDataSource }
    single<IExerciseHistoryDatasource> { appModule.exerciseHistoryDataSource }
    single<ITrainingDataSource> { appModule.trainingDataSource }
    single<ITrainingHistoryDataSource> { appModule.trainingHistoryDataSource }
    single<ISummaryDatasource> { appModule.summaryDataSource }
}