package com.example.mynewsapp.data.di.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.mynewsapp.data.datastore.dataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataStoreModule {

    @Binds
    @Singleton
    abstract fun bindDataStoreRepository(
        myRepository: DataStoreRepository
    ):DataStoreInterface

    companion object{
        @Provides
        @Singleton
        fun provideDataStore(@ApplicationContext applicationContext: Context
        ): DataStore<Preferences> {
            return applicationContext.dataStore
        }
    }
}