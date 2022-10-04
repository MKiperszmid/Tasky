package com.mk.tasky.core.domain.util

interface EmailMatcher {
    fun matches(email: String): Boolean
}