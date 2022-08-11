package com.mk.tasky.authentication.domain.usecase

import com.google.common.truth.Truth
import com.mk.tasky.authentication.domain.utils.EmailMatcher
import org.junit.Before
import org.junit.Test

internal class ValidateEmailUseCaseTest {
    private lateinit var validateEmail: ValidateEmailUseCase
    private lateinit var emailMatcher: EmailMatcher

    @Before
    fun setUp() {
        emailMatcher = object : EmailMatcher {
            override fun matches(email: String): Boolean {
                return email.contains("@") && email.contains(".")
            }
        }
        validateEmail = ValidateEmailUseCase(emailMatcher)
    }

    @Test
    fun `Email with no @ or dot, return false`() {
        val email = "asd"
        val result = validateEmail(email)
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `Email with no @, return false`() {
        val email = "asd.com"
        val result = validateEmail(email)
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `Email with no dot, return false`() {
        val email = "asd@asd"
        val result = validateEmail(email)
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `Valid email, return true`() {
        val email = "asd@asd.com"
        val result = validateEmail(email)
        Truth.assertThat(result).isTrue()
    }
}
