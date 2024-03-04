package jp.mikhail.pankratov.trainingMate

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.TrainingMateTheme
import jp.mikhail.pankratov.trainingMate.createTraining.presentation.CreateTrainingScreen
import jp.mikhail.pankratov.trainingMate.createTraining.presentation.CreateTrainingViewModel
import jp.mikhail.pankratov.trainingMate.di.AppModule
import jp.mikhail.pankratov.trainingMate.mainScreens.achivements.presentation.AchievementScreen
import jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation.AnalysisScreen
import jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation.AnalysisViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen.HistoryInfoScreen
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyInfoScreen.HistoryInfoViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen.HistoryScreen
import jp.mikhail.pankratov.trainingMate.mainScreens.history.presentation.historyScreen.HistoryScreenViewModel
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.TrainingScreen
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.TrainingViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation.AddExercisesScreen
import jp.mikhail.pankratov.trainingMate.trainingFeature.addExercises.presentation.AddExercisesViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.CreateExerciseScreen
import jp.mikhail.pankratov.trainingMate.trainingFeature.createExercise.CreateExerciseViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.ExerciseAtWorkScreen
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.ExerciseAtWorkViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.presentation.ExerciseAtWorkHistoryScreen
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWorkHistory.presentation.ExerciseAtWorkHistoryViewModel
import jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.presentation.ThisTrainingScreen
import jp.mikhail.pankratov.trainingMate.trainingFeature.thisTraining.presentation.ThisTrainingViewModel
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule
) {
    TrainingMateTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        val viewModel = getViewModel(
            key = "main_vm",
            factory = viewModelFactory {
                AppViewModel(
                    trainingDataSource = appModule.trainingDataSource,
                    exerciseDataSource = appModule.exerciseDataSource
                )
            }
        )
        viewModel.insertDefaultTraining()

        val navigator = rememberNavigator()

        val items = bottomNavigationItems()

        var selectedIndex by rememberSaveable { mutableStateOf(0) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            val current by navigator.currentEntry.collectAsStateWithLifecycle(null)

            Scaffold(
                topBar = {
                    TopAppBar(title = {
                        Text(
                            text = current?.route?.route?.split("/")?.first() ?: "",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = Dimens.Padding32)
                        )
                    },
                        navigationIcon = {
                            if (!Routs.MainScreens.mainScreens.contains(current?.route?.route)) {

                                IconButton(onClick = {
                                    navigator.popBackStack()
                                }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    )
                },
                bottomBar = {
                    if (!Routs.MainScreens.mainScreens.contains(current?.route?.route)) return@Scaffold
                    NavigationBar {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selectedIndex == index,
                                onClick = {
                                    selectedIndex = index
                                    navigateOnTabClick(index, navigator)
                                },
                                icon = {
                                    BadgedBox(
                                        badge = {
                                            if (item.badgeCount != null) {
                                                Badge {
                                                    Text(text = item.badgeCount.toString())
                                                }
                                            } else if (item.hasNews) {
                                                Badge()
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector =
                                            if (index == selectedIndex) item.selectedIcon else item.unselectedIcon,
                                            contentDescription = item.title
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            ) { paddings ->
                Column(modifier = Modifier.padding(paddings)) {
                    NavHost(navigator, appModule)
                }
            }
        }
    }
}

private fun navigateOnTabClick(index: Int, navigator: Navigator) {
    when (index) {
        Routs.MainScreens.training.position -> navigator.navigate(
            Routs.MainScreens.training.title
        )

        Routs.MainScreens.analysis.position -> navigator.navigate(
            Routs.MainScreens.analysis.title
        )

        Routs.MainScreens.achievement.position -> navigator.navigate(
            Routs.MainScreens.achievement.title
        )

        Routs.MainScreens.history.position -> navigator.navigate(
            Routs.MainScreens.history.title
        )

        else -> {}
    }
}

private fun bottomNavigationItems() = listOf(
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
        title = Routs.MainScreens.achievement.title,
        selectedIcon = Icons.Filled.EmojiEvents,
        unselectedIcon = Icons.Outlined.EmojiEvents,
        hasNews = false
    ),
    BottomNavigationItem(
        title = Routs.MainScreens.history.title,
        selectedIcon = Icons.Filled.History,
        unselectedIcon = Icons.Outlined.History,
        hasNews = false
    )
)

@Composable
fun NavHost(navigator: Navigator, appModule: AppModule) {
    NavHost(
        navigator = navigator,
        navTransition = NavTransition(),
        initialRoute = Routs.MainScreens.training.title
    ) {
        scene(route = Routs.MainScreens.training.title, navTransition = NavTransition()) {
            val viewModel = getViewModel(
                key = Routs.MainScreens.training.title,
                factory = viewModelFactory {
                    TrainingViewModel(
                        trainingDataSource = appModule.trainingDataSource,
                        trainingHistoryDataSource = appModule.trainingHistoryDataSource
                    )
                })

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
                factory = viewModelFactory {
                    AnalysisViewModel(
                        trainingDataSource = appModule.trainingDataSource,
                        trainingHistoryDataSource = appModule.trainingHistoryDataSource,
                        exerciseDataSource = appModule.exerciseDataSource,
                        exerciseHistoryDatasource = appModule.exerciseHistoryDataSource
                    )
                })
            val state by viewModel.state.collectAsStateWithLifecycle()
            AnalysisScreen(state = state, onEvent = viewModel::onEvent, navigator = navigator)
        }
        scene(route = Routs.MainScreens.achievement.title, navTransition = NavTransition()) {
            AchievementScreen(navigator = navigator)
        }
        scene(route = Routs.MainScreens.history.title, navTransition = NavTransition()) {
            val viewModel = getViewModel(
                key = Routs.MainScreens.history,
                factory = viewModelFactory {
                    HistoryScreenViewModel(trainingHistoryDataSource = appModule.trainingHistoryDataSource)
                })
            val state by viewModel.state.collectAsStateWithLifecycle()
            HistoryScreen(state = state, onEvent = viewModel::onEvent, navigator = navigator)
        }
        scene(route = Routs.TrainingScreens.createTraining, navTransition = NavTransition()) {
            val viewModel = getViewModel(
                key = Routs.TrainingScreens.createTraining,
                factory = viewModelFactory {
                    CreateTrainingViewModel(trainingDataSource = appModule.trainingDataSource)
                }
            )
            val state by viewModel.state.collectAsStateWithLifecycle()
            CreateTrainingScreen(state = state, onEvent = viewModel::onEvent, navigator = navigator)
        }

        trainingScreens(appModule, navigator)
        historyScreens(appModule, navigator)
    }
}

private fun RouteBuilder.historyScreens(
    appModule: AppModule,
    navigator: Navigator
) {
    group(
        route = Routs.HistoryScreens.historyGroupRoot,
        initialRoute = "${Routs.HistoryScreens.historyInfo}/{trainingHistoryId}"
    ) {
        scene(
            route = "${Routs.HistoryScreens.historyInfo}/{trainingHistoryId}",
            navTransition = NavTransition()
        ) { backStackEntry ->
            val trainingId: Long = backStackEntry.path("trainingHistoryId") ?: -1
            val viewModel = getViewModel(
                key = Routs.HistoryScreens.historyInfo,
                viewModelFactory {
                    HistoryInfoViewModel(
                        trainingHistoryDataSource = appModule.trainingHistoryDataSource,
                        exerciseHistoryDatasource = appModule.exerciseHistoryDataSource,
                        trainingHistoryId = trainingId
                    )
                }
            )
            val state by viewModel.state.collectAsStateWithLifecycle()
            HistoryInfoScreen(state = state, onEvent = viewModel::onEvent, navigator = navigator)
        }
    }
}


private fun RouteBuilder.trainingScreens(
    appModule: AppModule,
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
                factory = viewModelFactory {
                    ThisTrainingViewModel(
                        trainingHistoryDataSource = appModule.trainingHistoryDataSource,
                        exerciseDatasource = appModule.exerciseDataSource,
                        exerciseHistoryDatasource = appModule.exerciseHistoryDataSource,
                    )
                }
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
                factory = viewModelFactory {
                    AddExercisesViewModel(
                        trainingDataSource = appModule.trainingDataSource,
                        exerciseDatasource = appModule.exerciseDataSource,
                        trainingHistoryDataSource = appModule.trainingHistoryDataSource
                    )
                })
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
                factory = viewModelFactory {
                    CreateExerciseViewModel(
                        exerciseDatasource = appModule.exerciseDataSource,
                        trainingHistoryDataSource = appModule.trainingHistoryDataSource
                    )
                })
            val state by viewModel.state.collectAsStateWithLifecycle()
            CreateExerciseScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navigator = navigator
            )
        }

        scene(
            route = "${Routs.ExerciseScreens.exerciseAtWork}/{trainingId}/{exerciseTemplateId}",
            navTransition = NavTransition()
        ) { backStackEntry ->
            val trainingId: Long = backStackEntry.path("trainingId") ?: -1
            val exerciseTemplateId: Long = backStackEntry.path("exerciseTemplateId") ?: -1
            val viewModel = getViewModel(
                key = Routs.ExerciseScreens.exerciseAtWork,
                factory = viewModelFactory {
                    ExerciseAtWorkViewModel(
                        exerciseHistoryDatasource = appModule.exerciseHistoryDataSource,
                        trainingHistoryDataSource = appModule.trainingHistoryDataSource,
                        exerciseDataSource = appModule.exerciseDataSource,
                        trainingId = trainingId,
                        exerciseTemplateId = exerciseTemplateId,
                        notificationUtils = appModule.notificationUtils
                    )
                }
            )
            val state by viewModel.state.collectAsStateWithLifecycle()
            ExerciseAtWorkScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navigator = navigator
            )
        }

        scene(
            route = "${Routs.ExerciseScreens.exerciseAtWorkHistory}/{exerciseName}",
            navTransition = NavTransition()
        ) { backStackEntry ->
            val exerciseName: String = backStackEntry.path("exerciseName") ?: ""
            val viewModel = getViewModel(
                key = Routs.ExerciseScreens.exerciseAtWorkHistory,
                factory = viewModelFactory {
                    ExerciseAtWorkHistoryViewModel(
                        exerciseHistoryDatasource = appModule.exerciseHistoryDataSource,
                        exerciseName = exerciseName
                    )
                }
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

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

