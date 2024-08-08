package com.example.kakaoimageapi.ui

import com.example.androidtask.presentation.ListItem
import com.example.kakaoimageapi.data.model.ImageDocument
import java.util.UUID

fun List<ImageDocument>.toImageListItem(): List<ListItem> {
    return this.map {
        ListItem.ImageItem(
            uuid = UUID.randomUUID().toString(),
            thumbnailUrl = it.thumbnailUrl ?: "",
            siteName = it.displaySitename ?: "",
            datetime = it.datetime ?: "",
            isBookmarked = false,
        )
    }
}