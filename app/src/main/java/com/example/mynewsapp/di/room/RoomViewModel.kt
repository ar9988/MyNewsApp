package com.example.mynewsapp.di.room

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynewsapp.datasource.db.ArticleEntity
import com.example.mynewsapp.datasource.db.FolderEntity
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
    fun deleteArticle(url: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteArticle(url)
                onResult(true)
            } catch (e: Exception) {
                Log.e(TAG, "Error saving article: ", e)
                onResult(false)
            }
        }
    }
}