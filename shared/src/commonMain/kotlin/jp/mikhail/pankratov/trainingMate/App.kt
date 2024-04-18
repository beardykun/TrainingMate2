package jp.mikhail.pankratov.trainingMate

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextAlign
import dev.icerock.moko.mvvm.compose.getViewModel
import jp.mikhail.pankratov.trainingMate.core.presentation.Routs
import jp.mikhail.pankratov.trainingMate.core.presentation.TrainingMateTheme
import jp.mikhail.pankratov.trainingMate.di.AppModule
import jp.mikhail.pankratov.trainingMate.di.ViewModelsFac
import jp.mikhail.pankratov.trainingMate.di.domainUseCasesModule
import jp.mikhail.pankratov.trainingMate.di.local.dataSourcesModule
import jp.mikhail.pankratov.trainingMate.di.local.exerciseUseCaseModule
import jp.mikhail.pankratov.trainingMate.di.local.summaryUseCaseModule
import jp.mikhail.pankratov.trainingMate.di.local.trainingUseCaseModule
import jp.mikhail.pankratov.trainingMate.di.utilsModule
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.KoinApplication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule
) {
    PreComposeApp {
        KoinApplication(application = {
            modules(
                listOf(
                    dataSourcesModule(appModule),
                    utilsModule(appModule),
                    trainingUseCaseModule(),
                    exerciseUseCaseModule(),
                    summaryUseCaseModule(),
                    domainUseCasesModule()
                )
            )
        }) {
            TrainingMateTheme(
                darkTheme = darkTheme,
                dynamicColor = dynamicColor
            ) {
                val viewModel = getViewModel(
                    key = "main_vm",
                    factory = ViewModelsFac.getAppViewModelFactory()
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
                            NavHost(navigator)
                        }
                    }
                }
            }
        }
    }
}





