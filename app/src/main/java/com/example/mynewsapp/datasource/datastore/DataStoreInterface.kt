package com.example.mynewsapp.datasource.datastore

interface DataStoreInterface{
    suspend fun setCategory(category: String)
    suspend fun getCategory(): Result<String>
    suspend fun removeCategory()

    suspend fun setCountry(country: String)
    suspend fun getCountry(): Result<String>
    suspend fun removeCountry()

    suspend fun setExcludedNewsSources(sources: String)
    suspend fun getExcludedNewsSources(): Result<String>
    suspend fun removeExcludedNewsSources()

    suspend fun setSearchNewsSources(sources: String)
    suspend fun getSearchNewsSources(): Result<String>
    suspend fun removeSearchNewsSources()

    suspend fun setSortBy(sortBy: String)
    suspend fun getSortBy(): Result<String>
    suspend fun removeSortBy()
}