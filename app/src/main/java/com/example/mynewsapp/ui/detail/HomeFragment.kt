package com.example.mynewsapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mynewsapp.databinding.HomeFragmentBinding
import com.example.mynewsapp.data.datastore.dataStore
import com.example.mynewsapp.di.network.NetworkViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import androidx.fragment.app.viewModels
import com.example.mynewsapp.di.datastore.DataStoreViewModel
import com.example.mynewsapp.di.room.RoomViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class HomeFragment: Fragment() {
    private val networkViewModel: NetworkViewModel by viewModels()
    private val dsViewModel: DataStoreViewModel by viewModels()
    private val roomViewModel: RoomViewModel by viewModels()
    private val binding: HomeFragmentBinding by lazy{
        HomeFragmentBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lifecycleScope.launch {
            //binding.testTv.text=dsViewModel.getCategory().first()
            dsViewModel.getCategory().collect{
                binding.testTv.text = it
            }
        }
        //networkViewModel.getHeadlines("business", "us", 1)
        return binding.root
    }


}