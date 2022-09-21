package com.mk.tasky.agenda.presentation.detail.components

import android.Manifest
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.mk.tasky.BuildConfig
import com.mk.tasky.R
import com.mk.tasky.agenda.domain.model.AgendaPhoto
import com.mk.tasky.ui.theme.Black
import com.mk.tasky.ui.theme.Light2
import com.mk.tasky.ui.theme.LightBlue

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DetailPhotoSelector(
    photos: List<AgendaPhoto>,
    onPhotoSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                onPhotoSelected(it)
            }
        }

    val readGalleryPermissionState =
        rememberPermissionState(Manifest.permission.READ_MEDIA_IMAGES)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Light2)
    ) {
        if (photos.isEmpty()) {
            EmptyPhotoPicker(onAddPhoto = {
                launchImageGallery(readGalleryPermissionState, galleryLauncher)
            })
        } else {
            PhotoViewer(photos = photos, onPhotoClick = { }, onAddPhoto = {
                launchImageGallery(readGalleryPermissionState, galleryLauncher)
            })
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun launchImageGallery(
    permissionState: PermissionState,
    galleryLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {
    if (permissionState.status == PermissionStatus.Granted || BuildConfig.DEBUG) { // Emulator doesn't run the permission checker
        val mediaRequest =
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        galleryLauncher.launch(mediaRequest)
    } else {
        permissionState.launchPermissionRequest()
    }
}

@Composable
private fun PhotoViewer(
    photos: List<AgendaPhoto>,
    onPhotoClick: (AgendaPhoto) -> Unit,
    onAddPhoto: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(21.dp)) {
        Text(
            text = stringResource(R.string.photos),
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
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(it.location)
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
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
                            contentDescription = stringResource(R.string.add_photos),
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
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_photos),
            tint = LightBlue
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(R.string.add_photos),
            color = LightBlue,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
fun DetailPhotoSelectorPreview() {
    DetailPhotoSelector(photos = listOf(AgendaPhoto.Local("")), onPhotoSelected = {})
}
