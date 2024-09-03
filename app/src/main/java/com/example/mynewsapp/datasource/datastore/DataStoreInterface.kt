package com.example.mynewsapp.datasource.datastore

interface DataStoreInterface{
    suspend fun setCategory(category: String)

    suspend fun getCategory(): Result<String>

    suspend fun removeCategory()
}