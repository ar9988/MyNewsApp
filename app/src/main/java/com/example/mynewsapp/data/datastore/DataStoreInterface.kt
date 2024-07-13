package com.example.mynewsapp.data.datastore

interface DataStoreInterface {
    suspend fun setCategory(
        category: String
    )

    suspend fun getCategory(): Result<String>
}