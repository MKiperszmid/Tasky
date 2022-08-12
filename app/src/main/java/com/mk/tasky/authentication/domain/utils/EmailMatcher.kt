package com.mk.tasky.authentication.domain.utils

interface EmailMatcher {
    fun matches(email: String): Boolean
}