package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases

import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.DeleteLocalExerciseByIdUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetExerciseFromHistoryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetLatsSameHistoryExerciseUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetLocalExerciseByGroupUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.GetLocalExerciseByTemplateIdUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.exercise.InsertLocalExerciseUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary.GetTwoLastMonthlySummaryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary.GetTwoLastWeeklySummaryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.summary.InsetSummaryUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.UpdateTrainingHistoryStatusUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.domain.InsertExerciseUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.domain.IsExerciseExistsUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateBestLiftedWeightUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateExerciseDataUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.domain.useCases.GetExercisesWithNameUseCase
import org.koin.core.Koin

class ExerciseUseCaseProvider(val koin: Koin) {
    inline fun <reified T : Any> get(): T = koin.get()

    fun getUpdateBestLiftedWeightUseCase(): UpdateBestLiftedWeightUseCase = get()
    fun getExerciseByTemplateIdUseCase(): GetLocalExerciseByTemplateIdUseCase = get()
    fun getExerciseFromHistoryUseCase(): GetExerciseFromHistoryUseCase = get()
    fun getLatsSameExerciseUseCase(): GetLatsSameHistoryExerciseUseCase = get()
    fun getUpdateExerciseDataUseCase(): UpdateExerciseDataUseCase = get()
    fun getExercisesWithNameUseCase(): GetExercisesWithNameUseCase = get()
    fun getInsertExerciseUseCase(): InsertExerciseUseCase = get()
    fun getIsExerciseExistsUseCase(): IsExerciseExistsUseCase = get()
    fun getExerciseByGroupUseCase(): GetLocalExerciseByGroupUseCase = get()
    fun getDeleteLocalExerciseByIdUseCase(): DeleteLocalExerciseByIdUseCase = get()
    fun getTwoLastMonthlySummaryUseCase(): GetTwoLastMonthlySummaryUseCase = get()
    fun getTwoLastWeeklySummaryUseCase(): GetTwoLastWeeklySummaryUseCase = get()
    fun getInsetSummaryUseCase(): InsetSummaryUseCase = get()
    fun getUpdateStatusUseCase(): UpdateTrainingHistoryStatusUseCase = get()
    fun getInsertLocalExerciseUseCase(): InsertLocalExerciseUseCase = get()
}

