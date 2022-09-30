package com.mk.tasky.agenda.domain.model

sealed class AgendaPhoto(val location: String) {
    data class Local(val localUri: String) : AgendaPhoto(localUri)
    data class Remote(val remoteUrl: String, val key: String) : AgendaPhoto(remoteUrl)
}
