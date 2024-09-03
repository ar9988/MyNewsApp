package com.example.mynewsapp.datasource.network.dto

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("articles")
    val articles:List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)