package com.example.mynewsapp.di.network

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mynewsapp.data.NewsRepository
import com.example.mynewsapp.datasource.network.dto.News
import com.example.mynewsapp.datasource.network.dto.Article
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


    private val _headlines = MutableStateFlow<List<Article>>(emptyList())
    val headlines: StateFlow<List<Article>> = _headlines.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    fun getHeadlines(category: String, country: String, page: Int) {
        _isLoading.value = true
        _error.value = null
        val call = newsRepository.getHeadlines(category, country, page)

        // 요청 정보 로깅
        Log.d(TAG, "Sending request: ${call.request().toString()}")

        call.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "getHeadlines successful. Response code: ${response.code()}")
                    val news = response.body()
                    Log.d(TAG, "Received ${news?.articles?.size ?: 0} articles")
                    _headlines.value = news?.articles?.filter { it.title != "[Removed]" } ?: emptyList()
                } else {
                    Log.e(TAG, "getHeadlines failed. Response code: ${response.code()}")
                    Log.e(TAG, "Error body: ${response.errorBody()?.string()}")

                    // 응답 정보 로깅
                    Log.e(TAG, "Response headers: ${response.headers()}")

                    // 더 자세한 오류 정보 추출
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
                Log.e(TAG, "getHeadlines network request failed", t)
                Log.e(TAG, "Stacktrace: ", t)
                _error.value = "Network error: ${t.message}"
                _isLoading.value = false
            }
        })
    }

    fun getAllNews(query: String, page: Int) {
        newsRepository.getAllNews(query, page).enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                // 응답 처리
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                // 오류 처리
            }
        })
    }
}