package jp.mikhail.pankratov.trainingMate.di.local

import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.domain.useCases.RemoveExerciseUseCase
import org.koin.dsl.module

fun appUseCasesProvider() = module {
    single { RemoveExerciseUseCase(trainingUseCaseProvider = get()) }
}