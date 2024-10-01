package com.example.mynewsapp.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class, FolderEntity::class], version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun NewsDao(): NewsDao

    abstract fun FolderDao(): FolderDao
}
