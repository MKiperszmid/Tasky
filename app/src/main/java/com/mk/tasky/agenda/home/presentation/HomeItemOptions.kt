package com.mk.tasky.agenda.home.presentation

import com.mk.tasky.R
import com.mk.tasky.core.util.UIText

enum class HomeItemOptions(val type: UIText) {
    OPEN(UIText.StringResource(R.string.open)),
    EDIT(UIText.StringResource(R.string.edit)),
    DELETE(UIText.StringResource(R.string.delete));

    companion object {
        fun from(type: String): HomeItemOptions? =
            values().find { it.name.lowercase() == type.lowercase() }
    }
}
