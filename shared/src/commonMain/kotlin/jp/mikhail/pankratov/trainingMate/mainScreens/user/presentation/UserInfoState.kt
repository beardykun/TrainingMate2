package jp.mikhail.pankratov.trainingMate.mainScreens.user.presentation

import androidx.compose.runtime.Immutable
import com.aay.compose.barChart.model.BarParameters

@Immutable
data class UserInfoState(val strengthLevelParams: List<BarParameters>? = null)
