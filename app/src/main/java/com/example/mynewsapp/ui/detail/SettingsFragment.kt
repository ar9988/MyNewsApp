package com.example.mynewsapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.SettingsFragmentBinding
import com.example.mynewsapp.di.datastore.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment:Fragment() {
    private val dsViewModel:DataStoreViewModel by viewModels()
    private val binding:SettingsFragmentBinding by lazy {
        SettingsFragmentBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.interestSettingBtn.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(
                R.id.contentFrame,
                CategoryFragment()
            ).commit()
        }
        return binding.root
    }
}