package jp.mikhail.pankratov.trainingMate.core.presentation
const val YEAR = "year"
const val MONTH_NUM = "monthNum"
const val WEEK_NUM = "weekNum"
const val TRAINING_HISTORY_ID = "trainingHistoryId"
const val EXERCISE_TEMPLATE_ID = "exerciseTemplateId"
const val TRAINING_TEMPLATE_ID = "trainingTemplateId"
const val EXERCISE_NAME = "exerciseName"
object Routs {

    object MainScreens {
        val training = Screen("Feature Training", 0)
        val analysis = Screen("Feature Analysis", 1)
        val history = Screen("Feature History", 2)
        val userInfo = Screen("Feature User Info", 3)
        val mainScreens = listOf(training.title, analysis.title, history.title, userInfo.title)
    }

    object TrainingScreens {
        val trainingGroupRout = "Training Selection"
        val trainingExercises = "Training Exercises"
        val createTraining = "Create Training"
        val addExercises = "Add Exercises"
        val createExercise = "Create Exercise"
        val selectTraining = "Select Training"
    }

    object HistoryScreens {
        val historyGroupRoot = "History Group"
        val historyInfo = "Info History"
    }

    object ExerciseScreens {
        val exerciseAtWork = "Exercise At Work"
        val exerciseAtWorkHistory = "Exercise History"
        val exerciseSettings = "Exercise Settings"
    }

    object SummaryScreens {
        val summaryScreensRoot = "summaryScreensRoot"
        val summaryScreen = "Summary Screen"
    }
}

data class Screen(val title: String, val position: Int = 0)
