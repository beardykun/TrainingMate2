package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation

data class ViewModelArguments(
    val trainingId: Long,
    val exerciseTemplateId: Long,
    val trainingTemplateId: Long,
    val permissionRequest: String,
    val permissionDenied: String
)
