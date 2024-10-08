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
    suspend fun getAllURls():List<ArticleEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleEntity):Long

    @Delete
    suspend fun delete(article: ArticleEntity)

    @Query("SELECT * FROM ArticleEntity WHERE folderId = :folderId")
    suspend fun getArticleByFolderId(folderId: Int): List<ArticleEntity>
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
}
