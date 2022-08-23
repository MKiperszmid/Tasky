package com.mk.tasky.core.util

import android.content.Context

sealed class UIText {
    data class DynamicString(val text: String) : UIText()
    data class StringResource(val resId: Int) : UIText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> text
            is StringResource -> context.getString(resId)
        }
    }
}
