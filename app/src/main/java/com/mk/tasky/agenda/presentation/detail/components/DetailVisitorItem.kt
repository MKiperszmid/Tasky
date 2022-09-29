package com.mk.tasky.agenda.presentation.detail.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mk.tasky.R
import com.mk.tasky.agenda.domain.usecase.home.FormatNameUseCase
import com.mk.tasky.ui.theme.DarkGray
import com.mk.tasky.ui.theme.Gray
import com.mk.tasky.ui.theme.Light2
import com.mk.tasky.ui.theme.LightBlue

@Composable
fun DetailVisitorItem(
    imageUrl: Uri?,
    name: String,
    isHost: Boolean = false,
    isEditing: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .background(Light2, RoundedCornerShape(10.dp)).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            imageUrl?.let {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.photo),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(32.dp).clip(CircleShape)
                )
            } ?: Box(
                modifier = Modifier.size(32.dp).clip(CircleShape).background(Gray),
                contentAlignment = Alignment.Center
            ) {
                val nameFormatter = FormatNameUseCase()
                Text(text = nameFormatter(name))
            }
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = name, fontSize = 14.sp, color = DarkGray)
        }
        if (isHost) {
            Text(text = "creator", color = LightBlue, fontSize = 14.sp)
        } else if (isEditing) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.DeleteOutline, contentDescription = "delete")
            }
        }
    }
}

@Preview
@Composable
fun DetailVisitorItemPreview() {
    DetailVisitorItem(
        imageUrl = null,
        name = "Martin Kiperszmid",
        isHost = true,
        isEditing = false
    )
}
