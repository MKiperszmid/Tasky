package com.mk.tasky.agenda.data.mapper

import com.mk.tasky.agenda.data.remote.dto.PhotoDto
import com.mk.tasky.agenda.domain.model.AgendaPhoto

fun PhotoDto.toDomain(): AgendaPhoto {
    return AgendaPhoto.Remote(
        remoteUrl = this.url,
        key = this.key
    )
}
