package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.mainSccreeens.training.domain.local.ITrainingDataSource

expect class AppModule {

    val trainingDataSource: ITrainingDataSource
    val exerciseDataSource: IExerciseDatasource
}