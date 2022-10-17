package com.mk.tasky.core.navigation

object DeepLinks {
    private const val URL = "https://mkiperszmid.com"
    const val EVENT_DETAIL = "$URL/event?id={id}"
    const val REMINDER_DETAIL = "$URL/reminder?id={id}"
    const val TASK_DETAIL = "$URL/task?id={id}"
}
