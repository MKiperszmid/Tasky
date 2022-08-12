package com.mk.tasky.authentication.domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

internal class ValidatePasswordUseCaseTest {

    private lateinit var validatePassword: ValidatePasswordUseCase

    @Before
    fun setUp() {
        validatePassword = ValidatePasswordUseCase()
    }

    @Test
    fun `Small length, returns false`() {
        val password = "asd"
        val result = validatePassword(password)
        assertThat(result).isFalse()
    }

    @Test
    fun `Valid length, no uppercase, returns false`() {
        val password = "asdasdasd"
        val result = validatePassword(password)
        assertThat(result).isFalse()
    }

    @Test
    fun `Valid length, no lowercase, returns false`() {
        val password = "ASDASDASD"
        val result = validatePassword(password)
        assertThat(result).isFalse()
    }

    @Test
    fun `Valid length, no digits, returns false`() {
        val password = "asdASDASD"
        val result = validatePassword(password)
        assertThat(result).isFalse()
    }

    @Test
    fun `Valid length, no letters, returns false`() {
        val password = "123456789"
        val result = validatePassword(password)
        assertThat(result).isFalse()
    }

    @Test
    fun `Valid password, returns true`() {
        val password = "asdASD123"
        val result = validatePassword(password)
        assertThat(result).isTrue()
    }
}
