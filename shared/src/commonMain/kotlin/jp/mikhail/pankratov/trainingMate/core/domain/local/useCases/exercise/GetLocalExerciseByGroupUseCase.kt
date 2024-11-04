package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseDatasource
import kotlinx.coroutines.flow.Flow

class GetLocalExerciseByGroupUseCase(private val exerciseDatasource: IExerciseDatasource) {
    operator fun invoke(groupNames: String): Flow<List<ExerciseLocal>> {
        return exerciseDatasource.getExercisesByGroups(groupNames)
    }
}