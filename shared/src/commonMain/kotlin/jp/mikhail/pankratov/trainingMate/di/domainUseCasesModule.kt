package jp.mikhail.pankratov.trainingMate.di

import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.UpdateAutoInputUseCase
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.domain.useCases.ValidateInputUseCase
import org.koin.dsl.module

fun domainUseCasesModule() = module {
    single { UpdateAutoInputUseCase() }
    single { ValidateInputUseCase() }
}