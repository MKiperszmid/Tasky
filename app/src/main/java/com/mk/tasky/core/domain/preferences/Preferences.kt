package com.mk.tasky.core.domain.preferences

import com.mk.tasky.core.domain.model.LoggedUser

interface Preferences {
    fun saveToken(token: String)
    fun saveFullName(name: String)
    fun saveUserId(userId: String)
    fun loadLoggedUser(): LoggedUser

    companion object {
        const val KEY_TOKEN = "token"
        const val KEY_NAME = "name"
        const val KEY_USERID = "userid"
    }
}
