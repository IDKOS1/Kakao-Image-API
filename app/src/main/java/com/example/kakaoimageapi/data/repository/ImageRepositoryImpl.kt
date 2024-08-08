package com.example.kakaoimageapi.data.repository

import com.example.kakaoimageapi.data.model.ImageResponse
import com.example.kakaoimageapi.data.remote.SearchImage

class SearchRepositoryImpl(private val networkClient: SearchImage) : ImageRepository {
    override suspend fun getImageList(searchText: String, page: Int): ImageResponse {
        return networkClient.getImageList(search = searchText, page = page)
    }
}