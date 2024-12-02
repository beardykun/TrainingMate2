package jp.mikhail.pankratov.trainingMate.di.local

import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.domain.useCases.RemoveExerciseUseCase
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.domain.useCases.TrainingScoreUseCase
import org.koin.dsl.module

fun appUseCasesProvider() = module {
    single { RemoveExerciseUseCase(trainingUseCaseProvider = get()) }
    single { TrainingScoreUseCase() }
}