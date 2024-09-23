package com.example.mynewsapp.datasource.network

import com.example.mynewsapp.datasource.network.dto.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "3b1a55634db24acdbb86d8eb9108dd7e"

interface NewsInterface {
    @GET("v2/top-headlines?&apiKey=$API_KEY&pageSize=100")
    fun getHeadLines(
        @Query("category") category: String,
        @Query("country") country: String,
        @Query("page") page: Int,
        @Header("User-Agent") userAgent: String = "Mozilla/5.0"
    ): Call<News>

    @GET("v2/everything?apiKey=$API_KEY")
    fun getAllNews(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Header("User-Agent") userAgent: String = "Mozilla/5.0"
    ): Call<News>
}
