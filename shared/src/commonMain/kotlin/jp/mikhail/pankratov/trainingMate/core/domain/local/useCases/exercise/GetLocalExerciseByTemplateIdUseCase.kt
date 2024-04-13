package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise

import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.local.IExerciseDatasource
import kotlinx.coroutines.flow.Flow

class GetLocalExerciseByTemplateIdUseCase(private val exerciseDatasource: IExerciseDatasource) {

    operator fun invoke(exerciseTemplateId: Long): Flow<ExerciseLocal> {
        return exerciseDatasource.getExerciseById(exerciseTemplateId)
    }
}