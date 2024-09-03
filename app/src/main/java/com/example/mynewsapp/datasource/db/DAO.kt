package com.example.mynewsapp.datasource.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao{
    @Query("SELECT * FROM newsurl")
    fun getAllURls():List<NewsUrl>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newsUrl: NewsUrl)

    @Delete
    fun delete(newsUrl: NewsUrl)
}

@Dao
interface FolderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(folder: Folder): Long

    @Delete
    suspend fun delete(folder: Folder)

    @Query("SELECT * FROM Folder")
    fun getAllFolders(): Flow<List<Folder>>
}
