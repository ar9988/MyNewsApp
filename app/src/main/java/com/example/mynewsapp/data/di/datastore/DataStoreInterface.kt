package com.example.mynewsapp.data.di.datastore

interface DataStoreInterface {
    suspend fun setCategory(
        category: String
    )

    suspend fun getCategory(): String
}