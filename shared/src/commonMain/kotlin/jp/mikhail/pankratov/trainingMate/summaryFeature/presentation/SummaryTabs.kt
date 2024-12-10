package jp.mikhail.pankratov.trainingMate.summaryFeature.presentation

import maxrep.shared.generated.resources.Res
import maxrep.shared.generated.resources.month
import maxrep.shared.generated.resources.month_all
import maxrep.shared.generated.resources.week
import maxrep.shared.generated.resources.week_all
import org.jetbrains.compose.resources.StringResource

enum class SummaryTabs(val tabName: StringResource) {
    WEEK(Res.string.week),
    MONTH(Res.string.month),
    WEEK_ALL(Res.string.week_all),
    MONTH_ALL(Res.string.month_all)
}