package com.example.mynewsapp.di.datastore

import androidx.lifecycle.ViewModel
import com.example.mynewsapp.data.datastore.DataStoreInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreInterface
): ViewModel(){
    fun setCategory(
        name: String
    ) = flow {
        emit(dataStoreRepository.setCategory(name))
    }

    fun getCategory() = flow {
        val result = dataStoreRepository.getCategory()
        emit(result.getOrNull().orEmpty())
    }
}