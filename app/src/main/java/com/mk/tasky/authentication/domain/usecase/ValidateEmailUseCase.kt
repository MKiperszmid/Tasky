package com.mk.tasky.authentication.domain.usecase

import com.mk.tasky.authentication.domain.utils.EmailMatcher

class ValidateEmailUseCase(
    private val emailMatcher: EmailMatcher
) {
    operator fun invoke(email: String): Boolean {
        return emailMatcher.matches(email)
    }
}
