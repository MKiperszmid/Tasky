package com.mk.tasky.core.domain.usecase

import com.mk.tasky.core.domain.util.EmailMatcher

class ValidateEmailUseCase(
    private val emailMatcher: EmailMatcher
) {
    operator fun invoke(email: String): Boolean {
        return emailMatcher.matches(email)
    }
}
