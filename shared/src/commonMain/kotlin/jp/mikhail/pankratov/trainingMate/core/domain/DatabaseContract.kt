package jp.mikhail.pankratov.trainingMate.core.domain

object DatabaseContract {

    const val DATABASE_NAME = "TRAINING_DATABASE"
    const val TABLE_TRAINING = "TABLE_TRAINING"
    const val TABLE_EXERCISE = "TABLE_EXERCISE"
    const val TABLE_EXERCISE_INFO = "TABLE_EXERCISE_INFO"

    object TrainingColumns {
        const val TRAINING_NAME = "trainingName"
        const val TRAINING_NAME_WITH_DATE = "trainingNameWithDate"
        const val TRAINING_DATE = "trainingDate"
        const val TRAINING_EXERCISE_NAME = "trainingExerciseNameList"
        const val TRAINING_TIME_BETWEEN_SETS = "trainingTimeBetweenSets"
        const val TRAINING_TOTAL_TIME = "trainingTotalTime"
    }

    object ExerciseColumns {
        const val EXERCISE_NAME = "exerciseName"
        const val EXERCISE_IMAGE = "exerciseImage"
        const val EXERCISE_GROUP = "exerciseGroup"
    }

    object ExerciseInfoColumns {
        const val EXERCISE_NAME = "exerciseName"
        const val EXERCISE_TRAINING_NAME = "exerciseTrainingName"
        const val EXERCISE_REPS = "exerciseReps"
        const val EXERCISE_MAX_WEIGHT = "exerciseMaxWeight"
    }

    const val BICEPS_GROUP = "biceps"
    const val TRICEPS_GROUP = "triceps"
    const val SHOULDERS_GROUP = "shoulders"
    const val BACK_GROUP = "back"
    const val CHEST_GROUP = "chest"
    const val LEGS_GROUP = "legs"
    const val TRAPS_GROUP = "traps"
    const val ABS_GROUP = "abs"
}