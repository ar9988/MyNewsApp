package com.example.mynewsapp.di.room

import com.example.mynewsapp.datasource.db.ArticleEntity
import com.example.mynewsapp.datasource.db.FolderEntity
import com.example.mynewsapp.datasource.db.FolderDao
import com.example.mynewsapp.datasource.db.NewsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val newsDao:NewsDao,
    private val folderDao: FolderDao
){
    private val _folders = MutableStateFlow<List<FolderEntity>>(emptyList())
    val folders: StateFlow<List<FolderEntity>> = _folders

    suspend fun insertArticle(articleEntity: ArticleEntity) : Boolean{
        val result = newsDao.insert(articleEntity)
        return result != -1L
    }

    suspend fun isFolderNameExists(folderName: String): Boolean {
        return folderDao.isFolderNameExists(folderName) > 0
    }

    fun getAllFolders(): List<FolderEntity> {
        return folders.value
    }

    suspend fun createFolder(folderName: String): Unit {
        val folder = FolderEntity(name = folderName)
        folderDao.insert(folder)
    }


    init {
        CoroutineScope(Dispatchers.IO).launch {
            folderDao.getAllFolders().collect {
                _folders.value = it
            }
        }
    }
}