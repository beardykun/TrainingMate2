package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.local.IExerciseDatasource
import kotlinx.coroutines.flow.Flow

class GetLocalExercisesByNamesUseCase(private val exerciseDatasource: IExerciseDatasource) {
    operator fun invoke(names: List<String>): Flow<List<ExerciseLocal>> {
        return exerciseDatasource.getExercisesByNames(names)
    }
}