package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases

import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.CountExerciseInHistoryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.DeleteLocalExerciseByIdUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetAllLocalExercisesUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetExerciseFromHistoryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetExercisesForTrainingHistoryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetLatsSameHistoryExerciseUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetLocalExerciseByGroupUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetLocalExerciseByTemplateIdUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetLocalExercisesByNamesUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetStrengthDefiningExercisesUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.InsertExerciseHistoryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.InsertLocalExerciseUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.domain.IsLocalExerciseExistsUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateBestLiftedWeightUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateHistoryExerciseDataUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.domain.useCases.GetHistoryExercisesWithNameUseCase
import org.koin.core.Koin

class ExerciseUseCaseProvider(val koin: Koin) {
    inline fun <reified T : Any> get(): T = koin.get()

    //Local
    fun getUpdateBestLiftedWeightUseCase(): UpdateBestLiftedWeightUseCase = get()
    fun getExerciseByTemplateIdUseCase(): GetLocalExerciseByTemplateIdUseCase = get()
    fun getIsLocalExerciseExistsUseCase(): IsLocalExerciseExistsUseCase = get()
    fun getLocalExerciseByGroupUseCase(): GetLocalExerciseByGroupUseCase = get()
    fun getDeleteLocalExerciseByIdUseCase(): DeleteLocalExerciseByIdUseCase = get()
    fun getInsertLocalExerciseUseCase(): InsertLocalExerciseUseCase = get()
    fun getLocalExercisesByNamesUseCase(): GetLocalExercisesByNamesUseCase = get()
    fun getAllLocalExercisesUseCase(): GetAllLocalExercisesUseCase = get()
    fun getStrengthDefineExercisesUseCase(): GetStrengthDefiningExercisesUseCase = get()


    //History
    fun getExerciseFromHistoryUseCase(): GetExerciseFromHistoryUseCase = get()
    fun getLatsSameExerciseUseCase(): GetLatsSameHistoryExerciseUseCase = get()
    fun getUpdateHistoryExerciseDataUseCase(): UpdateHistoryExerciseDataUseCase = get()
    fun getHistoryExercisesWithNameUseCase(): GetHistoryExercisesWithNameUseCase = get()
    fun getInsertExerciseHistoryUseCase(): InsertExerciseHistoryUseCase = get()
    fun getCountExerciseInHistoryUseCase(): CountExerciseInHistoryUseCase = get()
    fun getGetExercisesForTrainingHistoryUseCase(): GetExercisesForTrainingHistoryUseCase = get()

}

