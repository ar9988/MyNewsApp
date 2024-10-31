package com.example.mynewsapp.di.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mynewsapp.datasource.datastore.DataStoreInterface
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreInterface {

    override suspend fun setCategory(category: String) {
        Result.runCatching {
            dataStore.edit { preferences ->
                preferences[INTEREST_CATEGORY] = category
            }
        }
    }

    override suspend fun getCategory(): Result<String> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[INTEREST_CATEGORY]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }
    }

    override suspend fun removeCategory() {
        dataStore.edit { preference ->
            preference.remove(INTEREST_CATEGORY)
        }
    }

    // 나라 설정
    override suspend fun setCountry(country: String) {
        Result.runCatching {
            dataStore.edit { preferences ->
                preferences[COUNTRY_KEY] = country
                Log.d("DataStoreRepository", "Saved Country: $country") // 저장 시 로그 추가
            }
        }
    }

    override suspend fun getCountry(): Result<String> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[COUNTRY_KEY]
                }
            val value = flow.firstOrNull() ?: ""
            Log.d("DataStoreRepository", "Fetched Country: $value") // 로그 추가
            value
        }
    }

    override suspend fun removeCountry() {
        dataStore.edit { preferences ->
            preferences.remove(COUNTRY_KEY)
        }
    }

    // 제외할 뉴스 소스 설정
    override suspend fun setExcludedNewsSources(sources: String) {
        Result.runCatching {
            dataStore.edit { preferences ->
                preferences[EXCLUDED_NEWS_SOURCES_KEY] = sources
            }
        }
    }

    override suspend fun getExcludedNewsSources(): Result<String> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[EXCLUDED_NEWS_SOURCES_KEY]
                }
            flow.firstOrNull() ?: ""
        }
    }

    override suspend fun removeExcludedNewsSources() {
        dataStore.edit { preferences ->
            preferences.remove(EXCLUDED_NEWS_SOURCES_KEY)
        }
    }

    // 검색할 뉴스 소스 설정
    override suspend fun setSearchNewsSources(sources: String) {
        Result.runCatching {
            dataStore.edit { preferences ->
                preferences[SEARCH_NEWS_SOURCES_KEY] = sources
            }
        }
    }

    override suspend fun getSearchNewsSources(): Result<String> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[SEARCH_NEWS_SOURCES_KEY]
                }
            flow.firstOrNull() ?: ""
        }
    }

    override suspend fun removeSearchNewsSources() {
        dataStore.edit { preferences ->
            preferences.remove(SEARCH_NEWS_SOURCES_KEY)
        }
    }

    // 정렬 방식 설정
    override suspend fun setSortBy(sortBy: String) {
        Result.runCatching {
            dataStore.edit { preferences ->
                preferences[SORT_BY_KEY] = sortBy
            }
        }
    }

    override suspend fun getSortBy(): Result<String> {
        return Result.runCatching {
            val flow = dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[SORT_BY_KEY]
                }
            flow.firstOrNull() ?: "publishedAt"
        }
    }

    override suspend fun removeSortBy() {
        dataStore.edit { preferences ->
            preferences.remove(SORT_BY_KEY)
        }
    }

    private companion object {
        val INTEREST_CATEGORY = stringPreferencesKey("interestCategory")
        val COUNTRY_KEY = stringPreferencesKey("country")
        val EXCLUDED_NEWS_SOURCES_KEY = stringPreferencesKey("excludedNewsSources")
        val SEARCH_NEWS_SOURCES_KEY = stringPreferencesKey("searchNewsSources")
        val SORT_BY_KEY = stringPreferencesKey("sortBy")
    }
}
