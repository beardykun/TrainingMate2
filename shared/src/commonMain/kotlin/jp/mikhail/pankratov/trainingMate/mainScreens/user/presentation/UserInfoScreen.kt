package jp.mikhail.pankratov.trainingMate.mainScreens.user.presentation

import Dimens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import jp.mikhail.pankratov.trainingMate.SharedRes
import jp.mikhail.pankratov.trainingMate.core.getString
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonBarChart
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextLarge
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.TextSmall
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun UserInfoScreen(state: UserInfoState, navigator: Navigator) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = Dimens.Padding16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextLarge(text = SharedRes.strings.strength_comparison_info.getString())
        Spacer(modifier = Modifier.height(Dimens.Padding8))
        TextSmall(text = SharedRes.strings.strength_comparison_hint.getString())
        Spacer(modifier = Modifier.height(Dimens.Padding32))
        state.strengthLevelParams?.let {
            if (it.size < 3) return@Column
            CommonBarChart(params = it, modifier = Modifier.fillMaxWidth())
        }
    }
}