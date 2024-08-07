package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.core.service.TimerServiceRep
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource
import jp.mikhail.pankratov.trainingMate.summaryFeature.domain.local.ISummaryDatasource
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseHistoryDatasource

expect class AppModule {
    val trainingDataSource: ITrainingDataSource
    val exerciseDataSource: IExerciseDatasource
    val exerciseHistoryDataSource: IExerciseHistoryDatasource
    val trainingHistoryDataSource: ITrainingHistoryDataSource
    val summaryDataSource: ISummaryDatasource
    val timerServiceRep: TimerServiceRep
}


