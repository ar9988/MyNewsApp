package com.example.mynewsapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mynewsapp.databinding.HomeFragmentBinding
import com.example.mynewsapp.util.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeFragment: Fragment() {
    private val binding: HomeFragmentBinding by lazy{
        HomeFragmentBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val interestCategory = stringPreferencesKey("interestCategory")
        val categoryFlow: Flow<String> = requireContext().dataStore.data.map { preferences ->
            preferences[interestCategory] ?: ""
        }
        lifecycleScope.launch {
            categoryFlow.collect{
                binding.testTv.text = it
            }
        }
        return binding.root
    }
}