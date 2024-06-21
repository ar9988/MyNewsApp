package com.example.mynewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.ActivityMainBinding
import com.example.mynewsapp.util.dataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setScreen()
    }
    private fun setScreen(){
        val interestCategory = stringPreferencesKey("interestCategory")
        val categoryFlow: Flow<String> = dataStore.data.map { preferences ->
            preferences[interestCategory] ?: ""
        }
        lifecycleScope.launch {
            categoryFlow.collect{
                if(it.isEmpty()){
                    supportFragmentManager.beginTransaction().replace(R.id.contentFrame,CategoryFragment()).commit()
                }
                else{
                    supportFragmentManager.beginTransaction().replace(R.id.contentFrame,HomeFragment()).commit()
                }
            }
        }
        binding.navigationBar.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.menu_home->{
                    supportFragmentManager.beginTransaction().replace(R.id.contentFrame,HomeFragment()).commit()
                    true
                }
                R.id.menu_search->{
                    supportFragmentManager.beginTransaction().replace(R.id.contentFrame,SearchFragment()).commit()
                    true
                }
                R.id.menu_archive->{
                    supportFragmentManager.beginTransaction().replace(R.id.contentFrame,ArchiveFragment()).commit()
                    true
                }
                R.id.menu_settings->{
                    supportFragmentManager.beginTransaction().replace(R.id.contentFrame,SettingsFragment()).commit()
                    true
                }
                else -> {
                    false
                }
            }
        }

    }
}