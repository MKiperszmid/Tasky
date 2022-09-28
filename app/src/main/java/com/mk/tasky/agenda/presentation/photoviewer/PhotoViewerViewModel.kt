package com.mk.tasky.agenda.presentation.photoviewer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoViewerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var location by mutableStateOf("")
        private set

    init {
        location = savedStateHandle.get<String>("location") ?: ""
    }
}
