package jp.mikhail.pankratov.trainingMate.di.local

import dev.icerock.moko.permissions.PermissionsController
import jp.mikhail.pankratov.trainingMate.AppViewModel
import jp.mikhail.pankratov.trainingMate.createTraining.presentation.CreateTrainingViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation.AnalysisViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen.HistoryInfoViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen.HistoryScreenViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen.domain.TrainingQuery
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.TrainingViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.user.presentation.UserInfoViewModel
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.addExercises.presentation.AddExercisesViewModel
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.createExercise.presentation.CreateExerciseViewModel
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.ExerciseAtWorkViewModel
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation.ViewModelArguments
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWorkHistory.presentation.ExerciseAtWorkHistoryViewModel
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseSettings.presentation.ExerciseSettingsViewModel
import jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.thisTraining.presentation.ThisTrainingViewModel
import jp.mikhail.pankratov.trainingMate.summaryFeature.presentation.SummaryViewModel
import jp.mikhail.pankratov.trainingMate.trainigSelection.presentation.TrainingSelectionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun viewModelModule() = module {
    viewModel(named("ThisTrainingViewModel")) {
        ThisTrainingViewModel(
            trainingUseCaseProvider = get(),
            exerciseUseCaseProvider = get(),
            summaryUseCaseProvider = get(),
            removeTrainingExerciseUseCase = get(),
            trainingScoreUseCase = get()
        )
    }

    viewModel(named("AppViewModel")) {
        AppViewModel(
            trainingUseCaseProvider = get(),
            exerciseUseCaseProvider = get()
        )
    }

    viewModel(named("TrainingSelectionViewModel")) {
        TrainingSelectionViewModel(
            trainingUseCaseProvider = get(),
            summaryUseCaseProvider = get()
        )
    }

    viewModel(named("TrainingViewModel")) {
        TrainingViewModel(
            trainingUseCaseProvider = get(),
            summaryUseCaseProvider = get()
        )
    }

    viewModel(named("AnalysisViewModel")) {
        AnalysisViewModel(
            trainingUseCaseProvider = get(),
            exerciseUseCaseProvider = get()
        )
    }

    viewModel(named("HistoryScreenViewModel")) { (query: TrainingQuery) ->
        HistoryScreenViewModel(
            trainingUseCaseProvider = get(),
            query = query
        )
    }

    viewModel(named("CreateTrainingViewModel")) {
        CreateTrainingViewModel(trainingUseCaseProvider = get())
    }

    viewModel(named("HistoryInfoViewModel")) { (trainingId: Long) ->
        HistoryInfoViewModel(
            trainingUseCaseProvider = get(),
            exerciseUseCaseProvider = get(),
            trainingHistoryId = trainingId
        )
    }

    viewModel(named("AddExercisesViewModel")) {
        AddExercisesViewModel(
            trainingUseCaseProvider = get(),
            exerciseUseCaseProvider = get()
        )
    }

    viewModel(named("CreateExerciseViewModel")) {
        CreateExerciseViewModel(
            exerciseUseCaseProvider = get(),
            trainingUseCaseProvider = get()
        )
    }

    viewModel(named("ExerciseAtWorkViewModel")) { (viewModelArguments: ViewModelArguments, permissionsController: PermissionsController) ->
        ExerciseAtWorkViewModel(
            trainingUseCaseProvider = get(),
            exerciseUseCaseProvider = get(),
            exerciseSettingsUseCaseProvider = get(),
            updateAutoInputUseCase = get(),
            validateNumericInputUseCase = get(),
            utilsProvider = get(),
            viewModelArguments = viewModelArguments,
            permissionsController = permissionsController
        )
    }

    viewModel(named("ExerciseAtWorkHistoryViewModel")) { (exerciseName: String, trainingTemplateId: Long) ->
        ExerciseAtWorkHistoryViewModel(
            exerciseUseCaseProvider = get(),
            exerciseName = exerciseName,
            trainingTemplateId = trainingTemplateId
        )
    }

    viewModel(named("ExerciseSettingsViewModel")) { (trainingTemplateId: Long, exerciseTemplateId: Long) ->
        ExerciseSettingsViewModel(
            exerciseSettingsUseCaseProvider = get(),
            validateNumericInputUseCase = get(),
            trainingTemplateId = trainingTemplateId,
            exerciseTemplateId = exerciseTemplateId
        )
    }

    viewModel(named("UserInfoViewModel")) {
        UserInfoViewModel(
            exerciseUseCaseProvider = get()
        )
    }

    viewModel(named("SummaryViewModel")) { (stringsToPass: List<String>) ->
        SummaryViewModel(
            summaryUseCaseProvider = get(),
            stringsToPass = stringsToPass
        )
    }
}