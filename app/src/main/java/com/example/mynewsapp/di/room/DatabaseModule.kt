package com.example.mynewsapp.di.room

import android.content.Context
import androidx.room.Room
import com.example.mynewsapp.datasource.db.AppDatabase
import com.example.mynewsapp.datasource.db.FolderDao
import com.example.mynewsapp.datasource.db.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideNewsDao(appDatabase: AppDatabase): NewsDao{
        return appDatabase.NewsDao()
    }

    @Provides
    fun provideFolderDao(appDatabase: AppDatabase):FolderDao{
        return appDatabase.FolderDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context):AppDatabase{
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "MyDatabase"
        ).build()
    }
}