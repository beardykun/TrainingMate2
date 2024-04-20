package jp.mikhail.pankratov.trainingMate.mainScreens.training.data.local

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.core.listToString
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.toTrainingLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class TrainingDataSource(db: TrainingDatabase) : ITrainingDataSource {

    private val queries = db.trainingTemplateQueries

    override suspend fun insertLocalTraining(training: TrainingLocal) {
        queries.insertTraining(
            id = training.id,
            name = training.name.trim(),
            groups = training.groups,
            exercises = training.exercises.listToString(),
            description = training.description
        )
    }

    override fun getLocalTrainings(): Flow<List<TrainingLocal>> {
        return queries.getTrainings().asFlow().mapToList().map { trainings ->
            trainings.map { training ->
                training.toTrainingLocal()
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun isLocalTrainingTableEmpty(): Boolean {
        return queries.countTrainingTemplates().executeAsOne() == 0L
    }

    override fun getTrainingById(trainingId: Long): Flow<TrainingLocal> {
        return queries.getTrainingsById(trainingId).asFlow().map {
            it.executeAsOne().toTrainingLocal()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateExercises(exercises: List<String>, id: Long) {
        queries.updateExercises(exercises = exercises.listToString(), id = id)
    }

    override suspend fun isLocalTrainingExists(name: String): Boolean {
        return queries.isTrainingExists(name = name.uppercase().trim()).executeAsOne() != 0L
    }

    override suspend fun deleteTrainingTemplate(id: Long) {
        queries.deleteTrainingTemplate(id)
    }
}