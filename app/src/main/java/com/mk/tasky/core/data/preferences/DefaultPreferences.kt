package com.mk.tasky.core.data.preferences

import android.content.SharedPreferences
import com.mk.tasky.core.domain.model.LoggedUser
import com.mk.tasky.core.domain.preferences.Preferences

class DefaultPreferences(
    private val sharedPreferences: SharedPreferences
) : Preferences {
    override fun saveToken(token: String) {
        sharedPreferences.edit().putString(Preferences.KEY_TOKEN, token).apply()
    }

    override fun saveFullName(name: String) {
        sharedPreferences.edit().putString(Preferences.KEY_NAME, name).apply()
    }

    override fun saveUserId(userId: String) {
        sharedPreferences.edit().putString(Preferences.KEY_USERID, userId).apply()
    }

    override fun loadLoggedUser(): LoggedUser {
        return LoggedUser(
            token = sharedPreferences.getString(Preferences.KEY_TOKEN, null),
            userId = sharedPreferences.getString(Preferences.KEY_USERID, null),
            fullName = sharedPreferences.getString(Preferences.KEY_NAME, null)
        )
    }
}
