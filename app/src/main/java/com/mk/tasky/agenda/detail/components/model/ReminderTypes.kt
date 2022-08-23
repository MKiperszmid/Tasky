package com.mk.tasky.agenda.detail.components.model

import com.mk.tasky.R
import com.mk.tasky.core.util.UIText

enum class ReminderTypes(val type: UIText) {
    TEN_MINUTES(UIText.StringResource(R.string.ten_minutes_before)),
    THIRTY_MINUTES(UIText.StringResource(R.string.thirty_minutes_before)),
    ONE_HOUR(UIText.StringResource(R.string.one_hour_before)),
    SIX_HOURS(UIText.StringResource(R.string.six_hours_before)),
    ONE_DAY(UIText.StringResource(R.string.one_day_before))
}
