package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.core.data.local.DatabaseDriverFactory
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.exercise.data.local.ExerciseDatasource
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.data.local.TrainingDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource

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
}