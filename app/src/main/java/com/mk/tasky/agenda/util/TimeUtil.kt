package com.mk.tasky.agenda.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object TimeUtil {
    fun timeToLong(time: LocalDateTime): Long {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun longToTime(time: Long): LocalDateTime {
        return LocalDateTime.ofInstant(
            Instant.ofEpochMilli(time),
            ZoneId.systemDefault()
        )
    }
}
