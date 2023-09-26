package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource

expect class AppModule {
    val trainingDataSource: ITrainingDataSource
    val exerciseDataSource: IExerciseDatasource
    val trainingHistoryDataSource: ITrainingHistoryDataSource
}