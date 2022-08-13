package com.mk.tasky.authentication.domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

internal class ValidateFullNameUseCaseTest {

    private lateinit var validateFullNameUseCase: ValidateFullNameUseCase

    @Before
    fun setUp() {
        validateFullNameUseCase = ValidateFullNameUseCase()
    }

    @Test
    fun `Small length name, return invalid`() {
        val name = "a"
        val result = validateFullNameUseCase(name)
        assertThat(result).isFalse()
    }

    @Test
    fun `Big length name, return invalid`() {
        val name = "a".repeat(51)
        val result = validateFullNameUseCase(name)
        assertThat(result).isFalse()
    }

    @Test
    fun `Min length name, return valid`() {
        val name = "aa"
        val result = validateFullNameUseCase(name)
        assertThat(result).isTrue()
    }

    @Test
    fun `Max length name, return valid`() {
        val name = "a".repeat(50)
        val result = validateFullNameUseCase(name)
        assertThat(result).isTrue()
    }

    @Test
    fun `Medium length name, return valid`() {
        val name = "a".repeat(25)
        val result = validateFullNameUseCase(name)
        assertThat(result).isTrue()
    }
}
