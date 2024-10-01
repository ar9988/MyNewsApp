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

    fun insertArticle(folderId:Int, articleEntity: ArticleEntity){

    }

    fun getAllFolders(): List<FolderEntity> {
        return folders.value
    }

    fun createFolder(folder: FolderEntity): Int {
        return 0
    }


    init {
        CoroutineScope(Dispatchers.IO).launch {
            folderDao.getAllFolders().collect {
                _folders.value = it
            }
        }
    }
}