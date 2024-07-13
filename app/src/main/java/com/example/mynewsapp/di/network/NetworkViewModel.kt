package com.example.mynewsapp.di.network

import androidx.lifecycle.ViewModel
import com.example.mynewsapp.data.network.News
import com.example.mynewsapp.data.network.NewsInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val newsInterface: NewsInterface
) : ViewModel() {
    // ViewModel 로직 구현
    fun getHeadlines(category: String, country: String, page: Int) {
        newsInterface.getHeadLines(category, country, page).enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                // 응답 처리
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                // 오류 처리
            }
        })
    }

    fun getAllNews(query: String, page: Int) {
        newsInterface.getAllNews(query, page).enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                // 응답 처리
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                // 오류 처리
            }
        })
    }
}