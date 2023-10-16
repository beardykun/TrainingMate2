package jp.mikhail.pankratov.trainingMate.core.presentation

object Routs {

    object MainScreens {
        val training = Screen("Training", 0)
        val analysis = Screen("Analysis", 1)
        val achievement = Screen("Achievement", 2)
        val history = Screen("History", 3)
        val mainScreens = listOf("Training", "Analysis", "Achievement", "History")
    }

    object TrainingScreens {
        val trainingGroupRout = "TrainingGroupRout"
        val trainingExercises = "TrainingExercises"
        val createTraining = "CreateTraining"
        val addExercises = "AddExercises"
    }

    object HistoryScreens {
        val historyGroupRoot = "HistoryGroupRoot"
        val historyInfo = "HistoryInfo"
    }

    object ExerciseScreens {
        val exerciseAtWork = "ExerciseAtWork"
    }
}

data class Screen(val title: String, val position: Int = 0)
