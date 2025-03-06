package com.example.mynewsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.databinding.HomeFragmentBinding
import com.example.mynewsapp.ui.viewmodel.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

@AndroidEntryPoint
class HomeFragment : BaseNewsFragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val dsViewModel: DataStoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            dsViewModel.getCategory().collect { category ->
                networkViewModel.getHeadlines(category, 1)
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
