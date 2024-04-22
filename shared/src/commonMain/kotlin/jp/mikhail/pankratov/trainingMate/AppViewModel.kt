package jp.mikhail.pankratov.trainingMate

import jp.mikhail.pankratov.trainingMate.core.domain.DatabaseContract
import jp.mikhail.pankratov.trainingMate.core.domain.local.exercise.ExerciseLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.training.TrainingLocal
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.ExerciseUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.domain.local.useCases.TrainingUseCaseProvider
import jp.mikhail.pankratov.trainingMate.core.listToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

class AppViewModel(
    private val trainingUseCaseProvider: TrainingUseCaseProvider,
    private val exerciseUseCaseProvider: ExerciseUseCaseProvider
) : moe.tlaster.precompose.viewmodel.ViewModel() {

    fun insertDefaultTraining() = viewModelScope.launch(Dispatchers.IO) {
        if (!trainingUseCaseProvider.getTrainingTableEmptyUseCase()()) return@launch
        trainingUseCaseProvider.getInsertLocalTrainingUseCase().invoke(
            TrainingLocal(
                id = null,
                name = "Full Body",
                groups = listOf(
                    DatabaseContract.BICEPS_GROUP, DatabaseContract.TRICEPS_GROUP,
                    DatabaseContract.SHOULDERS_GROUP, DatabaseContract.BACK_GROUP,
                    DatabaseContract.CHEST_GROUP, DatabaseContract.LEGS_GROUP,
                    DatabaseContract.TRAPS_GROUP, DatabaseContract.ABS_GROUP
                ).listToString(),
                exercises = emptyList(),
                description = "full body training, good after a long break to restart your training process"
            )
        )
        insertDefaultExercises()
    }

    private fun insertDefaultExercises() = viewModelScope.launch(Dispatchers.IO) {
        //biceps
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Barbell curls",
                group = DatabaseContract.BICEPS_GROUP,
                image = "barbell_curl"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Standing reverse curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "standing_reverse_curl"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Standing biceps cable curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "standing_biceps_cable_curl"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Overhead cable curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "overhead_cable_curl",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Seated dumbbell curls",
                group = DatabaseContract.BICEPS_GROUP,
                image = "seated_dumbbell_curls",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Dumbbell preacher curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "dumbbell_preacher_curl",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Preacher curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "preacher_curl"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Concentration curls",
                group = DatabaseContract.BICEPS_GROUP,
                image = "concentration_curls",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Dumbbell bicep curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "dumbbell_bicep_curl",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Dumbbell alternate bicep curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "dumbbell_alternate_bicep_curl",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Cross body hammer curl",
                group = DatabaseContract.BICEPS_GROUP,
                image = "cross_body_hammer_curl",
                usesTwoDumbbells = true
            )
        )
        //triceps
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Lying Triceps Press",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "lying_triceps_press"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Standing triceps press",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "standing_triceps_press"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Triceps dumbbell kickback",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "tricep_dumbbell_kickback",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Triceps push-down",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "triceps_pushdown"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Push ups close triceps position",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "push_ups_close_triceps_position"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Dips triceps",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "dips_triceps"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Close grip barbell press",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "close_grip_barbell_press"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Cable rope overhead extension",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "cable_rope_overhead_extention"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Bench dips",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "bench_dips"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Cable one arm triceps extension",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "cable_one_arm_triceps_extension",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Dumbbell one arm triceps extension",
                group = DatabaseContract.TRICEPS_GROUP,
                image = "dumbbell_one_arm_triceps_extension",
                usesTwoDumbbells = true
            )
        )

        //Shoulders
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Barbell Shoulder Press",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "barbell_shoulder_press"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Shoulder Press",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "shoulder_press"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Reverse machine flyes",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "reverse_machine_flyes"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Front dumbbell raise",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "front_dumbbell_raise",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Side lateral raise",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "side_lateral_raise"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Dumbbell shoulder press",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "dumbbell_shoulder_press",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Cable rear delt fly",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "cable_rear_delt_fly",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Bent over dumbbell rear delt raise",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "bent_over_dumbbell_rear_delt_raise",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Barbell rea delt rows",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "barbell_rea_delt_rows"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Arnold dumbbell press",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "arnold_dumbbell_press",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Alternating deltoid raise",
                group = DatabaseContract.SHOULDERS_GROUP,
                image = "alternating_deltoid_raise",
                usesTwoDumbbells = true
            )
        )

        //back
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Chin ups",
                group = DatabaseContract.BACK_GROUP,
                image = "chin_up"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Reverse grip bent over rows",
                group = DatabaseContract.BACK_GROUP,
                image = "reverse_grip_bent_over_rows"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Lying T-bar row",
                group = DatabaseContract.BACK_GROUP,
                image = "lying_t_bar_row"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Close grip front lat pull-down",
                group = DatabaseContract.BACK_GROUP,
                image = "close_grip_front_lat_pulldown"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Bent over barbell row",
                group = DatabaseContract.BACK_GROUP,
                image = "bent_over_barbell_row"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Wide grip lat pull-down",
                group = DatabaseContract.BACK_GROUP,
                image = "wide_grip_lat_pulldown"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Underhand cable pull-down",
                group = DatabaseContract.BACK_GROUP,
                image = "underhand_cable_pulldown"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "One arm dumbbell row",
                group = DatabaseContract.BACK_GROUP,
                image = "one_arm_dumbbell_row",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Seated cable row",
                group = DatabaseContract.BACK_GROUP,
                image = "seated_cable_row"
            )
        )

        //chest
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Bench Press",
                group = DatabaseContract.CHEST_GROUP,
                image = "bench_press"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Machine flye",
                group = DatabaseContract.CHEST_GROUP,
                image = "machine_flye"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Machine bench press",
                group = DatabaseContract.CHEST_GROUP,
                image = "machine_bench_press"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Dumbbell flyes",
                group = DatabaseContract.CHEST_GROUP,
                image = "dumbbell_flyes",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Decline dumbbell bench press",
                group = DatabaseContract.CHEST_GROUP,
                image = "decline_dumbbell_bench_press",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Decline bench press",
                group = DatabaseContract.CHEST_GROUP,
                image = "decline_bench_press"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Barbell incline bench",
                group = DatabaseContract.CHEST_GROUP,
                image = "barbell_incline_bench"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Incline dumbbell bench press",
                group = DatabaseContract.CHEST_GROUP,
                image = "incline_dumbbell_bench_press",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Incline dumbbell flyes",
                group = DatabaseContract.CHEST_GROUP,
                image = "incline_dumbbell_flyes",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Low cable crossover",
                group = DatabaseContract.CHEST_GROUP,
                image = "low_cable_crossover"
            )
        )

        //legs
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Squats",
                group = DatabaseContract.LEGS_GROUP,
                image = "squat"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Romanian deadlift from deficit",
                group = DatabaseContract.LEGS_GROUP,
                image = "romanian_deadlift_from_deficit"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Romanian deadlift",
                group = DatabaseContract.LEGS_GROUP,
                image = "romanian_deadlift"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Leg press",
                group = DatabaseContract.LEGS_GROUP,
                image = "leg_press"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Machine squat",
                group = DatabaseContract.LEGS_GROUP,
                image = "machine_squat"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Leg extensions",
                group = DatabaseContract.LEGS_GROUP,
                image = "leg_extensions"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Glute ham raise",
                group = DatabaseContract.LEGS_GROUP,
                image = "glute_ham_raise"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Good mornings",
                group = DatabaseContract.LEGS_GROUP,
                image = "good_morning"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Barbell squat",
                group = DatabaseContract.LEGS_GROUP,
                image = "barbell_squat"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Barbell lunge",
                group = DatabaseContract.LEGS_GROUP,
                image = "barbell_lunge"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Dumbbell lunges",
                group = DatabaseContract.LEGS_GROUP,
                image = "dumbbell_lunges",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Dumbbell step ups",
                group = DatabaseContract.LEGS_GROUP,
                image = "dumbbell_step_ups",
                usesTwoDumbbells = true
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Smith machine squat",
                group = DatabaseContract.LEGS_GROUP,
                image = "smith_machine_squat"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Smith machine front squat",
                group = DatabaseContract.LEGS_GROUP,
                image = "smith_machine_front_squat"
            )
        )

        //abs
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Oblique crunches on the flor",
                group = DatabaseContract.ABS_GROUP,
                image = "oblique_crunches_on_the_flor"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Legs rise on bars",
                group = DatabaseContract.ABS_GROUP,
                image = "legs_rise_on_bars"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Flat bench leg pull in",
                group = DatabaseContract.ABS_GROUP,
                image = "flat_bench_leg_pull_in"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Hanging pike",
                group = DatabaseContract.ABS_GROUP,
                image = "hanging_pike"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Janda sit up",
                group = DatabaseContract.ABS_GROUP,
                image = "janda_sit_up"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Knee hip raise on bars",
                group = DatabaseContract.ABS_GROUP,
                image = "knee_hip_raise_on_bars"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Kneeling rope crunch",
                group = DatabaseContract.ABS_GROUP,
                image = "kneeling_rope_crunch"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Seated leg tucks",
                group = DatabaseContract.ABS_GROUP,
                image = "seated_leg_tucks"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Tuck crunch",
                group = DatabaseContract.ABS_GROUP,
                image = "tuck_crunch"
            )
        )

        //traps
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Barbell shrugs behind the back",
                group = DatabaseContract.TRAPS_GROUP,
                image = "barbell_shrag_behind_the_back"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Barbell shrug",
                group = DatabaseContract.TRAPS_GROUP,
                image = "barbell_shrug"
            )
        )
        exerciseUseCaseProvider.getInsertLocalExerciseUseCase().invoke(
            ExerciseLocal(
                name = "Dumbbell shrug",
                group = DatabaseContract.TRAPS_GROUP,
                image = "dumbbell_shrug",
                usesTwoDumbbells = true
            )
        )
    }
}