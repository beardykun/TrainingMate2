package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.core.service.TimerServiceRep
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseSettingsDatasource
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local.ISummaryDatasource

expect class AppModule {
    val trainingDataSource: ITrainingDataSource
    val exerciseDataSource: IExerciseDatasource
    val exerciseHistoryDataSource: IExerciseHistoryDatasource
    val exerciseSettingsDataSource: IExerciseSettingsDatasource
    val trainingHistoryDataSource: ITrainingHistoryDataSource
    val summaryDataSource: ISummaryDatasource
    val timerServiceRep: TimerServiceRep
}


