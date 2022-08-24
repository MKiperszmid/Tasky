package com.mk.tasky.agenda.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditableViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var body by mutableStateOf("")
        private set

    init {
        body = savedStateHandle.get<String>("body") ?: ""
    }

    fun onTextChange(value: String) {
        body = value
    }
}
