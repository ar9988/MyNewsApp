package com.example.mynewsapp.di.room

import android.util.Log
import com.example.mynewsapp.datasource.db.ArticleEntity
import com.example.mynewsapp.datasource.db.FolderEntity
import com.example.mynewsapp.datasource.db.FolderDao
import com.example.mynewsapp.datasource.db.NewsDao
import com.example.mynewsapp.datasource.network.dto.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val newsDao:NewsDao,
    private val folderDao: FolderDao
){
    private val _folders = MutableStateFlow<List<FolderEntity>>(emptyList())
    private val _articleUrls = MutableStateFlow<Set<String>>(emptySet())  // URL Set 추가
    val folders: StateFlow<List<FolderEntity>> = _folders
    val articleUrls: StateFlow<Set<String>> = _articleUrls
    suspend fun insertArticle(articleEntity: ArticleEntity) : Boolean{
        val result = newsDao.insert(articleEntity)
        updateFolderArticleCountAndTime(articleEntity.folderId)
        return result != -1L
    }

    suspend fun isFolderNameExists(folderName: String): Boolean {
        return folderDao.isFolderNameExists(folderName) > 0
    }
    suspend fun updateFolderArticleCountAndTime(folderId: Int) {
        val articleCount = newsDao.getArticleByFolderId(folderId).size
        val updatedAt = getCurrentTime()
        folderDao.updateFolder(updatedAt, articleCount, folderId)
    }


    suspend fun createFolder(folderName: String): Unit {
        val currentTime = getCurrentTime()
        val folder = FolderEntity(name = folderName, createdAt = currentTime, updatedAt = currentTime, articleCount = 0)
        folderDao.insert(folder)
    }

    suspend fun getArticleByFolderId(folderId: Int): List<ArticleEntity> {
        return newsDao.getArticleByFolderId(folderId)
    }

    suspend fun deleteArticle(article: Article){
        val folderId = newsDao.getFolderIdByUrl(article.url)
        newsDao.delete(article.url)
        updateFolderArticleCountAndTime(folderId)
    }
    private fun getCurrentTime(): String {
        return java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
    }

    suspend fun deleteFolder(folder: FolderEntity) {
        folderDao.delete(folder)
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            folderDao.getAllFolders().collect { folders ->
                Log.d("Room", "Folders updated: ${folders.size}")
                _folders.value = folders
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            newsDao.getAllArticles().collect { articles ->
                val urls = articles.map { it.url }.toSet()
                Log.d("Room", "Articles updated: ${urls.size}")
                _articleUrls.value = urls
            }
        }
    }
}