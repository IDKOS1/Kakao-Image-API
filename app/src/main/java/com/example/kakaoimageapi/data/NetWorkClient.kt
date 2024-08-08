package com.example.kakaoimageapi.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.example.kakaoimageapi.BuildConfig
import com.example.kakaoimageapi.data.remote.SearchImage
import okhttp3.Interceptor
import okio.IOException
import kotlin.jvm.Throws

object NetworkClient {
    private const val BASE_URL = "https://dapi.kakao.com/v2/search/"
    val kakaoNetwork: SearchImage by lazy { getClient().create(SearchImage::class.java) }

    private fun getClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient(NetworkInterceptor()))
            .build()
    }

    private fun getOkHttpClient(interceptor: NetworkInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .run {
                addInterceptor(interceptor)
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                build()
            }
    }

    class NetworkInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_API_KEY}")
                .build()
            return chain.proceed(newRequest)
        }
    }
}