package jp.mikhail.pankratov.trainingMate

import androidx.compose.ui.window.ComposeUIViewController
import jp.mikhail.pankratov.trainingMate.di.AppModule
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() = ComposeUIViewController {
    val darkTheme = UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
            UIUserInterfaceStyle.UIUserInterfaceStyleDark
    App(
        darkTheme = darkTheme,
        dynamicColor = false,
        appModule = AppModule()
    )
}