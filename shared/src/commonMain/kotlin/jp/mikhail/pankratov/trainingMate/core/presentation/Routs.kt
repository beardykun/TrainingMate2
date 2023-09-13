package jp.mikhail.pankratov.trainingMate.core.presentation

object Routs {

    object MainScreens {
        val training = Screen("Training", 0)
        val analysis = Screen("Analysis", 1)
        val achievement = Screen("Achievement", 2)
        val history = Screen("History", 3)
    }

    object TrainingScreens {
        val trainingGroupRout = "ThisTraining"
        val trainingExercises = "TrainingExercises"
    }
}

data class Screen(val title: String, val position: Int = 0)
