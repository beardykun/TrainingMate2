package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.core.NotificationUtils
import jp.mikhail.pankratov.trainingMate.core.data.local.DatabaseDriverFactory
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.exercise.data.local.ExerciseDatasource
import jp.mikhail.pankratov.trainingMate.exercise.data.local.ExerciseHistoryDatasource
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseHistoryDatasource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.data.local.TrainingDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.data.local.TrainingHistoryDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingHistoryDataSource

actual class AppModule {

    actual val trainingDataSource: ITrainingDataSource by lazy {
        TrainingDataSource(
            db = TrainingDatabase(
                driver = DatabaseDriverFactory().createDriver()
            )
        )
    }

    actual val exerciseDataSource: IExerciseDatasource by lazy {
        ExerciseDatasource(
            db = TrainingDatabase(
                driver = DatabaseDriverFactory().createDriver()
            )
        )
    }

    actual val exerciseHistoryDataSource: IExerciseHistoryDatasource by lazy {
        ExerciseHistoryDatasource(
            db = TrainingDatabase(
                driver = DatabaseDriverFactory().createDriver()
            )
        )
    }

    actual val trainingHistoryDataSource: ITrainingHistoryDataSource by lazy {
        TrainingHistoryDataSource(
            db = TrainingDatabase(
                driver = DatabaseDriverFactory().createDriver()
            )
        )
    }
    actual val notificationUtils: NotificationUtils
        get() = TODO("Not yet implemented")
}