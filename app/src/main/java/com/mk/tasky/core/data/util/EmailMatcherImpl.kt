package com.mk.tasky.core.data.util

import android.util.Patterns
import com.mk.tasky.core.domain.util.EmailMatcher

class EmailMatcherImpl : EmailMatcher {
    override fun matches(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
