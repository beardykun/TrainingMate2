package jp.mikhail.pankratov.trainingMate.ongoingTrainingFeature.exerciseAtWork.presentation

data class ViewModelArguments(
    val trainingId: Long,
    val exerciseTemplateId: Long,
    val trainingTemplateId: Long,
    val permissionRequest: String,
    val permissionDenied: String
)
