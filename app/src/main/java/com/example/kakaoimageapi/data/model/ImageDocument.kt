package com.example.kakaoimageapi.data.model

import com.google.gson.annotations.SerializedName

data class ImageDocument(
    @SerializedName("collection") val collection: String?,
    @SerializedName("datetime") val datetime: String?,
    @SerializedName("display_sitename") val displaySitename: String?,
    @SerializedName("doc_url") val docUrl: String?,
    @SerializedName("height") val height: Int?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("thumbnail_url") val thumbnailUrl: String?,
    @SerializedName("width") val width: Int?
)

data class ImageResponse(
    @SerializedName("documents") val documents: List<ImageDocument>?,
    @SerializedName("meta") val meta: Meta?,
)

data class Meta(
    @SerializedName("is_end") val isEnd: Boolean?,
    @SerializedName("pageable_count") val pageableCount: Int?,
    @SerializedName("total_count") val totalCount: Int?
)