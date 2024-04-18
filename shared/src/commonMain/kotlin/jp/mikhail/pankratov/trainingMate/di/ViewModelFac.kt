package jp.mikhail.pankratov.trainingMate.di

import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.permissions.PermissionsController
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
        viewModelFactory {
            AppViewModel(
                trainingUseCaseProvider = get(),
                exerciseUseCaseProvider = get()
            )
        }

    fun getTrainingViewModelFactory() =
        viewModelFactory {
            TrainingViewModel(
                trainingUseCaseProvider = get(),
                summaryUseCaseProvider = get()
            )
        }

    fun getAnalysisViewModelFactory() =
        viewModelFactory {
            AnalysisViewModel(
                trainingUseCaseProvider = get(),
                exerciseUseCaseProvider = get()
            )
        }

    fun getHistoryScreenViewModelFactory() =
        viewModelFactory {
            HistoryScreenViewModel(trainingUseCaseProvider = get())
        }

    fun getCreateTrainingViewModelFactory() =
        viewModelFactory {
            CreateTrainingViewModel(trainingUseCaseProvider = get())
        }

    fun getHistoryInfoViewModelFactory(trainingId: Long) =
        viewModelFactory {
            HistoryInfoViewModel(
                trainingUseCaseProvider = get(),
                exerciseUseCaseProvider = get(),
                trainingHistoryId = trainingId
            )
        }

    fun getThisTrainingViewModelFactory() =
        viewModelFactory {
            ThisTrainingViewModel(
                trainingUseCaseProvider = get(),
                exerciseUseCaseProvider = get(),
                summaryUseCaseProvider = get()
            )
        }

    fun getAddExercisesViewModelFactory() =
        viewModelFactory {
            AddExercisesViewModel(
                trainingUseCaseProvider = get(),
                exerciseUseCaseProvider = get()
            )
        }

    fun getCreateExerciseViewModelFactory() =
        viewModelFactory {
            CreateExerciseViewModel(
                exerciseUseCaseProvider = get(),
                trainingUseCaseProvider = get()
            )
        }

    fun getExerciseAtWorkViewModelFactory(
        trainingId: Long, exerciseTemplateId: Long,
        permissionsController: PermissionsController
    ) =
        viewModelFactory {
            ExerciseAtWorkViewModel(
                trainingUseCaseProvider = get(),
                exerciseUseCaseProvider = get(),
                updateAutoInputUseCase = get(),
                validateInputUseCase = get(),
                trainingId = trainingId,
                exerciseTemplateId = exerciseTemplateId,
                utilsProvider = get(),
                permissionsController = permissionsController
            )
        }

    fun getExerciseAtWorkHistoryViewModelFactory(exerciseName: String) =
        viewModelFactory {
            ExerciseAtWorkHistoryViewModel(
                exerciseUseCaseProvider = get(),
                exerciseName = exerciseName
            )
        }
}