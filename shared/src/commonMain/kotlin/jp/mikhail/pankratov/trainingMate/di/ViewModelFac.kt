package jp.mikhail.pankratov.trainingMate.di

import dev.icerock.moko.mvvm.compose.viewModelFactory
import jp.mikhail.pankratov.trainingMate.AppViewModel
import jp.mikhail.pankratov.trainingMate.createTraining.presentation.CreateTrainingViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation.AnalysisViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen.HistoryInfoViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen.HistoryScreenViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.TrainingViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation.AddExercisesViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.presentation.CreateExerciseViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.ExerciseAtWorkViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.presentation.ExerciseAtWorkHistoryViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.presentation.ThisTrainingViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object ViewModelsFac : KoinComponent {
    fun getAppViewModelFactory() =
        viewModelFactory { AppViewModel(trainingDataSource = get(), exerciseDataSource = get()) }

    fun getTrainingViewModelFactory() =
        viewModelFactory {
            TrainingViewModel(
                trainingDataSource = get(),
                trainingHistoryDataSource = get(),
                summaryDataSource = get()
            )
        }

    fun getAnalysisViewModelFactory() =
        viewModelFactory {
            AnalysisViewModel(
                trainingDataSource = get(),
                trainingHistoryDataSource = get(),
                exerciseDataSource = get(),
                exerciseHistoryDatasource = get()
            )
        }

    fun getHistoryScreenViewModelFactory() =
        viewModelFactory {
            HistoryScreenViewModel(trainingHistoryDataSource = get())
        }

    fun getCreateTrainingViewModelFactory() =
        viewModelFactory {
            CreateTrainingViewModel(trainingDataSource = get())
        }

    fun getHistoryInfoViewModelFactory(trainingId: Long) =
        viewModelFactory {
            HistoryInfoViewModel(
                trainingHistoryDataSource = get(),
                exerciseHistoryDatasource = get(),
                trainingHistoryId = trainingId
            )
        }

    fun getThisTrainingViewModelFactory() =
        viewModelFactory {
            ThisTrainingViewModel(
                trainingHistoryDataSource = get(),
                exerciseDatasource = get(),
                exerciseHistoryDatasource = get(),
                summaryDatasource = get()
            )
        }

    fun getAddExercisesViewModelFactory() =
        viewModelFactory {
            AddExercisesViewModel(
                useCaseProvider = get()
            )
        }

    fun getCreateExerciseViewModelFactory() =
        viewModelFactory {
            CreateExerciseViewModel(
                provider = get(),
                trainingHistoryDataSource = get()
            )
        }

    fun getExerciseAtWorkViewModelFactory(trainingId: Long, exerciseTemplateId: Long) =
        viewModelFactory {
            ExerciseAtWorkViewModel(
                useCaseProvider = get(),
                trainingId = trainingId,
                exerciseTemplateId = exerciseTemplateId,
                notificationUtils = get()
            )
        }

    fun getExerciseAtWorkHistoryViewModelFactory(exerciseName: String) =
        viewModelFactory {
            ExerciseAtWorkHistoryViewModel(
                useCaseProvider = get(),
                exerciseName = exerciseName
            )
        }
}