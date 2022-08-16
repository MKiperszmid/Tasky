package com.mk.tasky.core.util

sealed interface UIEvent {
    object Navigate : UIEvent
}
