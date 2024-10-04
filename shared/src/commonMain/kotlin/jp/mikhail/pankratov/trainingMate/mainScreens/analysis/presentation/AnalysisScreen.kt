package jp.mikhail.pankratov.trainingMate.mainScreens.analysis.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables.ExerciseItem
import jp.mikhail.pankratov.trainingMate.mainScreens.training.presentation.composables.LocalTrainingItem
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun AnalysisScreen(
    state: AnalysisScreenSate,
    onEvent: (AnalysisScreenEvent) -> Unit,
    navigator: Navigator
) {
    if (!state.historyTrainings.isNullOrEmpty()) {
        LaunchedEffect(Unit) {
            onEvent(AnalysisScreenEvent.OnGeneralSelected)
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TabsComposable(
            chartLabel = state.chartLabel,
            categories = listOf(
                MetricsMode.GENERAL,
                MetricsMode.TRAINING,
                MetricsMode.EXERCISE
            ),
            metricsMode = state.metricsMode,
            metricsData = state.metricsData,
            metricsXAxisData = state.metricsXAxisData,
            analysisMode = state.analysisMode.name,
            isDropdownExpanded = state.isDropdownExpanded,
            onEvent = onEvent
        )

        if (state.metricsMode == MetricsMode.TRAINING && !state.graphDisplayed) {
            state.localTrainings?.let { localTrainings ->
                TrainingChoice(localTrainings) { trainingId, name ->
                    onEvent(AnalysisScreenEvent.OnTrainingIdSelected(trainingId, name))
                }
            }
        }
        if (state.metricsMode == MetricsMode.EXERCISE && !state.graphDisplayed) {
            state.localExercises?.let { localExercises ->
                ExerciseNameChoice(localExercises = localExercises) { exerciseName ->
                    onEvent(AnalysisScreenEvent.OnExerciseNameSelected(exerciseName))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseNameChoice(localExercises: List<ExerciseLocal>, onItemClick: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TextLarge(text = stringResource(SharedRes.strings.select_exercise))
        LazyColumn {
            items(
                items = localExercises,
                key = { item ->
                    item.name
                }) { exercise ->
                ExerciseItem(
                    exerciseLocal = exercise,
                    onClick = {
                        onItemClick.invoke(it.name)
                    },
                    isStrengthDefining = exercise.isStrengthDefining,
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrainingChoice(localTrainings: List<TrainingLocal>, onItemClick: (Long, String) -> Unit) {
    TextLarge(text = stringResource(SharedRes.strings.choose_your_training).uppercase())
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(
            items = localTrainings,
            key = { item ->
                item.name
            }) { training ->
            LocalTrainingItem(
                training = training,
                onClick = {
                    training.id?.let { onItemClick.invoke(it, training.name) }
                },
                onDeleteClick = {},
                isDeletable = false,
                modifier = Modifier.animateItemPlacement()
            )
        }
    }
}