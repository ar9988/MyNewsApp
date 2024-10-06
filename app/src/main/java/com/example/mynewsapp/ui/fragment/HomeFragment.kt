package com.example.mynewsapp.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mynewsapp.databinding.HomeFragmentBinding
import com.example.mynewsapp.di.network.NetworkViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewsapp.datasource.network.dto.Article
import com.example.mynewsapp.di.datastore.DataStoreViewModel
import com.example.mynewsapp.di.room.RoomViewModel
import com.example.mynewsapp.ui.adapter.NewsRecyclerAdapter
import com.example.mynewsapp.ui.adapter.OnCheckBoxClickListener
import com.example.mynewsapp.ui.adapter.OnItemClickListener
import com.example.mynewsapp.ui.util.ItemSpacingDecoration

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val networkViewModel: NetworkViewModel by viewModels()
    private val dsViewModel: DataStoreViewModel by viewModels()
    private val roomViewModel: RoomViewModel by viewModels()
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = NewsRecyclerAdapter()
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
        setupRecyclerView()
        observeViewModel()
        lifecycleScope.launch {
            dsViewModel.getCategory().collect { category ->
                networkViewModel.getHeadlines(category, "us", 1)
            }
        }
    }
    private fun setupRecyclerView() {
        adapter.setOnClickListener(object: OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                Log.d("item Clicked", "clicked: ${adapter.getItems()[position].url}")
                val url = adapter.getItems()[position].url
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }
                startActivity(intent)
            }
        })
        adapter.setOnCheckBoxListener(object : OnCheckBoxClickListener {
            override fun onCheckBoxClick(article: Article, isChecked: Boolean) {
                roomViewModel
                if(isChecked){
                    val bottomSheetDialogFragment = FolderListDialogFragment.newInstance(article)
                    bottomSheetDialogFragment.show(parentFragmentManager, "folderListDialog")
                    Log.d("Checked", article.title)
                }else{
                    Log.d("unChecked", article.title)
                }
            }
        })
        binding.newsRecyclerView.adapter = adapter
        binding.newsRecyclerView.addItemDecoration(ItemSpacingDecoration(16))
    }
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            networkViewModel.headlines.collect { articles ->
                binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                adapter.setList(articles)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            networkViewModel.isLoading.collect { isLoading ->
                binding.progressBar.isVisible = isLoading
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            networkViewModel.error.collect { error ->
                error?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}