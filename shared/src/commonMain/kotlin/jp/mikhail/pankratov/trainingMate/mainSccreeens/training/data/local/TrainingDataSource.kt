package jp.mikhail.pankratov.trainingMate.mainSccreeens.training.data.local

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import jp.mikhail.pankratov.trainingMate.mainSccreeens.training.domain.local.ITrainingDataSource
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.database.TrainingDatabase
import jp.mikhail.pankratov.trainingMate.mainSccreeens.training.domain.local.toTraining
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrainingDataSource(db: TrainingDatabase) : ITrainingDataSource {

    private val queries = db.trainingTemplateQueries

    override suspend fun insertTraining(training: Training) {
        queries.insertTraining(
            id  = null,
            name = training.name,
            groups = training.groups,
            description = training.description
        )
    }

    override fun getTrainings(): Flow<List<Training?>> {
        return queries.getTrainings().asFlow().mapToList().map { trainings ->
            trainings.map { training ->
                training.toTraining()
            }
        }
    }

    override suspend fun trainingTableEmpty(): Boolean {
        return queries.countTrainingTemplates().executeAsOne() == 0L
    }

    private fun List<String>.listToString(): String {
        return this.filterNot { it.isEmpty() }
            .joinToString(separator = ", ")
    }
}