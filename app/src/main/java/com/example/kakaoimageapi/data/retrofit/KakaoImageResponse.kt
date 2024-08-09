package com.example.kakaoimageapi.data.retrofit

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class KakaoImageResponse(
    val meta: Meta,
    val documents: List<Document>
)

data class Meta(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("pageable_count") val pageableCount: Int,
    @SerializedName("is_end") val isEnd: Boolean
)

data class Document(
    val uuid: String = UUID.randomUUID().toString(),
    @SerializedName("collection") val collection: String,
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("display_sitename") val displaySitename: String,
    @SerializedName("doc_url") val docUrl: String,
    @SerializedName("datetime") val datetime: String,
    val isBookmarked: Boolean = false
)
