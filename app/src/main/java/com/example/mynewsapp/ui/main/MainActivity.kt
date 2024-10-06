package com.example.mynewsapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.ActivityMainBinding
import com.example.mynewsapp.ui.fragment.ArchiveFragment
import com.example.mynewsapp.ui.fragment.CategoryFragment
import com.example.mynewsapp.ui.fragment.HomeFragment
import com.example.mynewsapp.ui.fragment.SearchFragment
import com.example.mynewsapp.ui.fragment.SettingsFragment
import com.example.mynewsapp.di.datastore.DataStoreViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val dsViewModel:DataStoreViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigationView = binding.navigationBar
        setContentView(binding.root)
        setScreen()
    }
    private fun setScreen(){
        val categoryFlow = dsViewModel.getCategory()
        lifecycleScope.launch {
            categoryFlow.collect{
                if(it.isEmpty()){
                    supportFragmentManager.beginTransaction().replace(R.id.contentFrame,
                        CategoryFragment()
                    ).commit()
                }
                else{
                    supportFragmentManager.beginTransaction().replace(R.id.contentFrame,
                        HomeFragment()
                    ).commit()
                }
            }
        }
        binding.navigationBar.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.menu_home->{
                    lifecycleScope.launch {
                        categoryFlow.collect{
                            if(it.isEmpty()){
                                supportFragmentManager.beginTransaction().replace(R.id.contentFrame,
                                    CategoryFragment()
                                ).commit()
                            }
                            else{
                                supportFragmentManager.beginTransaction().replace(R.id.contentFrame,
                                    HomeFragment()
                                ).commit()
                            }
                        }
                    }
                    true
                }
                R.id.menu_search->{
                    supportFragmentManager.beginTransaction().replace(R.id.contentFrame,
                        SearchFragment()
                    ).commit()
                    true
                }
                R.id.menu_archive->{
                    supportFragmentManager.beginTransaction().replace(R.id.contentFrame,
                        ArchiveFragment()
                    ).commit()
                    true
                }
                R.id.menu_settings->{
                    supportFragmentManager.beginTransaction().replace(R.id.contentFrame,
                        SettingsFragment()
                    ).commit()
                    true
                }
                else -> {
                    false
                }
            }
        }

    }
}