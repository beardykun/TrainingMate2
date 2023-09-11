package jp.mikhail.pankratov.trainingMate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cabin
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MobileOff
import androidx.compose.material.icons.outlined.Cabin
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MobileOff
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import jp.mikhail.pankratov.trainingMate.core.presentation.AppViewModel
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.TrainingMateTheme
import jp.mikhail.pankratov.trainingMate.di.AppModule
import jp.mikhail.pankratov.trainingMate.mainSccreeens.achivements.presentation.AchievementScreen
import jp.mikhail.pankratov.trainingMate.mainSccreeens.analysis.presentation.AnalysisScreen
import jp.mikhail.pankratov.trainingMate.mainSccreeens.analysis.presentation.HistiryScreen
import jp.mikhail.pankratov.trainingMate.mainSccreeens.training.presentation.TrainingScreen
import jp.mikhail.pankratov.trainingMate.mainSccreeens.training.presentation.TrainingViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
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
            Scaffold(
                bottomBar = {
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
            ) {
                NavHost(navigator)
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
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false
    ),
    BottomNavigationItem(
        title = Routs.MainScreens.analysis.title,
        selectedIcon = Icons.Filled.Explore,
        unselectedIcon = Icons.Outlined.Explore,
        hasNews = false
    ),
    BottomNavigationItem(
        title = Routs.MainScreens.achievement.title,
        selectedIcon = Icons.Filled.MobileOff,
        unselectedIcon = Icons.Outlined.MobileOff,
        hasNews = false
    ),
    BottomNavigationItem(
        title = Routs.MainScreens.history.title,
        selectedIcon = Icons.Filled.Cabin,
        unselectedIcon = Icons.Outlined.Cabin,
        hasNews = false
    )
)

@Composable
fun NavHost(navigator: Navigator) {
    NavHost(
        navigator = navigator,
        navTransition = NavTransition(),
        initialRoute = Routs.MainScreens.training.title
    ) {
        scene(route = Routs.MainScreens.training.title, navTransition = NavTransition()) {
            val viewModel = getViewModel(
                key = Routs.MainScreens.training.title,
                factory = viewModelFactory {
                    TrainingViewModel()
                })

            val state by viewModel.state.collectAsState()
            TrainingScreen(
                state = state,
                onEvent = viewModel::onEvent,
                navigator = navigator
            )
        }

        scene(route = Routs.MainScreens.analysis.title, navTransition = NavTransition()) {
            AnalysisScreen(navigator)
        }
        scene(route = Routs.MainScreens.achievement.title, navTransition = NavTransition()) {
            AchievementScreen(navigator)
        }
        scene(route = Routs.MainScreens.history.title, navTransition = NavTransition()) {
            HistiryScreen(navigator)
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

