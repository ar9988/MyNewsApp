package com.example.mynewsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Database(entities = [NewsUrl::class], version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun NewsDao():NewsDao
}

@Provides
@Singleton
fun provideAppDataBase(@ApplicationContext appContext: Context):AppDatabase{
    return Room.databaseBuilder(
        appContext,
        AppDatabase::class.java,
        "NewsUrlDB"
    ).build()
}