package com.mk.tasky.agenda.presentation.detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.tasky.ui.theme.Black
import com.mk.tasky.ui.theme.Light2
import com.mk.tasky.ui.theme.LightBlue

@Composable
fun DetailPhotoSelector(
    photos: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Light2)
    ) {
        if (photos.isEmpty()) {
            EmptyPhotoPicker(onAddPhoto = {
                // TODO: Open Photo Picker
            })
        } else {
            PhotoViewer(photos = photos, onPhotoClick = { }, onAddPhoto = { })
        }
    }
}

@Composable
private fun PhotoViewer(
    photos: List<String>,
    onPhotoClick: (String) -> Unit,
    onAddPhoto: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(21.dp)) {
        Text(
            text = "Photos",
            color = Black,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(21.dp))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(photos) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {
                            onPhotoClick(it)
                        }
                        .border(
                            BorderStroke(2.dp, color = LightBlue),
                            shape = RoundedCornerShape(5.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "${it[0]}") // TODO: Reeplace with the image
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
            if (photos.size < 10) {
                item {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                onAddPhoto()
                            }
                            .border(
                                BorderStroke(2.dp, color = LightBlue),
                                shape = RoundedCornerShape(5.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "add_photos",
                            tint = LightBlue
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyPhotoPicker(
    onAddPhoto: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onAddPhoto()
            }
            .padding(37.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "add_photos", tint = LightBlue)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Add Photos",
            color = LightBlue,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
fun DetailPhotoSelectorPreview() {
    DetailPhotoSelector(photos = listOf("A", "B"))
}
