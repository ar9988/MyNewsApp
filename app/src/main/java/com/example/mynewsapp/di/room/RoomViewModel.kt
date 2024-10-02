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

    fun createFolder(folderName: String): Int {
        return repository.createFolder(FolderEntity(name = folderName))
    }

    fun saveArticleToFolder(article: ArticleEntity, folderId: Int) {
        Log.d(TAG, "saveArticleToFolder: ")
        viewModelScope.launch {
            repository.insertArticle(folderId,article)
        }
    }
}