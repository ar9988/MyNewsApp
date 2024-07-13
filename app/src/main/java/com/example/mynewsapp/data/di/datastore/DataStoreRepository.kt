package com.example.mynewsapp.data.di.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) :DataStoreInterface{

    override suspend fun setCategory(category: String){
        dataStore.edit { preferences ->
            preferences[INTEREST_CATEGORY] = category
        }
    }
    override suspend fun getCategory(): String {
        val flow = dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preference ->
            preference[INTEREST_CATEGORY]
        }
        return flow.firstOrNull() ?: ""
    }


    private companion object{
        val INTEREST_CATEGORY = stringPreferencesKey("interestCategory")
    }
}