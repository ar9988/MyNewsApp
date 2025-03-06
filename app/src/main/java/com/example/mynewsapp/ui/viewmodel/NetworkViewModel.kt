package com.example.mynewsapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mynewsapp.datasource.network.dto.News
import com.example.mynewsapp.datasource.network.dto.Article
import com.example.mynewsapp.di.network.NewsRepository
import com.example.mynewsapp.di.room.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {
    companion object {
        private const val TAG = "NetworkViewModel"
    }

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    fun getHeadlines(category: String, page: Int) {
        _isLoading.value = true
        _error.value = null
        val call = newsRepository.getHeadlines(category, page)
        val savedArticleUrls = roomRepository.articleUrls.value
        Log.d("viewModel", savedArticleUrls.size.toString())
        call.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    val news = response.body()
                    val articles = news?.articles?.filter { it.title != "[Removed]" } ?: emptyList()
                    _articles.value = articles.map { article ->
                        val isSaved = savedArticleUrls.contains(article.url)
                        article.copy(isFavorite = isSaved)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = when {
                        errorBody?.contains("rate limit") == true -> "Rate limit exceeded"
                        errorBody?.contains("invalid-api-key") == true -> "Invalid API key"
                        else -> "Unknown error: ${response.code()}"
                    }
                    _error.value = errorMessage
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                _error.value = "Network error: ${t.message}"
                _isLoading.value = false
            }
        })
    }

    fun searchNewsWithDomains(domain:String?,sortBy:String?,query: String, page: Int) {
        _isLoading.value = true
        _error.value = null
        var sortOption = "publishedAt"
        val savedArticleUrls = roomRepository.articleUrls.value
        if(sortBy!=null) {
            sortOption = sortBy
        }
        val call = domain?.let {
            newsRepository.searchNewsWithDomains(it, sortOption, query, page)
        } ?: newsRepository.searchNewsWithoutDomains(sortOption, query, page)
        // 요청 URL을 로그로 출력
        Log.d("NetworkViewModel", "Request URL: ${call.request()}")

        call.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if(response.isSuccessful){
                    val news = response.body()
                    val articles = news?.articles?.filter { it.title != "[Removed]" } ?: emptyList()
                    _articles.value = articles.map { article ->
                        val isSaved = savedArticleUrls.contains(article.url)
                        article.copy(
                            isFavorite = isSaved,
                            title = article.title ?: "Default Title" // null일 경우 기본값 설정
                        )
                    }
                }
                else{
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = when {
                        errorBody?.contains("rate limit") == true -> "Rate limit exceeded"
                        errorBody?.contains("invalid-api-key") == true -> "Invalid API key"
                        else -> "Unknown error: ${response.code()}"
                    }
                    _error.value = errorMessage
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                _error.value = "Network error: ${t.message}"
                _isLoading.value = false
            }
        })
    }

    fun searchNewsWithExcludeDomains(domain:String?,sortBy: String?,query: String, page: Int) {
        _isLoading.value = true
        _error.value = null
        var sortOption = "publishedAt"
        val savedArticleUrls = roomRepository.articleUrls.value
        if(sortBy!=null) {
            sortOption = sortBy
        }
        val call = domain?.let {
            newsRepository.searchNewsWithExcludeDomains(it, sortOption, query, page)
        } ?: newsRepository.searchNewsWithoutDomains(sortOption, query, page)

        // 요청 URL을 로그로 출력
        Log.d("NetworkViewModel", "Request URL: ${call.request()}")

        call.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if(response.isSuccessful){
                    val news = response.body()
                    val articles = news?.articles?.filter { it.title != "[Removed]" } ?: emptyList()
                    _articles.value = articles.map { article ->
                        val isSaved = savedArticleUrls.contains(article.url)
                        article.copy(
                            isFavorite = isSaved,
                            title = article.title ?: "Default Title" // null일 경우 기본값 설정
                        )
                    }
                }
                else{
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = when {
                        errorBody?.contains("rate limit") == true -> "Rate limit exceeded"
                        errorBody?.contains("invalid-api-key") == true -> "Invalid API key"
                        else -> "Unknown error: ${response.code()}"
                    }
                    _error.value = errorMessage
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                _error.value = "Network error: ${t.message}"
                _isLoading.value = false
            }
        })
    }


}
