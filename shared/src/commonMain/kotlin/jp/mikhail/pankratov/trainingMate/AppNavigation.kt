package jp.mikhail.pankratov.trainingMate

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.EXERCISE_NAME
import jp.mikhail.pankratov.trainingMate.core.presentation.EXERCISE_TEMPLATE_ID
import jp.mikhail.pankratov.trainingMate.core.presentation.MONTH_NUM
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.TRAINING_HISTORY_ID
import jp.mikhail.pankratov.trainingMate.core.presentation.TRAINING_TEMPLATE_ID
import jp.mikhail.pankratov.trainingMate.core.presentation.WEEK_NUM
import jp.mikhail.pankratov.trainingMate.core.presentation.YEAR
import jp.mikhail.pankratov.trainingMate.createTraining.presentation.CreateTrainingScreen
import jp.mikhail.pankratov.trainingMate.createTraining.presentation.CreateTrainingViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation.AnalysisScreen
import jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation.AnalysisViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen.HistoryInfoScreen
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen.HistoryInfoViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen.HistoryScreen
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen.HistoryScreenViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen.domain.TrainingQuery
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.TrainingScreen
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.TrainingViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.user.presentation.UserInfoScreen
import jp.mikhail.pankratov.trainingMate.mainScreens.user.presentation.UserInfoViewModel
import jp.mikhail.pankratov.trainingMate.summaryFeature.presentation.SummaryScreen
import jp.mikhail.pankratov.trainingMate.summaryFeature.presentation.SummaryViewModel
import jp.mikhail.pankratov.trainingMate.trainigSelection.presentation.TrainingSelectionScreen
import jp.mikhail.pankratov.trainingMate.trainigSelection.presentation.TrainingSelectionViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation.AddExercisesScreen
import jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation.AddExercisesViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.presentation.CreateExerciseScreen
import jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.presentation.CreateExerciseViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.ExerciseAtWorkScreen
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.ExerciseAtWorkViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.ViewModelArguments
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.presentation.ExerciseAtWorkHistoryScreen
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.presentation.ExerciseAtWorkHistoryViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.presentation.ThisTrainingScreen
import jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.presentation.ThisTrainingViewModel
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.last_month_summary
import maxrep.shared.generated.resources.last_week_summary
import maxrep.shared.generated.resources.notification_permission_denied
import maxrep.shared.generated.resources.notification_permission_required
import maxrep.shared.generated.resources.this_month_summary
import maxrep.shared.generated.resources.this_week_summary
import maxrep.shared.generated.resources.week
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

@Composable
fun NavHost(navigator: Navigator) {
    moe.tlaster.precompose.navigation.NavHost(
        navigator = navigator,
        navTransition = NavTransition(),
        initialRoute = Routs.MainScreens.training.title
    ) {
        scene(route = Routs.MainScreens.training.title, navTransition = NavTransition()) {
            val viewModel: TrainingViewModel = koinViewModel(qualifier = named("TrainingViewModel"))

            val state by viewModel.state.collectAsStateWithLifecycle()
            TrainingScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navigator = navigator
            )
        }

        scene(route = Routs.MainScreens.analysis.title, navTransition = NavTransition()) {
            val viewModel: AnalysisViewModel = koinViewModel(qualifier = named("AnalysisViewModel"))
            val state by viewModel.state.collectAsStateWithLifecycle()
            AnalysisScreen(state = state, onEvent = viewModel::onEvent, navigator = navigator)
        }
        scene(
            route = "${Routs.MainScreens.history.title}/{$YEAR}/{$MONTH_NUM}/{$WEEK_NUM}",
            navTransition = NavTransition()
        ) { backStackEntry ->
            val year: Long = backStackEntry.path(YEAR) ?: 0
            val monthNum: Long? = backStackEntry.path(MONTH_NUM)
            val weekNum: Long? = backStackEntry.path(WEEK_NUM)

            var query: TrainingQuery = TrainingQuery.All
            monthNum?.let {
                query = TrainingQuery.Month(month = monthNum, year = year)
            }
            weekNum?.let {
                query = TrainingQuery.Week(week = weekNum, year = year)
            }
            val viewModel: HistoryScreenViewModel =
                koinViewModel(qualifier = named("HistoryScreenViewModel")) { parametersOf(query) }
            val state by viewModel.state.collectAsStateWithLifecycle()
            HistoryScreen(state = state, onEvent = viewModel::onEvent, navigator = navigator)
        }
        scene(route = Routs.TrainingScreens.createTraining, navTransition = NavTransition()) {
            val viewModel: CreateTrainingViewModel =
                koinViewModel(qualifier = named("CreateTrainingViewModel"))
            val state by viewModel.state.collectAsStateWithLifecycle()
            CreateTrainingScreen(state = state, onEvent = viewModel::onEvent, navigator = navigator)
        }

        scene(route = Routs.MainScreens.userInfo.title, navTransition = NavTransition()) {
            val viewModel: UserInfoViewModel =
                koinViewModel(qualifier = named("UserInfoViewModel"))
            val state by viewModel.state.collectAsStateWithLifecycle()
            UserInfoScreen(state = state, navigator = navigator)
        }

        trainingScreens(navigator)
        historyScreens(navigator)
        summaryScreens(navigator)
    }
}

private fun RouteBuilder.summaryScreens(
    navigator: Navigator
) {
    group(
        route = Routs.SummaryScreens.summaryScreensRoot,
        initialRoute = Routs.SummaryScreens.summaryScreen
    ) {
        scene(route = Routs.SummaryScreens.summaryScreen, navTransition = NavTransition()) {
            val stringsToPass = listOf(
                Res.string.last_week_summary.getString(),
                Res.string.this_week_summary.getString(),
                Res.string.last_month_summary.getString(),
                Res.string.this_month_summary.getString(),
                Res.string.week.getString()
            )
            val viewModel: SummaryViewModel =
                koinViewModel(qualifier = named("SummaryViewModel")) { parametersOf(stringsToPass) }
            val state by viewModel.state.collectAsStateWithLifecycle()
            SummaryScreen(state = state, onEvent = viewModel::onEvent)
        }
    }
}

private fun RouteBuilder.historyScreens(
    navigator: Navigator
) {
    scene(
        route = "${Routs.HistoryScreens.historyInfo}/{$TRAINING_HISTORY_ID}",
        navTransition = NavTransition()
    ) { backStackEntry ->
        val trainingId: Long = backStackEntry.path(TRAINING_HISTORY_ID) ?: -1
        val viewModel: HistoryInfoViewModel =
            koinViewModel(qualifier = named("HistoryInfoViewModel")) { parametersOf(trainingId) }
        val state by viewModel.state.collectAsStateWithLifecycle()
        HistoryInfoScreen(state = state, onEvent = viewModel::onEvent, navigator = navigator)
    }
}


private fun RouteBuilder.trainingScreens(
    navigator: Navigator
) {
    group(
        route = Routs.TrainingScreens.trainingGroupRout,
        initialRoute = Routs.TrainingScreens.selectTraining
    ) {
        scene(
            route = Routs.TrainingScreens.selectTraining,
            navTransition = NavTransition()
        ) {
            val viewModel: TrainingSelectionViewModel =
                koinViewModel(qualifier = named("TrainingSelectionViewModel"))
            val sate by viewModel.state.collectAsStateWithLifecycle()
            TrainingSelectionScreen(
                state = sate,
                onEvent = viewModel::onEvent,
                navigator = navigator
            )
        }
        scene(
            route = Routs.TrainingScreens.trainingExercises,
            navTransition = NavTransition()
        ) {
            val viewModel: ThisTrainingViewModel =
                koinViewModel(qualifier = named("ThisTrainingViewModel"))
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
            val viewModel: AddExercisesViewModel =
                koinViewModel(qualifier = named("AddExercisesViewModel"))
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
            val viewModel: CreateExerciseViewModel =
                koinViewModel(qualifier = named("CreateExerciseViewModel"))
            val state by viewModel.state.collectAsStateWithLifecycle()
            CreateExerciseScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navigator = navigator
            )
        }

        scene(
            route = "${Routs.ExerciseScreens.exerciseAtWork}/{$TRAINING_HISTORY_ID}/{$EXERCISE_TEMPLATE_ID}/{$TRAINING_TEMPLATE_ID}",
            navTransition = NavTransition()
        ) { backStackEntry ->
            val trainingId: Long = backStackEntry.path(TRAINING_HISTORY_ID) ?: -1
            val exerciseTemplateId: Long = backStackEntry.path(EXERCISE_TEMPLATE_ID) ?: -1
            val trainingTemplateId: Long = backStackEntry.path(TRAINING_TEMPLATE_ID) ?: -1
            val permissionRequest = stringResource(Res.string.notification_permission_required)
            val permissionDenied = stringResource(Res.string.notification_permission_denied)
            val viewModelArguments = ViewModelArguments(
                trainingId = trainingId,
                exerciseTemplateId = exerciseTemplateId,
                trainingTemplateId = trainingTemplateId,
                permissionRequest = permissionRequest,
                permissionDenied = permissionDenied
            )
            val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()

            val viewModel: ExerciseAtWorkViewModel =
                koinViewModel(qualifier = named("ExerciseAtWorkViewModel")) {
                    parametersOf(
                        viewModelArguments,
                        factory.createPermissionsController()
                    )
                }
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
            val viewModel: ExerciseAtWorkHistoryViewModel =
                koinViewModel(qualifier = named("ExerciseAtWorkHistoryViewModel"))
                { parametersOf(exerciseName) }
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
        selectedIcon = Icons.Filled.Home,
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
    ),
    BottomNavigationItem(
        title = Routs.MainScreens.userInfo.title,
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info,
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
            "${Routs.MainScreens.history.title}/${null}/${null}/${null}"
        )

        Routs.MainScreens.userInfo.position -> navigator.navigate(
            Routs.MainScreens.userInfo.title
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
