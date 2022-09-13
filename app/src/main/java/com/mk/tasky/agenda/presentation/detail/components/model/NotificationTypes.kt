package com.mk.tasky.agenda.presentation.detail.components.model

import com.mk.tasky.R
import com.mk.tasky.core.util.UIText
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.abs

enum class NotificationTypes(val type: UIText) {
    TEN_MINUTES(UIText.StringResource(R.string.ten_minutes_before)),
    THIRTY_MINUTES(UIText.StringResource(R.string.thirty_minutes_before)),
    ONE_HOUR(UIText.StringResource(R.string.one_hour_before)),
    SIX_HOURS(UIText.StringResource(R.string.six_hours_before)),
    ONE_DAY(UIText.StringResource(R.string.one_day_before));

    companion object {
        fun from(dateTime: LocalDateTime, notificationTime: LocalDateTime): NotificationTypes {
            val difference = Duration.between(dateTime, notificationTime)
            if (abs(difference.toDays()) == 1L) {
                return ONE_DAY
            }
            if (abs(difference.toHours()) == 6L) {
                return SIX_HOURS
            }
            if (abs(difference.toHours()) == 1L) {
                return ONE_HOUR
            }
            if (abs(difference.toMinutes()) == 30L) {
                return THIRTY_MINUTES
            }
            return TEN_MINUTES
        }

        fun remindAt(time: LocalDateTime, notificationTypes: NotificationTypes): LocalDateTime {
            return when (notificationTypes) {
                TEN_MINUTES -> time.minusMinutes(10)
                THIRTY_MINUTES -> time.minusMinutes(30)
                ONE_HOUR -> time.minusHours(1)
                SIX_HOURS -> time.minusHours(6)
                ONE_DAY -> time.minusDays(1)
            }
        }
    }
}
