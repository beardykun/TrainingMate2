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
        val trainingGroupRout = "This Training"
        val trainingExercises = "Training Exercises"
        val createTraining = "Create Training"
        val addExercises = "Add Exercises"
        val createExercise = "Create Exercise"
    }

    object HistoryScreens {
        val historyGroupRoot = "History Group"
        val historyInfo = "History Info"
    }

    object ExerciseScreens {
        val exerciseAtWork = "Exercise At Work"
        val exerciseAtWorkHistory = "Exercise History"
    }
}

data class Screen(val title: String, val position: Int = 0)
