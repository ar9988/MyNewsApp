package com.example.mynewsapp.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewsapp.databinding.ArchiveFragmentBinding
import com.example.mynewsapp.datasource.db.toArticle
import com.example.mynewsapp.datasource.network.dto.Article
import com.example.mynewsapp.datasource.network.dto.Source
import com.example.mynewsapp.di.room.RoomViewModel
import com.example.mynewsapp.ui.adapter.FolderRecyclerAdapter
import com.example.mynewsapp.ui.adapter.NewsRecyclerAdapter
import com.example.mynewsapp.ui.adapter.OnCheckBoxClickListener
import com.example.mynewsapp.ui.adapter.OnItemClickListener
import com.example.mynewsapp.ui.util.ItemSpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchiveFragment : Fragment() {
    private var _binding: ArchiveFragmentBinding? = null
    private val binding get() = _binding!!
    private val folderAdapter = FolderRecyclerAdapter()
    private val newsAdapter = NewsRecyclerAdapter()
    private val roomViewModel: RoomViewModel by viewModels()
    private var isNewsAdapterVisible = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArchiveFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.folderRecycler.adapter = folderAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            roomViewModel.folders.collect {
                binding.folderRecycler.layoutManager = LinearLayoutManager(requireContext())
                folderAdapter.setList(it)
            }
        }
        newsAdapter.setOnClickListener(object: OnItemClickListener {
                override fun onItemClick(v: View, position: Int) {
                    Log.d("item Clicked", "clicked: ${newsAdapter.getItems()[position].url}")
                    val url = newsAdapter.getItems()[position].url
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(url)
                    }
                    startActivity(intent)
                }
        })
        newsAdapter.setOnCheckBoxListener(object : OnCheckBoxClickListener {
            override fun onCheckBoxClick(article: Article, isChecked: Boolean) {
                if(isChecked){
                    Log.d("Checked", article.title)
                }else{
                    Log.d("unChecked", article.title)
                }
            }
        })
        folderAdapter.setOnClickListener(object : OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                val folder = folderAdapter.getItem(position)
                roomViewModel.getArticleByFolderId(folder.id) {
                    binding.folderRecycler.layoutManager = LinearLayoutManager(requireContext())
                    val articles: MutableList<Article> = mutableListOf()
                    it.forEach { articleEntity ->
                        articles.add(articleEntity.toArticle(Source(articleEntity.sourceId, articleEntity.sourceName), true))
                    }
                    newsAdapter.setList(articles)
                    binding.folderRecycler.adapter = newsAdapter
                    isNewsAdapterVisible = true
                }
            }
        })

        binding.folderRecycler.addItemDecoration(ItemSpacingDecoration(10))

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (isNewsAdapterVisible) {
                binding.folderRecycler.adapter = folderAdapter
                isNewsAdapterVisible = false
            } else {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }
}