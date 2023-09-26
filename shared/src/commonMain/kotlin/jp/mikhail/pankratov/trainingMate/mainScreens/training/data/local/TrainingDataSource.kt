package jp.mikhail.pankratov.trainingMate.mainScreens.training.data.local

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.core.listToString
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.toTraining
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrainingDataSource(db: TrainingDatabase) : ITrainingDataSource {

    private val queries = db.trainingTemplateQueries

    override suspend fun insertTraining(training: Training) {
        queries.insertTraining(
            id = training.id,
            name = training.name,
            groups = training.groups,
            exercises = training.exercises.listToString(),
            description = training.description
        )
    }

    override fun getTrainings(): Flow<List<Training>> {
        return queries.getTrainings().asFlow().mapToList().map { trainings ->
            trainings.map { training ->
                training.toTraining()
            }
        }
    }

    override suspend fun trainingTableEmpty(): Boolean {
        return queries.countTrainingTemplates().executeAsOne() == 0L
    }

    override fun getTrainingById(trainingId: Long): Flow<Training> {
        return queries.getTrainingsById(trainingId).asFlow().map {
            it.executeAsOne().toTraining()
        }
    }

    override suspend fun updateExercises(exercises: List<String>, id: Long) {
        queries.updateExercises(exercises = exercises.listToString(), id = id)
    }
}