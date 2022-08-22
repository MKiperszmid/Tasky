package com.mk.tasky.agenda.detail.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScreen(
    viewModel: AgendaDetailViewModel = hiltViewModel()
) {
    Text(text = "Detail Screen of: ${viewModel.state}")
}
