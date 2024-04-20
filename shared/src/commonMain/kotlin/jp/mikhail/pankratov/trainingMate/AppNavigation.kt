package jp.mikhail.pankratov.trainingMate

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import jp.mikhail.pankratov.trainingMate.core.presentation.EXERCISE_NAME
import jp.mikhail.pankratov.trainingMate.core.presentation.EXERCISE_TEMPLATE_ID
import jp.mikhail.pankratov.trainingMate.core.presentation.MONTH_NUM
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.TRAINING_HISTORY_ID
import jp.mikhail.pankratov.trainingMate.core.presentation.WEEK_NUM
import jp.mikhail.pankratov.trainingMate.core.presentation.YEAR
import jp.mikhail.pankratov.trainingMate.createTraining.presentation.CreateTrainingScreen
import jp.mikhail.pankratov.trainingMate.di.ViewModelsFac
import jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation.AnalysisScreen
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen.HistoryInfoScreen
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen.HistoryScreen
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen.domain.TrainingQuery
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.TrainingScreen
import jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation.AddExercisesScreen
import jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.presentation.CreateExerciseScreen
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.ExerciseAtWorkScreen
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.presentation.ExerciseAtWorkHistoryScreen
import jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.presentation.ThisTrainingScreen
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
fun NavHost(navigator: Navigator) {
    moe.tlaster.precompose.navigation.NavHost(
        navigator = navigator,
        navTransition = NavTransition(),
        initialRoute = Routs.MainScreens.training.title
    ) {
        scene(route = Routs.MainScreens.training.title, navTransition = NavTransition()) {
            val viewModel = getViewModel(
                key = Routs.MainScreens.training.title,
                factory = ViewModelsFac.getTrainingViewModelFactory()
            )

            val state by viewModel.state.collectAsStateWithLifecycle()
            TrainingScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navigator = navigator
            )
        }

        scene(route = Routs.MainScreens.analysis.title, navTransition = NavTransition()) {
            val viewModel = getViewModel(
                key = Routs.MainScreens.analysis.title,
                factory = ViewModelsFac.getAnalysisViewModelFactory()
            )
            val state by viewModel.state.collectAsStateWithLifecycle()
            AnalysisScreen(state = state, onEvent = viewModel::onEvent, navigator = navigator)
        }
        scene(
            route = "${Routs.MainScreens.history.title}/{$YEAR}/{$MONTH_NUM}/{$WEEK_NUM}",
            navTransition = NavTransition()
        ) { backStackEntry ->
            val year: Long = backStackEntry.path(YEAR) ?: -1
            val monthNum: Long? = backStackEntry.path(MONTH_NUM)
            val weekNum: Long? = backStackEntry.path(WEEK_NUM)
            println("TAGGER year: $year month: $monthNum week: $weekNum")

            var query: TrainingQuery = TrainingQuery.All
            monthNum?.let {
                query = TrainingQuery.Month(month = monthNum, year = year)
            }
            weekNum?.let {
                query = TrainingQuery.Week(week = weekNum, year = year)
            }
            println("TAGGER $query")
            val viewModel = getViewModel(
                key = Routs.MainScreens.history,
                factory = ViewModelsFac.getHistoryScreenViewModelFactory(query)
            )
            val state by viewModel.state.collectAsStateWithLifecycle()
            HistoryScreen(state = state, onEvent = viewModel::onEvent, navigator = navigator)
        }
        scene(route = Routs.TrainingScreens.createTraining, navTransition = NavTransition()) {
            val viewModel = getViewModel(
                key = Routs.TrainingScreens.createTraining,
                factory = ViewModelsFac.getCreateTrainingViewModelFactory()
            )
            val state by viewModel.state.collectAsStateWithLifecycle()
            CreateTrainingScreen(state = state, onEvent = viewModel::onEvent, navigator = navigator)
        }

        trainingScreens(navigator)
        historyScreens(navigator)
    }
}

private fun RouteBuilder.historyScreens(
    navigator: Navigator
) {
    group(
        route = Routs.HistoryScreens.historyGroupRoot,
        initialRoute = "${Routs.HistoryScreens.historyInfo}/{$TRAINING_HISTORY_ID}"
    ) {
        scene(
            route = "${Routs.HistoryScreens.historyInfo}/{$TRAINING_HISTORY_ID}",
            navTransition = NavTransition()
        ) { backStackEntry ->
            val trainingId: Long = backStackEntry.path(TRAINING_HISTORY_ID) ?: -1
            val viewModel = getViewModel(
                key = Routs.HistoryScreens.historyInfo,
                factory = ViewModelsFac.getHistoryInfoViewModelFactory(trainingId = trainingId)
            )
            val state by viewModel.state.collectAsStateWithLifecycle()
            HistoryInfoScreen(state = state, onEvent = viewModel::onEvent, navigator = navigator)
        }
    }
}


private fun RouteBuilder.trainingScreens(
    navigator: Navigator
) {
    group(
        route = Routs.TrainingScreens.trainingGroupRout,
        initialRoute = Routs.TrainingScreens.trainingExercises
    ) {
        scene(
            route = Routs.TrainingScreens.trainingExercises,
            navTransition = NavTransition()
        ) {
            val viewModel = getViewModel(
                key = Routs.TrainingScreens.trainingExercises,
                factory = ViewModelsFac.getThisTrainingViewModelFactory()
            )
            val state by viewModel.state.collectAsStateWithLifecycle()

            ThisTrainingScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navigator = navigator
            )
        }

        scene(
            route = Routs.TrainingScreens.addExercises,
            navTransition = NavTransition()
        ) {
            val viewModel = getViewModel(
                key = Routs.TrainingScreens.addExercises,
                factory = ViewModelsFac.getAddExercisesViewModelFactory()
            )
            val state by viewModel.state.collectAsStateWithLifecycle()
            AddExercisesScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navigator = navigator
            )
        }

        scene(
            route = Routs.TrainingScreens.createExercise,
            navTransition = NavTransition()
        ) {
            val viewModel = getViewModel(
                key = Routs.TrainingScreens.createExercise,
                factory = ViewModelsFac.getCreateExerciseViewModelFactory()
            )
            val state by viewModel.state.collectAsStateWithLifecycle()
            CreateExerciseScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navigator = navigator
            )
        }

        scene(
            route = "${Routs.ExerciseScreens.exerciseAtWork}/{$TRAINING_HISTORY_ID}/{$EXERCISE_TEMPLATE_ID}",
            navTransition = NavTransition()
        ) { backStackEntry ->
            val trainingId: Long = backStackEntry.path(TRAINING_HISTORY_ID) ?: -1
            val exerciseTemplateId: Long = backStackEntry.path(EXERCISE_TEMPLATE_ID) ?: -1
            val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()

            val viewModel = getViewModel(
                key = Routs.ExerciseScreens.exerciseAtWork,
                factory = ViewModelsFac.getExerciseAtWorkViewModelFactory(
                    trainingId = trainingId,
                    exerciseTemplateId = exerciseTemplateId,
                    permissionsController = factory.createPermissionsController()
                )
            )
            BindEffect(viewModel.permissionsController)
            val state by viewModel.state.collectAsStateWithLifecycle()
            ExerciseAtWorkScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navigator = navigator
            )
        }

        scene(
            route = "${Routs.ExerciseScreens.exerciseAtWorkHistory}/{$EXERCISE_NAME}",
            navTransition = NavTransition()
        ) { backStackEntry ->
            val exerciseName: String = backStackEntry.path(EXERCISE_NAME) ?: ""
            val viewModel = getViewModel(
                key = Routs.ExerciseScreens.exerciseAtWorkHistory,
                factory = ViewModelsFac.getExerciseAtWorkHistoryViewModelFactory(exerciseName = exerciseName)
            )
            val state by viewModel.state.collectAsStateWithLifecycle()
            ExerciseAtWorkHistoryScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navigator = navigator
            )
        }
    }
}

fun bottomNavigationItems() = listOf(
    BottomNavigationItem(
        title = Routs.MainScreens.training.title,
        selectedIcon = Icons.Filled.FitnessCenter,
        unselectedIcon = Icons.Outlined.FitnessCenter,
        hasNews = false
    ),
    BottomNavigationItem(
        title = Routs.MainScreens.analysis.title,
        selectedIcon = Icons.Filled.Timeline,
        unselectedIcon = Icons.Outlined.Timeline,
        hasNews = false
    ),
    BottomNavigationItem(
        title = Routs.MainScreens.history.title,
        selectedIcon = Icons.Filled.History,
        unselectedIcon = Icons.Outlined.History,
        hasNews = false
    )
)

fun navigateOnTabClick(index: Int, navigator: Navigator) {
    when (index) {
        Routs.MainScreens.training.position -> navigator.navigate(
            Routs.MainScreens.training.title
        )

        Routs.MainScreens.analysis.position -> navigator.navigate(
            Routs.MainScreens.analysis.title
        )

        Routs.MainScreens.history.position -> navigator.navigate(
            Routs.MainScreens.history.title
        )

        else -> {}
    }
}


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)
