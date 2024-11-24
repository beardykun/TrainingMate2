package jp.mikhail.pankratov.trainingMate.summaryFeature.domain

import androidx.compose.ui.graphics.Color
import jp.mikhail.pankratov.trainingMate.core.presentation.UiText

data class LocalBarParameters (
    val dataName: UiText,
    val data: List<Double>,
    val barColor: Color = Color.Blue,
)