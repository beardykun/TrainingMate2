package jp.mikhail.pankratov.trainingMate

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import jp.mikhail.pankratov.trainingMate.core.domain.DatabaseContract
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.Exercise
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.Training
import jp.mikhail.pankratov.trainingMate.exercise.domain.local.IExerciseDatasource
import jp.mikhail.pankratov.trainingMate.mainScreens.training.domain.local.ITrainingDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class AppViewModel(
    private val trainingDataSource: ITrainingDataSource,
    private val exerciseDataSource: IExerciseDatasource
) : ViewModel() {

    fun insertDefaultTraining() = viewModelScope.launch(Dispatchers.IO) {
        if (!trainingDataSource.trainingTableEmpty()) return@launch
        trainingDataSource.insertTraining(
            Training(
                id = null,
                userId = "1",
                name = "Biceps Training example",
                groups = DatabaseContract.BICEPS_GROUP,
                exercises = emptyList(),
                description = "example biceps training"
            )
        )
        insertDefaultExercises()
    }

    private fun insertDefaultExercises() = viewModelScope.launch(Dispatchers.IO) {
        //biceps
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Barbell curls",
                group = DatabaseContract.BICEPS_GROUP,
                image = "barbell_curl"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Standing reverse curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "standing_reverse_curl"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Standing biceps cable curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "standing_biceps_cable_curl"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Overhead cable curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "overhead_cable_curl"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Seated dumbbell curls",
                group = DatabaseContract.BICEPS_GROUP,
                image = "seated_dumbbell_curls"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Dumbbell preacher curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "dumbbell_preacher_curl"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Preacher curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "preacher_curl"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Concentration curls",
                group = DatabaseContract.BICEPS_GROUP,
                image = "concentration_curls"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Dumbbell bicep curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "dumbbell_bicep_curl"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Dumbbell alternate bicep curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "dumbbell_alternate_bicep_curl"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Cross body hammer curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "cross_body_hammer_curl"
            )
        )
        //triceps
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Lying Triceps Press",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "lying_triceps_press"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Standing triceps press",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "standing_triceps_press"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Triceps dumbbell kickback",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "tricep_dumbbell_kickback"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Triceps push-down",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "triceps_pushdown"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Push ups close triceps position",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "push_ups_close_triceps_position"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Dips triceps",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "dips_triceps"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Close grip barbell press",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "close_grip_barbell_press"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Cable rope overhead extension",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "cable_rope_overhead_extention"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Bench dips",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "bench_dips"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Cable one arm triceps extension",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "cable_one_arm_triceps_extension"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Dumbbell one arm triceps extension",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "dumbbell_one_arm_triceps_extension"
            )
        )

        //Shoulders
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Barbell Shoulder Press",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "barbell_shoulder_press"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Shoulder Press",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "shoulder_press"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Reverse machine flyes",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "reverse_machine_flyes"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Front dumbbell raise",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "front_dumbbell_raise"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Side lateral raise",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "side_lateral_raise"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Dumbbell shoulder press",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "dumbbell_shoulder_press"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Cable rear delt fly",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "cable_rear_delt_fly"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Bent over dumbbell rear delt raise",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "bent_over_dumbbell_rear_delt_raise"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Barbell rea delt rows",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "barbell_rea_delt_rows"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Arnold dumbbell press",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "arnold_dumbbell_press"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Alternating deltoid raise",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "alternating_deltoid_raise"
            )
        )

        //back
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Chin ups",
                group = DatabaseContract.BACK_GROUP,
                image = "chin_up"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Reverse grip bent over rows",
                group = DatabaseContract.BACK_GROUP,
                image = "reverse_grip_bent_over_rows"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Lying T-bar row",
                group = DatabaseContract.BACK_GROUP,
                image = "lying_t_bar_row"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Close grip front lat pull-down",
                group = DatabaseContract.BACK_GROUP,
                image = "close_grip_front_lat_pulldown"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Bent over barbell row",
                group = DatabaseContract.BACK_GROUP,
                image = "bent_over_barbell_row"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Wide grip lat pull-down",
                group = DatabaseContract.BACK_GROUP,
                image = "wide_grip_lat_pulldown"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Underhand cable pull-down",
                group = DatabaseContract.BACK_GROUP,
                image = "underhand_cable_pulldown"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Wide grip lat pull-down",
                group = DatabaseContract.BACK_GROUP,
                image = "wide_grip_lat_pulldown"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "One arm dumbbell row",
                group = DatabaseContract.BACK_GROUP,
                image = "one_arm_dumbbell_row"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Seated cable row",
                group = DatabaseContract.BACK_GROUP,
                image = "seated_cable_row"
            )
        )

        //chest
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Bench Press",
                group = DatabaseContract.CHEST_GROUP,
                image = "bench_press"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Machine flye",
                group = DatabaseContract.CHEST_GROUP,
                image = "machine_flye"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Machine bench press",
                group = DatabaseContract.CHEST_GROUP,
                image = "machine_bench_press"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Dumbbell flyes",
                group = DatabaseContract.CHEST_GROUP,
                image = "dumbbell_flyes"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Decline dumbbell bench press",
                group = DatabaseContract.CHEST_GROUP,
                image = "decline_dumbbell_bench_press"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Decline bench press",
                group = DatabaseContract.CHEST_GROUP,
                image = "decline_bench_press"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Barbell incline bench",
                group = DatabaseContract.CHEST_GROUP,
                image = "barbell_incline_bench"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Incline dumbbell bench press",
                group = DatabaseContract.CHEST_GROUP,
                image = "incline_dumbbell_bench_press"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Incline dumbbell flyes",
                group = DatabaseContract.CHEST_GROUP,
                image = "incline_dumbbell_flyes"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Low cable crossover",
                group = DatabaseContract.CHEST_GROUP,
                image = "low_cable_crossover"
            )
        )

        //legs
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Squats",
                group = DatabaseContract.LEGS_GROUP,
                image = "squat"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Romanian deadlift from deficit",
                group = DatabaseContract.LEGS_GROUP,
                image = "romanian_deadlift_from_deficit"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Romanian deadlift",
                group = DatabaseContract.LEGS_GROUP,
                image = "romanian_deadlift"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Leg press",
                group = DatabaseContract.LEGS_GROUP,
                image = "leg_press"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Machine squat",
                group = DatabaseContract.LEGS_GROUP,
                image = "machine_squat"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Leg extensions",
                group = DatabaseContract.LEGS_GROUP,
                image = "leg_extensions"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Glute ham raise",
                group = DatabaseContract.LEGS_GROUP,
                image = "glute_ham_raise"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Good mornings",
                group = DatabaseContract.LEGS_GROUP,
                image = "good_morning"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Barbell squat",
                group = DatabaseContract.LEGS_GROUP,
                image = "barbell_squat"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Barbell lunge",
                group = DatabaseContract.LEGS_GROUP,
                image = "barbell_lunge"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Dumbbell lunges",
                group = DatabaseContract.LEGS_GROUP,
                image = "dumbbell_lunges"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Dumbbell step ups",
                group = DatabaseContract.LEGS_GROUP,
                image = "dumbbell_step_ups"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Smith machine squat",
                group = DatabaseContract.LEGS_GROUP,
                image = "smith_machine_squat"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Smith machine front squat",
                group = DatabaseContract.LEGS_GROUP,
                image = "smith_machine_front_squat"
            )
        )

        //abs
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Oblique crunches on the flor",
                group = DatabaseContract.ABS_GROUP,
                image = "oblique_crunches_on_the_flor"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Legs rise on bars",
                group = DatabaseContract.ABS_GROUP,
                image = "legs_rise_on_bars"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Flat bench leg pull in",
                group = DatabaseContract.ABS_GROUP,
                image = "flat_bench_leg_pull_in"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Hanging pike",
                group = DatabaseContract.ABS_GROUP,
                image = "hanging_pike"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Janda sit up",
                group = DatabaseContract.ABS_GROUP,
                image = "janda_sit_up"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Knee hip raise on bars",
                group = DatabaseContract.ABS_GROUP,
                image = "knee_hip_raise_on_bars"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Kneeling rope crunch",
                group = DatabaseContract.ABS_GROUP,
                image = "kneeling_rope_crunch"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Seated leg tucks",
                group = DatabaseContract.ABS_GROUP,
                image = "seated_leg_tucks"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Oblique crunches on the flor",
                group = DatabaseContract.ABS_GROUP,
                image = "oblique_crunches_on_the_flor"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Tuck crunch",
                group = DatabaseContract.ABS_GROUP,
                image = "tuck_crunch"
            )
        )

        //traps
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Barbell shrugs behind the back",
                group = DatabaseContract.TRAPS_GROUP,
                image = "barbell_shrag_behind_the_back"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Barbell shrug",
                group = DatabaseContract.TRAPS_GROUP,
                image = "barbell_shrug"
            )
        )
        exerciseDataSource.insertExercise(
            Exercise(
                name = "Dumbbell shrug",
                group = DatabaseContract.TRAPS_GROUP,
                image = "dumbbell_shrug"
            )
        )
    }
}