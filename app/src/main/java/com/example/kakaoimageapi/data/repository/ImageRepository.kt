package com.example.kakaoimageapi.data.repository

import com.example.kakaoimageapi.data.model.ImageResponse

interface ImageRepository {
    suspend fun getImageList(searchText: String, page: Int): ImageResponse
}