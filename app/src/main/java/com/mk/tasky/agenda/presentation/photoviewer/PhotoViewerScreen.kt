package com.mk.tasky.agenda.presentation.photoviewer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mk.tasky.R

@Composable
fun PhotoViewerScreen(
    onBack: (Boolean) -> Unit,
    viewModel: PhotoViewerViewModel = hiltViewModel()
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onBack(false) }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "back")
                }
                Text(text = "Photo", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                IconButton(onClick = { onBack(true) }) {
                    Icon(imageVector = Icons.Default.DeleteOutline, contentDescription = "delete")
                }
            }
        }

        item {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(viewModel.location)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.photo),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
