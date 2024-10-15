package jp.mikhail.pankratov.trainingMate.core.domain.util

import androidx.compose.runtime.Composable
import jp.mikhail.pankratov.trainingMate.core.getString
import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.abs
import maxrep.shared.generated.resources.alternating_deltoid_raise
import maxrep.shared.generated.resources.arnold_dumbbell_press
import maxrep.shared.generated.resources.back
import maxrep.shared.generated.resources.barbell_curls
import maxrep.shared.generated.resources.barbell_incline_bench
import maxrep.shared.generated.resources.barbell_lunge
import maxrep.shared.generated.resources.barbell_rea_delt_rows
import maxrep.shared.generated.resources.barbell_rear_delt_rows
import maxrep.shared.generated.resources.barbell_shoulder_press
import maxrep.shared.generated.resources.barbell_shrug
import maxrep.shared.generated.resources.barbell_shrug_behind_the_back
import maxrep.shared.generated.resources.barbell_squat
import maxrep.shared.generated.resources.bench_dips
import maxrep.shared.generated.resources.bench_press
import maxrep.shared.generated.resources.bent_over_barbell_row
import maxrep.shared.generated.resources.bent_over_dumbbell_rear_delt_raise
import maxrep.shared.generated.resources.biceps
import maxrep.shared.generated.resources.cable_one_arm_triceps_extension
import maxrep.shared.generated.resources.cable_rear_delt_fly
import maxrep.shared.generated.resources.cable_rope_overhead_extension
import maxrep.shared.generated.resources.cable_rope_overhead_extention
import maxrep.shared.generated.resources.chest
import maxrep.shared.generated.resources.chin_up
import maxrep.shared.generated.resources.chin_ups
import maxrep.shared.generated.resources.close_grip_barbell_press
import maxrep.shared.generated.resources.close_grip_front_lat_pulldown
import maxrep.shared.generated.resources.concentration_curls
import maxrep.shared.generated.resources.cross_body_hammer_curl
import maxrep.shared.generated.resources.dead_lift
import maxrep.shared.generated.resources.decline_bench_press
import maxrep.shared.generated.resources.decline_dumbbell_bench_press
import maxrep.shared.generated.resources.dips_triceps
import maxrep.shared.generated.resources.dumbbell_alternate_bicep_curl
import maxrep.shared.generated.resources.dumbbell_bicep_curl
import maxrep.shared.generated.resources.dumbbell_flyes
import maxrep.shared.generated.resources.dumbbell_lunges
import maxrep.shared.generated.resources.dumbbell_one_arm_triceps_extension
import maxrep.shared.generated.resources.dumbbell_preacher_curl
import maxrep.shared.generated.resources.dumbbell_shoulder_press
import maxrep.shared.generated.resources.dumbbell_shrug
import maxrep.shared.generated.resources.dumbbell_step_ups
import maxrep.shared.generated.resources.flat_bench_leg_pull_in
import maxrep.shared.generated.resources.front_dumbbell_raise
import maxrep.shared.generated.resources.glute_ham_raise
import maxrep.shared.generated.resources.good_morning
import maxrep.shared.generated.resources.hanging_pike
import maxrep.shared.generated.resources.incline_dumbbell_bench_press
import maxrep.shared.generated.resources.incline_dumbbell_flyes
import maxrep.shared.generated.resources.janda_sit_up
import maxrep.shared.generated.resources.knee_hip_raise_on_bars
import maxrep.shared.generated.resources.kneeling_rope_crunch
import maxrep.shared.generated.resources.leg_extensions
import maxrep.shared.generated.resources.leg_press
import maxrep.shared.generated.resources.legs
import maxrep.shared.generated.resources.legs_rise_on_bars
import maxrep.shared.generated.resources.low_cable_crossover
import maxrep.shared.generated.resources.lying_t_bar_row
import maxrep.shared.generated.resources.lying_triceps_press
import maxrep.shared.generated.resources.machine_bench_press
import maxrep.shared.generated.resources.machine_flye
import maxrep.shared.generated.resources.machine_squat
import maxrep.shared.generated.resources.oblique_crunches_on_the_floor
import maxrep.shared.generated.resources.oblique_crunches_on_the_flor
import maxrep.shared.generated.resources.one_arm_dumbbell_row
import maxrep.shared.generated.resources.overhead_cable_curl
import maxrep.shared.generated.resources.preacher_curl
import maxrep.shared.generated.resources.push_ups_close_triceps_position
import maxrep.shared.generated.resources.reverse_grip_bent_over_rows
import maxrep.shared.generated.resources.reverse_machine_flyes
import maxrep.shared.generated.resources.romanian_deadlift
import maxrep.shared.generated.resources.romanian_deadlift_from_deficit
import maxrep.shared.generated.resources.seated_cable_row
import maxrep.shared.generated.resources.seated_dumbbell_curls
import maxrep.shared.generated.resources.seated_leg_tucks
import maxrep.shared.generated.resources.shoulder_press
import maxrep.shared.generated.resources.shoulders
import maxrep.shared.generated.resources.side_lateral_raise
import maxrep.shared.generated.resources.smith_machine_front_squat
import maxrep.shared.generated.resources.smith_machine_squat
import maxrep.shared.generated.resources.squat
import maxrep.shared.generated.resources.standing_biceps_cable_curl
import maxrep.shared.generated.resources.standing_reverse_curl
import maxrep.shared.generated.resources.standing_triceps_press
import maxrep.shared.generated.resources.traps
import maxrep.shared.generated.resources.tricep_dumbbell_kickback
import maxrep.shared.generated.resources.triceps
import maxrep.shared.generated.resources.triceps_pushdown
import maxrep.shared.generated.resources.tuck_crunch
import maxrep.shared.generated.resources.underhand_cable_pulldown
import maxrep.shared.generated.resources.wide_grip_lat_pulldown
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

fun getDrawableResourceByName(resName: String): DrawableResource? {
    return when (resName) {
        "abs" -> Res.drawable.abs
        "shoulders" -> Res.drawable.shoulders
        "biceps" -> Res.drawable.biceps
        "triceps" -> Res.drawable.triceps
        "back" -> Res.drawable.back
        "chest" -> Res.drawable.chest
        "legs" -> Res.drawable.legs
        "traps" -> Res.drawable.traps
        "standing_reverse_curl" -> Res.drawable.standing_reverse_curl
        "standing_biceps_cable_curl" -> Res.drawable.standing_biceps_cable_curl
        "overhead_cable_curl" -> Res.drawable.overhead_cable_curl
        "seated_dumbbell_curls" -> Res.drawable.seated_dumbbell_curls
        "dumbbell_preacher_curl" -> Res.drawable.dumbbell_preacher_curl
        "preacher_curl" -> Res.drawable.preacher_curl
        "concentration_curls" -> Res.drawable.concentration_curls
        "dumbbell_bicep_curl" -> Res.drawable.dumbbell_bicep_curl
        "dumbbell_alternate_bicep_curl" -> Res.drawable.dumbbell_alternate_bicep_curl
        "cross_body_hammer_curl" -> Res.drawable.cross_body_hammer_curl
        "lying_triceps_press" -> Res.drawable.lying_triceps_press
        "standing_triceps_press" -> Res.drawable.standing_triceps_press
        "tricep_dumbbell_kickback" -> Res.drawable.tricep_dumbbell_kickback
        "triceps_pushdown" -> Res.drawable.triceps_pushdown
        "push_ups_close_triceps_position" -> Res.drawable.push_ups_close_triceps_position
        "dips_triceps" -> Res.drawable.dips_triceps
        "close_grip_barbell_press" -> Res.drawable.close_grip_barbell_press
        "cable_rope_overhead_extension" -> Res.drawable.cable_rope_overhead_extention
        "bench_dips" -> Res.drawable.bench_dips
        "cable_one_arm_triceps_extension" -> Res.drawable.cable_one_arm_triceps_extension
        "dumbbell_one_arm_triceps_extension" -> Res.drawable.dumbbell_one_arm_triceps_extension
        "barbell_shoulder_press" -> Res.drawable.barbell_shoulder_press
        "shoulder_press" -> Res.drawable.shoulder_press
        "reverse_machine_flyes" -> Res.drawable.reverse_machine_flyes
        "front_dumbbell_raise" -> Res.drawable.front_dumbbell_raise
        "side_lateral_raise" -> Res.drawable.side_lateral_raise
        "dumbbell_shoulder_press" -> Res.drawable.dumbbell_shoulder_press
        "cable_rear_delt_fly" -> Res.drawable.cable_rear_delt_fly
        "bent_over_dumbbell_rear_delt_raise" -> Res.drawable.bent_over_dumbbell_rear_delt_raise
        "barbell_rea_delt_rows" -> Res.drawable.barbell_rea_delt_rows
        "arnold_dumbbell_press" -> Res.drawable.arnold_dumbbell_press
        "alternating_deltoid_raise" -> Res.drawable.alternating_deltoid_raise
        "chin_up" -> Res.drawable.chin_up
        "romanian_deadlift" -> Res.drawable.romanian_deadlift
        "reverse_grip_bent_over_rows" -> Res.drawable.reverse_grip_bent_over_rows
        "lying_t_bar_row" -> Res.drawable.lying_t_bar_row
        "close_grip_front_lat_pulldown" -> Res.drawable.close_grip_front_lat_pulldown
        "bent_over_barbell_row" -> Res.drawable.bent_over_barbell_row
        "wide_grip_lat_pulldown" -> Res.drawable.wide_grip_lat_pulldown
        "underhand_cable_pulldown" -> Res.drawable.underhand_cable_pulldown
        "one_arm_dumbbell_row" -> Res.drawable.one_arm_dumbbell_row
        "seated_cable_row" -> Res.drawable.seated_cable_row
        "bench_press" -> Res.drawable.bench_press
        "machine_flye" -> Res.drawable.machine_flye
        "machine_bench_press" -> Res.drawable.machine_bench_press
        "dumbbell_flyes" -> Res.drawable.dumbbell_flyes
        "decline_dumbbell_bench_press" -> Res.drawable.decline_dumbbell_bench_press
        "decline_bench_press" -> Res.drawable.decline_bench_press
        "barbell_incline_bench" -> Res.drawable.barbell_incline_bench
        "incline_dumbbell_bench_press" -> Res.drawable.incline_dumbbell_bench_press
        "incline_dumbbell_flyes" -> Res.drawable.incline_dumbbell_flyes
        "low_cable_crossover" -> Res.drawable.low_cable_crossover
        "squat" -> Res.drawable.squat
        "romanian_deadlift_from_deficit" -> Res.drawable.romanian_deadlift_from_deficit
        "leg_press" -> Res.drawable.leg_press
        "machine_squat" -> Res.drawable.machine_squat
        "leg_extensions" -> Res.drawable.leg_extensions
        "glute_ham_raise" -> Res.drawable.glute_ham_raise
        "good_morning" -> Res.drawable.good_morning
        "barbell_squat" -> Res.drawable.barbell_squat
        "barbell_lunge" -> Res.drawable.barbell_lunge
        "dumbbell_lunges" -> Res.drawable.dumbbell_lunges
        "dumbbell_step_ups" -> Res.drawable.dumbbell_step_ups
        "smith_machine_squat" -> Res.drawable.smith_machine_squat
        "smith_machine_front_squat" -> Res.drawable.smith_machine_front_squat
        "oblique_crunches_on_the_flor" -> Res.drawable.oblique_crunches_on_the_flor
        "legs_rise_on_bars" -> Res.drawable.legs_rise_on_bars
        "flat_bench_leg_pull_in" -> Res.drawable.flat_bench_leg_pull_in
        "hanging_pike" -> Res.drawable.hanging_pike
        "janda_sit_up" -> Res.drawable.janda_sit_up
        "knee_hip_raise_on_bars" -> Res.drawable.knee_hip_raise_on_bars
        "kneeling_rope_crunch" -> Res.drawable.kneeling_rope_crunch
        "seated_leg_tucks" -> Res.drawable.seated_leg_tucks
        "tuck_crunch" -> Res.drawable.tuck_crunch
        "barbell_shrug_behind_the_back" -> Res.drawable.barbell_shrug_behind_the_back
        "barbell_shrug" -> Res.drawable.barbell_shrug
        "dumbbell_shrug" -> Res.drawable.dumbbell_shrug
        "barbell_curls" -> Res.drawable.barbell_curls
        else -> null
    }
}

@Composable
fun getExerciseNameStringResource(exerciseName: String): String {
    return when (exerciseName) {
        "barbell curls" -> Res.string.barbell_curls.getString()
        "standing reverse curl" -> Res.string.standing_reverse_curl.getString()
        "standing biceps cable curl" -> Res.string.standing_biceps_cable_curl.getString()
        "overhead cable curl" -> Res.string.overhead_cable_curl.getString()
        "seated dumbbell curls" -> Res.string.seated_dumbbell_curls.getString()
        "dumbbell preacher curl" -> Res.string.dumbbell_preacher_curl.getString()
        "preacher curl" -> Res.string.preacher_curl.getString()
        "concentration curls" -> Res.string.concentration_curls.getString()
        "dumbbell bicep curl" -> Res.string.dumbbell_bicep_curl.getString()
        "dumbbell alternate bicep curl" -> Res.string.dumbbell_alternate_bicep_curl.getString()
        "cross body hammer curl" -> Res.string.cross_body_hammer_curl.getString()
        "lying triceps press" -> Res.string.lying_triceps_press.getString()
        "standing triceps press" -> Res.string.standing_triceps_press.getString()
        "triceps push-down" -> Res.string.triceps_pushdown.getString()
        "push ups close triceps position" -> Res.string.push_ups_close_triceps_position.getString()
        "dips triceps" -> Res.string.dips_triceps.getString()
        "close grip barbell press" -> Res.string.close_grip_barbell_press.getString()
        "cable rope overhead extension" -> Res.string.cable_rope_overhead_extension.getString()
        "bench dips" -> Res.string.bench_dips.getString()
        "cable one arm triceps extension" -> Res.string.cable_one_arm_triceps_extension.getString()
        "dumbbell one arm triceps extension" -> Res.string.dumbbell_one_arm_triceps_extension.getString()
        "barbell shoulder press" -> Res.string.barbell_shoulder_press.getString()
        "shoulder press" -> Res.string.shoulder_press.getString()
        "reverse machine flyes" -> Res.string.reverse_machine_flyes.getString()
        "front dumbbell raise" -> Res.string.front_dumbbell_raise.getString()
        "side lateral raise" -> Res.string.side_lateral_raise.getString()
        "dumbbell shoulder press" -> Res.string.dumbbell_shoulder_press.getString()
        "cable rear delt fly" -> Res.string.cable_rear_delt_fly.getString()
        "bent over dumbbell rear delt raise" -> Res.string.bent_over_dumbbell_rear_delt_raise.getString()
        "barbell rear delt rows" -> Res.string.barbell_rear_delt_rows.getString()
        "arnold dumbbell press" -> Res.string.arnold_dumbbell_press.getString()
        "alternating deltoid raise" -> Res.string.alternating_deltoid_raise.getString()
        "chin ups" -> Res.string.chin_ups.getString()
        "dead lift" -> Res.string.dead_lift.getString()
        "reverse grip bent over rows" -> Res.string.reverse_grip_bent_over_rows.getString()
        "lying t-bar row" -> Res.string.lying_t_bar_row.getString()
        "close grip front lat pull-down" -> Res.string.close_grip_front_lat_pulldown.getString()
        "bent over barbell row" -> Res.string.bent_over_barbell_row.getString()
        "wide grip lat pull-down" -> Res.string.wide_grip_lat_pulldown.getString()
        "underhand cable pull-down" -> Res.string.underhand_cable_pulldown.getString()
        "one arm dumbbell row" -> Res.string.one_arm_dumbbell_row.getString()
        "seated cable row" -> Res.string.seated_cable_row.getString()
        "bench press" -> Res.string.bench_press.getString()
        "machine flye" -> Res.string.machine_flye.getString()
        "machine bench press" -> Res.string.machine_bench_press.getString()
        "dumbbell flyes" -> Res.string.dumbbell_flyes.getString()
        "decline dumbbell bench press" -> Res.string.decline_dumbbell_bench_press.getString()
        "decline bench press" -> Res.string.decline_bench_press.getString()
        "barbell incline bench" -> Res.string.barbell_incline_bench.getString()
        "incline dumbbell bench press" -> Res.string.incline_dumbbell_bench_press.getString()
        "incline dumbbell flyes" -> Res.string.incline_dumbbell_flyes.getString()
        "low cable crossover" -> Res.string.low_cable_crossover.getString()
        "squats" -> Res.string.squat.getString()
        "romanian deadlift from deficit" -> Res.string.romanian_deadlift_from_deficit.getString()
        "romanian deadlift" -> Res.string.romanian_deadlift.getString()
        "leg press" -> Res.string.leg_press.getString()
        "machine squat" -> Res.string.machine_squat.getString()
        "leg extensions" -> Res.string.leg_extensions.getString()
        "glute ham raise" -> Res.string.glute_ham_raise.getString()
        "good mornings" -> Res.string.good_morning.getString()
        "barbell squat" -> Res.string.barbell_squat.getString()
        "barbell lunge" -> Res.string.barbell_lunge.getString()
        "dumbbell lunges" -> Res.string.dumbbell_lunges.getString()
        "dumbbell step ups" -> Res.string.dumbbell_step_ups.getString()
        "smith machine squat" -> Res.string.smith_machine_squat.getString()
        "smith machine front squat" -> Res.string.smith_machine_front_squat.getString()
        "oblique crunches on the floor" -> Res.string.oblique_crunches_on_the_floor.getString()
        "legs rise on bars" -> Res.string.legs_rise_on_bars.getString()
        "flat bench leg pull in" -> Res.string.flat_bench_leg_pull_in.getString()
        "hanging pike" -> Res.string.hanging_pike.getString()
        "janda sit up" -> Res.string.janda_sit_up.getString()
        "knee hip raise on bars" -> Res.string.knee_hip_raise_on_bars.getString()
        "kneeling rope crunch" -> Res.string.kneeling_rope_crunch.getString()
        "seated leg tucks" -> Res.string.seated_leg_tucks.getString()
        "tuck crunch" -> Res.string.tuck_crunch.getString()
        "barbell shrug" -> Res.string.barbell_shrug.getString()
        "dumbbell shrug" -> Res.string.dumbbell_shrug.getString()
        "barbell shrugs behind the back" -> Res.string.barbell_shrug_behind_the_back.getString()
        "triceps dumbbell kickback" -> Res.string.tricep_dumbbell_kickback.getString()
        else -> exerciseName
    }
}