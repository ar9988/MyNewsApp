package com.example.mynewsapp.di.room

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynewsapp.datasource.db.ArticleEntity
import com.example.mynewsapp.datasource.db.FolderEntity
import com.example.mynewsapp.datasource.network.dto.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "RoomViewModel"

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val repository: RoomRepository
):ViewModel(){
    val folders: StateFlow<List<FolderEntity>> = repository.folders

    suspend fun getAllFolders(): List<FolderEntity> {
        return repository.getAllFolders()
    }

    fun createFolder(folderName: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = if (!repository.isFolderNameExists(folderName)) {
                repository.createFolder(folderName)
                true
            } else {
                false
            }
            onResult(result)
        }
    }

    fun saveArticleToFolder(article: ArticleEntity, onResult: (Boolean) -> Unit) {
        Log.d(TAG, "saveArticleToFolder: ")
        viewModelScope.launch {
            try {
                repository.insertArticle(article)
                onResult(true)
            } catch (e: Exception) {
                Log.e(TAG, "Error saving article: ", e)
                onResult(false)
            }
        }
    }
}