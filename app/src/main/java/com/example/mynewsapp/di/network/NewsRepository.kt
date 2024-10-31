package com.example.mynewsapp.di.network

import com.example.mynewsapp.datasource.network.NewsInterface
import com.example.mynewsapp.datasource.network.dto.News
import retrofit2.Call
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsInterface: NewsInterface,
) {
    fun getHeadlines(category: String, page: Int): Call<News> {
        return newsInterface.getHeadLines(category,page)
    }

    fun searchNewsWithDomains(domains:String,sortBy:String,query: String, page: Int): Call<News> {
        return newsInterface.searchNewsWithDomains(domains,sortBy,query,page)
    }
    fun searchNewsWithExcludeDomains(excludeDomains:String,sortBy:String,query: String, page: Int): Call<News> {
        return newsInterface.searchNewsWithExcludeDomains(excludeDomains,sortBy,query,page)
    }

    fun searchNewsWithoutDomains(sortOption: String, query: String, page: Int): Call<News> {
        return newsInterface.searchNewsWithoutDomains(sortOption,query,page)
    }


//    fun getAllFavoriteArticles(): Flow<List<Article>> {
//        return newsDao.getAllFavoriteArticles()
//    }
//
//    suspend fun saveArticle(article: Article) {
//        newsDao.insert(article)
//    }
//
//    suspend fun isFavorite(url: String): Boolean {
//        return newsDao.isFavorite(url) != null
//    }
}
