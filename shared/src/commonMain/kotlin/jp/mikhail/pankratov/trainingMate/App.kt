package jp.mikhail.pankratov.trainingMate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.TrainingMateTheme
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean
) {
    PreComposeApp {
        TrainingMateTheme(
            darkTheme = darkTheme,
            dynamicColor = dynamicColor
        ) {
            val viewModel: AppViewModel =
                koinViewModel(qualifier = named("AppViewModel")) { parametersOf() }
            viewModel.insertDefaultTraining()

            val navigator = rememberNavigator()

            val items = bottomNavigationItems()

            var selectedIndex by rememberSaveable { mutableStateOf(0) }

            Surface(
                modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars),
                color = MaterialTheme.colorScheme.background,
            ) {
                val current by navigator.currentEntry.collectAsStateWithLifecycle(null)

                Scaffold(
                    bottomBar = {
                        val destination = current?.route?.route
                        if (destination == null || !Routs.MainScreens.mainScreens.any {
                                destination.startsWith(
                                    it
                                )
                            }) {
                            return@Scaffold
                        }
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
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        NavHost(navigator)
                    }
                }
            }
        }
    }
}





