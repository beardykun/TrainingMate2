package jp.mikhail.pankratov.trainingMate.core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun TrainingMateTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
)