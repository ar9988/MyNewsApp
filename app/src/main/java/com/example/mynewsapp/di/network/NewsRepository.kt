package com.example.mynewsapp.data

import android.util.Log
import com.example.mynewsapp.datasource.db.NewsDao
import com.example.mynewsapp.datasource.network.NewsInterface
import com.example.mynewsapp.datasource.network.dto.Article
import com.example.mynewsapp.datasource.network.dto.News
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsInterface: NewsInterface,
    private val newsDao: NewsDao
) {
    fun getHeadlines(category: String,country: String, page: Int): Call<News> {
        return newsInterface.getHeadLines(category,country,page)
    }

    fun getAllNews(query: String, page: Int): Call<News> {
        return newsInterface.getAllNews(query,page)
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
