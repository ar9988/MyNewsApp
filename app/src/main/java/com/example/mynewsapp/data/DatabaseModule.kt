package com.example.mynewsapp.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideNewsDao(appDatabase: AppDatabase): NewsDao{
        return appDatabase.NewsDao()
    }
}