package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.domain.useCases.UpdateAutoInputUseCase
import jp.mikhail.pankratov.trainingMate.core.domain.commomUseCases.ValidateNumericInputUseCase
import org.koin.dsl.module

fun domainUseCasesModule() = module {
    single { UpdateAutoInputUseCase() }
    single { ValidateNumericInputUseCase() }
}