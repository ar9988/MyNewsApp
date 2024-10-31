package com.example.mynewsapp.ui.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.databinding.SearchFragmentBinding
import com.example.mynewsapp.di.datastore.DataStoreViewModel
import com.example.mynewsapp.di.network.NetworkViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseNewsFragment() {
    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val dsViewModel: DataStoreViewModel by viewModels()
    private var domainSetting:String? = null
    private var excludedDomainSetting:String? = null
    private var sortBySetting:String? =null
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            combine(
                dsViewModel.getSortBy(),
                dsViewModel.getSearchNewsSources(),
                dsViewModel.getExcludedNewsSources()
            ){ sortBy,domain,excludedDomain ->
                Triple(sortBy,domain,excludedDomain)
            }.collect{(sortBy,domain,excludedDomain)->
                domainSetting = domain
                sortBySetting = sortBy
                excludedDomainSetting =excludedDomain
            }
        }
        val items = arrayOf("Domain","Exclude Domain")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchBtn.setOnClickListener {
            val searchText = binding.searchText.text.toString()
            if (searchText.isNotEmpty()) {
                val spinnerIdx = binding.spinner.selectedItemPosition
                if(spinnerIdx==0){
                    networkViewModel.searchNewsWithDomains(domainSetting,sortBySetting,searchText, 1)
                }else if(spinnerIdx==1){
                    networkViewModel.searchNewsWithExcludeDomains(excludedDomainSetting,sortBySetting,searchText, 1)
                }
            } else {
                Toast.makeText(requireContext(), "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getRecyclerView(): RecyclerView = binding.newsRecyclerView
    override fun getProgressBar(): View = binding.progressBar

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
