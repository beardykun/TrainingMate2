package jp.mikhail.pankratov.trainingMate.android

import android.os.Bundle
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import jp.mikhail.pankratov.trainingMate.App
import jp.mikhail.pankratov.trainingMate.di.AppModule
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent

class MainActivity : PreComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App(
                        darkTheme = isSystemInDarkTheme(),
                        dynamicColor = true,
                        appModule = AppModule(context = LocalContext.current.applicationContext)
                    )
                }
            }
        }
    }
}
