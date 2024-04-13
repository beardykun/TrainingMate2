package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases

import jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.domain.InsertExerciseUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.domain.IsExerciseExistsUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateBestLiftedWeightUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateExerciseDataUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateTrainingDataUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.domain.useCases.GetExercisesWithNameUseCase
import org.koin.core.Koin

class UseCaseProvider(val koin: Koin) {
    inline fun <reified T : Any> get(): T = koin.get()

    fun getUpdateBestLiftedWeightUseCase(): UpdateBestLiftedWeightUseCase = get()
    fun getExerciseByTemplateIdUseCase(): GetExerciseByTemplateIdUseCase = get()
    fun getOngoingTrainingUseCase(): GetOngoingTrainingUseCase = get()
    fun getExerciseFromHistoryUseCase(): GetExerciseFromHistoryUseCase = get()
    fun getLatsSameExerciseUseCase(): GetLatsSameExerciseUseCase = get()
    fun getUpdateExerciseDataUseCase(): UpdateExerciseDataUseCase = get()
    fun getUpdateTrainingDataUseCase(): UpdateTrainingDataUseCase = get()
    fun getExercisesWithNameUseCase(): GetExercisesWithNameUseCase = get()
    fun getInsertExerciseUseCase(): InsertExerciseUseCase = get()
    fun getIsExerciseExistsUseCase(): IsExerciseExistsUseCase = get()
    fun getUpdateTrainingExerciseUseCase(): UpdateTrainingExerciseUseCase = get()
    fun getInsertTrainingRecordUseCase(): InsertTrainingRecordUseCase = get()
    fun getExerciseByGroupUseCase(): GetLocalExerciseByGroupUseCase = get()
    fun getDeleteLocalExerciseByIdUseCase(): DeleteLocalExerciseByIdUseCase = get()
}

