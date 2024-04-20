package jp.mikhail.pankratov.trainingMate.core.domain.local.useCases

import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.local.IsLocalTrainingTableEmptyUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.DeleteTrainingHistoryRecordUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.GetLastSameHistoryTrainingUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.GetLatestHistoryTrainingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.GetOngoingTrainingUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.GetParticularHistoryTrainingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.GetParticularMonthHistoryTrainings
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.GetParticularWeekHistoryTrainings
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.GetTrainingHistoryRecordByIdUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.InsertTrainingHistoryRecordUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.history.UpdateTrainingHistoryStatusUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.local.DeleteTrainingTemplateUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.local.GetLocalTrainingsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.local.InsertLocalTrainingUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.local.IsLocalTrainingExistsUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.training.local.UpdateTrainingLocalExerciseUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateTrainingHistoryDataUseCase
import org.koin.core.Koin

class TrainingUseCaseProvider(val koin: Koin) {
    inline fun <reified T : Any> get(): T = koin.get()

    //Local
    fun getInsertLocalTrainingUseCase(): InsertLocalTrainingUseCase = get()
    fun getTrainingTableEmptyUseCase(): IsLocalTrainingTableEmptyUseCase = get()
    fun getLocalTrainingsUseCase(): GetLocalTrainingsUseCase = get()
    fun getUpdateTrainingLocalExerciseUseCase(): UpdateTrainingLocalExerciseUseCase = get()
    fun getDeleteTrainingTemplateUseCase(): DeleteTrainingTemplateUseCase = get()
    fun getIsLocalTrainingExistsUseCase(): IsLocalTrainingExistsUseCase = get()

    //History
    fun getInsertTrainingHistoryRecordUseCase(): InsertTrainingHistoryRecordUseCase = get()
    fun getLatestHistoryTrainingsUseCase(): GetLatestHistoryTrainingsUseCase = get()
    fun getDeleteTrainingHistoryRecordUseCase(): DeleteTrainingHistoryRecordUseCase = get()
    fun getOngoingTrainingUseCase(): GetOngoingTrainingUseCase = get()
    fun getUpdateTrainingHistoryDataUseCase(): UpdateTrainingHistoryDataUseCase = get()
    fun getUpdateTrainingHistoryStatusUseCase(): UpdateTrainingHistoryStatusUseCase = get()
    fun getGetLastSameHistoryTrainingUseCase(): GetLastSameHistoryTrainingUseCase = get()
    fun getGetHistoryTrainingRecordByIdUseCase(): GetTrainingHistoryRecordByIdUseCase = get()
    fun getParticularHistoryTrainingsUseCase(): GetParticularHistoryTrainingsUseCase = get()
    fun getParticularMonthHistoryTrainings(): GetParticularMonthHistoryTrainings = get()
    fun getParticularWeekHistoryTrainings(): GetParticularWeekHistoryTrainings = get()
}