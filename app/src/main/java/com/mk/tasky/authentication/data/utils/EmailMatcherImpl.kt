package com.mk.tasky.authentication.data.utils

import android.util.Patterns
import com.mk.tasky.authentication.domain.utils.EmailMatcher

class EmailMatcherImpl : EmailMatcher {
    override fun matches(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
