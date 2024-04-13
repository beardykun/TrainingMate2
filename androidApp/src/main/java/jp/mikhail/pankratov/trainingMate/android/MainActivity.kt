package jp.mikhail.pankratov.trainingMate.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import jp.mikhail.pankratov.trainingMate.App
import jp.mikhail.pankratov.trainingMate.di.AppModule
import jp.mikhail.pankratov.trainingMate.di.dataSourcesModule
import jp.mikhail.pankratov.trainingMate.di.useCasesModel
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appModule = AppModule(context = this.applicationContext)
        startKoin {
            modules(dataSourcesModule(appModule), useCasesModel())
        }
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App(
                        darkTheme = isSystemInDarkTheme(),
                        dynamicColor = true,
                        appModule = appModule
                    )
                }
            }
        }
    }
}
