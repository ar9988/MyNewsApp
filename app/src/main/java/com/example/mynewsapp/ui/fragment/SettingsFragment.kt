package com.example.mynewsapp.ui.fragment

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
    private var _binding:SettingsFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.interestSettingBtn.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(
                R.id.contentFrame,
                CategoryFragment()
            ).commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}