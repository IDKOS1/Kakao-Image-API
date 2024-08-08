package com.example.kakaoimageapi.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApiService {
    @GET("image")
    fun searchImages(
        @Header("Authorization") authorization: String,
        @Query("query") query: String,
        @Query("sort") sort: String? = "accuracy",
        @Query("page") page: Int? = 1,
        @Query("size") size: Int? = 80
    ): Call<KakaoImageResponse>
}
