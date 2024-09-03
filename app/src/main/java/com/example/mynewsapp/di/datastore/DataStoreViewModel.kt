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
): ViewModel(){
    fun setCategory(
        category: String
    ) = flow {
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
}