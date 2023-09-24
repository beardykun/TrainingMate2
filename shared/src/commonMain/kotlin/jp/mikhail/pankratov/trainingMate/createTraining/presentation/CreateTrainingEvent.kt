package jp.mikhail.pankratov.trainingMate.createTraining.presentation

sealed class CreateTrainingEvent {
    data class OnTrainingNameChanged(val name: String) : CreateTrainingEvent()
    data class OnTrainingGroupsChanged(val group: String) : CreateTrainingEvent()
    data object OnAddNewTraining: CreateTrainingEvent()
}