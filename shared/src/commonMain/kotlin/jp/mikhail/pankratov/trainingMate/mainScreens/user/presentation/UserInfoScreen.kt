package jp.mikhail.pankratov.trainingMate.mainScreens.user.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.mikhail.pankratov.trainingMate.core.presentation.commomComposables.CommonRadarChart
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun UserInfoScreen(state: UserInfoState, navigator: Navigator) {

    Column(modifier = Modifier.fillMaxSize()) {
        state.strengthLevel?.let {
            if (it.size < 3) return@Column
            CommonRadarChart(map = it)
        }
    }
}