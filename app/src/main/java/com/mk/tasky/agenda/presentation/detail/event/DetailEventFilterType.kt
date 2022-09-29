package com.mk.tasky.agenda.presentation.detail.event

import com.mk.tasky.R
import com.mk.tasky.core.util.UIText

enum class DetailEventFilterType(val type: UIText) {
    ALL(UIText.StringResource(R.string.all)),
    GOING(UIText.StringResource(R.string.going)),
    NOT_GOING(UIText.StringResource(R.string.not_going));

    companion object {
        fun from(type: String): DetailEventFilterType? =
            values().find { it.name.lowercase() == type.lowercase() }
    }
}
