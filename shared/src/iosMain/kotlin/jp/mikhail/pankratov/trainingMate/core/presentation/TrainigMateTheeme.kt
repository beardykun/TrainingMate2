package jp.mikhail.pankratov.trainingMate.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import jp.mikhail.pankratov.trainingMate.theme.DarkColorScheme
import jp.mikhail.pankratov.trainingMate.theme.LightColorScheme
import jp.mikhail.pankratov.trainingMate.theme.Typography

@Composable
actual fun TrainingMateTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}