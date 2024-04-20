package jp.mikhail.pankratov.trainingMate.core.presentation
const val YEAR = "year"
const val MONTH_NUM = "monthNum"
const val WEEK_NUM = "weekNum"
const val TRAINING_HISTORY_ID = "trainingHistoryId"
const val EXERCISE_TEMPLATE_ID = "exerciseTemplateId"
const val EXERCISE_NAME = "exerciseName"
object Routs {

    object MainScreens {
        val training = Screen("Training", 0)
        val analysis = Screen("Analysis", 1)
        val history = Screen("History", 2)
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
