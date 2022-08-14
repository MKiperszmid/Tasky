package com.mk.tasky.authentication.domain.usecase

class ValidateFullNameUseCase {
    operator fun invoke(name: String): Boolean {
        return name.length in 2..50
    }
}
