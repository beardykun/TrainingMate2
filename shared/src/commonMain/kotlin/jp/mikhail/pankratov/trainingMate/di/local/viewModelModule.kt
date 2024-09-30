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
import jp.mikhail.pankratov.trainingMate.trainigSelection.presentation.TrainingSelectionViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation.AddExercisesViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.presentation.CreateExerciseViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.ExerciseAtWorkViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.presentation.ExerciseAtWorkHistoryViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.presentation.ThisTrainingViewModel
import org.koin.dsl.module

fun viewModelModule() = module {
    factory {
        ThisTrainingViewModel(
            trainingUseCaseProvider = get(),
            exerciseUseCaseProvider = get(),
            summaryUseCaseProvider = get()
        )
    }

    factory {
        AppViewModel(
            trainingUseCaseProvider = get(),
            exerciseUseCaseProvider = get()
        )
    }

    factory {
        TrainingSelectionViewModel(
            trainingUseCaseProvider = get(),
            summaryUseCaseProvider = get()
        )
    }

    factory {
        TrainingViewModel(
            trainingUseCaseProvider = get(),
            summaryUseCaseProvider = get()
        )
    }

    factory {
        AnalysisViewModel(
            trainingUseCaseProvider = get(),
            exerciseUseCaseProvider = get()
        )
    }

    factory { (query: TrainingQuery) ->
        HistoryScreenViewModel(
            trainingUseCaseProvider = get(),
            query = query
        )
    }

    factory {
        CreateTrainingViewModel(trainingUseCaseProvider = get())
    }

    factory { (trainingId: Long) ->
        HistoryInfoViewModel(
            trainingUseCaseProvider = get(),
            exerciseUseCaseProvider = get(),
            trainingHistoryId = trainingId
        )
    }

    factory {
        AddExercisesViewModel(
            trainingUseCaseProvider = get(),
            exerciseUseCaseProvider = get()
        )
    }

    factory {
        CreateExerciseViewModel(
            exerciseUseCaseProvider = get(),
            trainingUseCaseProvider = get()
        )
    }

    factory { (trainingId: Long, exerciseTemplateId: Long, trainingTemplateId: Long, permissionsController: PermissionsController) ->
        ExerciseAtWorkViewModel(
            trainingUseCaseProvider = get(),
            exerciseUseCaseProvider = get(),
            updateAutoInputUseCase = get(),
            validateInputUseCase = get(),
            trainingId = trainingId,
            exerciseTemplateId = exerciseTemplateId,
            trainingTemplateId = trainingTemplateId,
            utilsProvider = get(),
            permissionsController = permissionsController
        )
    }

    factory {(exerciseName: String) ->
        ExerciseAtWorkHistoryViewModel(
            exerciseUseCaseProvider = get(),
            exerciseName = exerciseName
        )
    }

    factory {
        UserInfoViewModel(
            exerciseUseCaseProvider = get()
        )
    }
}