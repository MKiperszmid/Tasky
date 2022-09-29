package com.mk.tasky.agenda.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.mk.tasky.agenda.presentation.detail.event.DetailEventFilterType

@Composable
fun DetailVisitorsFilter(
    onClick: (DetailEventFilterType) -> Unit,
    selectedFilterType: DetailEventFilterType,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val filterTypes = DetailEventFilterType.values()

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(filterTypes) {
            DetailVisitorsPill(
                text = it.type.asString(context),
                selected = selectedFilterType == it,
                onClick = {
                    onClick(it)
                }
            )
        }
    }
}

@Preview
@Composable
fun DetailVisitorsFilterPreview() {
    DetailVisitorsFilter({}, DetailEventFilterType.ALL)
}
