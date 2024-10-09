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
    private val _articles = MutableStateFlow<List<ArticleEntity>>(emptyList())
    val folders: StateFlow<List<FolderEntity>> = _folders
    val articles: StateFlow<List<ArticleEntity>> = _articles
    suspend fun insertArticle(articleEntity: ArticleEntity) : Boolean{
        val result = newsDao.insert(articleEntity)
        return result != -1L
    }

    suspend fun isFolderNameExists(folderName: String): Boolean {
        return folderDao.isFolderNameExists(folderName) > 0
    }

    suspend fun createFolder(folderName: String): Unit {
        val folder = FolderEntity(name = folderName)
        folderDao.insert(folder)
    }

    suspend fun getArticleByFolderId(folderId: Int): List<ArticleEntity> {
        return newsDao.getArticleByFolderId(folderId)
    }


    init {
        CoroutineScope(Dispatchers.IO).launch {
            folderDao.getAllFolders().collect {
                _folders.value = it
            }
            newsDao.getAllArticles().collect{
                _articles.value = it
            }
        }
    }
}