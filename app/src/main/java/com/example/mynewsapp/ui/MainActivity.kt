package com.example.mynewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.mynewsapp.R
import com.example.mynewsapp.util.dataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val interestCategory = stringPreferencesKey("interestCategory")
        val categoryFlow: Flow<String> = dataStore.data.map { preferences ->
            preferences[interestCategory] ?: ""
        }
        lifecycleScope.launch {
            categoryFlow.collect{
                if(it.isEmpty()){
                    
                }
                else{

                }
            }
        }
    }
}