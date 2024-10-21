package com.example.mynewsapp.datasource.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao{
    @Query("SELECT * FROM ArticleEntity")
    fun getAllArticles():Flow<List<ArticleEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleEntity):Long

    @Query("DELETE FROM ArticleEntity WHERE url = :url")
    suspend fun delete(url: String)

    @Query("SELECT * FROM ArticleEntity WHERE folderId = :folderId")
    suspend fun getArticleByFolderId(folderId: Int): List<ArticleEntity>

    @Query("SELECT folderId FROM ArticleEntity WHERE url = :url LIMIT 1")
    suspend fun getFolderIdByUrl(url: String): Int
}

@Dao
interface FolderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(folder: FolderEntity): Unit

    @Delete
    suspend fun delete(folder: FolderEntity)

    @Query("SELECT * FROM FolderEntity")
    fun getAllFolders(): Flow<List<FolderEntity>>

    @Query("SELECT COUNT(*) FROM FolderEntity WHERE name = :folderName")
    suspend fun isFolderNameExists(folderName: String): Int

    @Query("UPDATE FolderEntity SET updatedAt = :updatedAt, articleCount = :articleCount WHERE id = :folderId")
    suspend fun updateFolder(updatedAt: String, articleCount: Int, folderId: Int)
}
