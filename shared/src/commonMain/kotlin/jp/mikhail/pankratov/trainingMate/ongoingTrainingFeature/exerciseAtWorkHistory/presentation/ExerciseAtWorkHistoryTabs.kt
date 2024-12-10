package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWorkHistory.presentation

import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.all
import maxrep.shared.generated.resources.same_training
import org.jetbrains.compose.resources.StringResource

enum class ExerciseAtWorkHistoryTabs(val title: StringResource) {
    ALL(Res.string.all),
    SAME_TRAINING(Res.string.same_training)
}
