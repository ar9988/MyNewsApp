package com.example.mynewsapp.data.datastore

import javax.inject.Inject

interface DataStoreInterface{
    suspend fun setCategory(category: String)

    suspend fun getCategory(): Result<String>

    suspend fun removeCategory()
}