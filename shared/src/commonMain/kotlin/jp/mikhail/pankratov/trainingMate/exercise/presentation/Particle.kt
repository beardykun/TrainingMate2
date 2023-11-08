package jp.mikhail.pankratov.trainingMate.exercise.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class Particle(
    var position: Offset,
    val velocity: Offset,
    var color: Color,
    var lifespan: Int
)
