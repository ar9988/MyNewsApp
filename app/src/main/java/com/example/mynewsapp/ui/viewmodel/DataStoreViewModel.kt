package com.example.mynewsapp.di.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynewsapp.datasource.datastore.DataStoreInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreInterface
) : ViewModel() {

    // 카테고리 설정
    fun setCategory(category: String) = flow {
        emit(dataStoreRepository.setCategory(category))
    }

    fun getCategory() = flow {
        val result = dataStoreRepository.getCategory()
        emit(result.getOrDefault(""))
    }

    fun removeCategoryData() {
        viewModelScope.launch {
            dataStoreRepository.removeCategory()
        }
    }

    // 나라 설정
    fun setCountry(country: String) = flow {
        emit(dataStoreRepository.setCountry(country))
    }

    fun getCountry() = flow {
        val result = dataStoreRepository.getCountry()
        emit(result.getOrDefault(""))
    }

    fun removeCountryData() {
        viewModelScope.launch {
            dataStoreRepository.removeCountry()
        }
    }

    // 제외할 뉴스 소스 설정
    fun setExcludedNewsSources(sources: String) = flow {
        emit(dataStoreRepository.setExcludedNewsSources(sources))
    }

    fun getExcludedNewsSources() = flow {
        val result = dataStoreRepository.getExcludedNewsSources()
        emit(result.getOrDefault(""))
    }

    fun removeExcludedNewsSourcesData() {
        viewModelScope.launch {
            dataStoreRepository.removeExcludedNewsSources()
        }
    }

    // 검색할 뉴스 소스 설정
    fun setSearchNewsSources(sources: String) = flow {
        emit(dataStoreRepository.setSearchNewsSources(sources))
    }

    fun getSearchNewsSources() = flow {
        val result = dataStoreRepository.getSearchNewsSources()
        emit(result.getOrDefault(""))
    }

    fun removeSearchNewsSourcesData() {
        viewModelScope.launch {
            dataStoreRepository.removeSearchNewsSources()
        }
    }

    // 정렬 방식 설정
    fun setSortBy(sortBy: String) = flow {
        emit(dataStoreRepository.setSortBy(sortBy))
    }

    fun getSortBy() = flow {
        val result = dataStoreRepository.getSortBy()
        emit(result.getOrDefault("publishedAt"))
    }

    fun removeSortByData() {
        viewModelScope.launch {
            dataStoreRepository.removeSortBy()
        }
    }
}
