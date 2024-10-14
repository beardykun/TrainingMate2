package jp.mikhail.pankratov.trainingMate.core.domain.util

import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.abs
import maxrep.shared.generated.resources.alternating_deltoid_raise
import maxrep.shared.generated.resources.arnold_dumbbell_press
import maxrep.shared.generated.resources.back
import maxrep.shared.generated.resources.barbell_curl
import maxrep.shared.generated.resources.barbell_incline_bench
import maxrep.shared.generated.resources.barbell_lunge
import maxrep.shared.generated.resources.barbell_rea_delt_rows
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
import maxrep.shared.generated.resources.cable_rope_overhead_extention
import maxrep.shared.generated.resources.chest
import maxrep.shared.generated.resources.chin_up
import maxrep.shared.generated.resources.close_grip_barbell_press
import maxrep.shared.generated.resources.close_grip_front_lat_pulldown
import maxrep.shared.generated.resources.concentration_curls
import maxrep.shared.generated.resources.cross_body_hammer_curl
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
        else -> null
    }
}