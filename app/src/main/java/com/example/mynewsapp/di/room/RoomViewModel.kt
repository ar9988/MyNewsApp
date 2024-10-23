package com.example.mynewsapp.di.room

import android.util.Log
import android.view.View
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

    fun getArticleByFolderId(folderId:Int, setList: (list:List<ArticleEntity>) -> Unit){
        viewModelScope.launch {
            val resultList = repository.getArticleByFolderId(folderId)
            setList(resultList)
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
    fun deleteArticle(article: Article, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteArticle(article)
                onResult(true)
            } catch (e: Exception) {
                Log.e(TAG, "Error saving article: ", e)
                onResult(false)
            }
        }
    }

    fun deleteFolder(folder: FolderEntity) {
        viewModelScope.launch {
            repository.deleteFolder(folder)
        }
    }
}