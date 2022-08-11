package com.mk.tasky.authentication.domain.usecase

import android.util.Patterns

class ValidEmailUseCase {
    operator fun invoke(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
