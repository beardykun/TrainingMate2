package jp.mikhail.pankratov.trainingMate.exercise.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ExerciseAtWorkScreen(
    state: ExerciseAtWorkState,
    onEvent: (ExerciseAtWorkEvent) -> Unit,
    navigator: Navigator
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = state.exercise?.name.toString(), modifier = Modifier.clickable {
            navigator.goBack()
        })
    }
}