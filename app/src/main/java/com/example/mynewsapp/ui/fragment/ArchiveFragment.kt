package com.example.mynewsapp.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewsapp.databinding.ArchiveFragmentBinding
import com.example.mynewsapp.datasource.db.toArticle
import com.example.mynewsapp.datasource.network.dto.Article
import com.example.mynewsapp.datasource.network.dto.Source
import com.example.mynewsapp.ui.viewmodel.RoomViewModel
import com.example.mynewsapp.ui.adapter.FolderRecyclerAdapter
import com.example.mynewsapp.ui.adapter.NewsRecyclerAdapter
import com.example.mynewsapp.ui.util.OnCheckBoxClickListener
import com.example.mynewsapp.ui.util.OnItemClickListener
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
        setupBackPressCallback()
        setupRecyclerAdapters()
    }

    private fun setupBackPressCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (isNewsAdapterVisible) {
                binding.folderRecycler.adapter = folderAdapter
                isNewsAdapterVisible = false
            } else {
                isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun setupRecyclerAdapters() {
        binding.folderRecycler.adapter = folderAdapter
        binding.folderRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.folderRecycler.addItemDecoration(ItemSpacingDecoration(10))

        viewLifecycleOwner.lifecycleScope.launch {
            roomViewModel.folders.collect { folders ->
                Log.d("ArchiveFragment", "Collected Folders: $folders")
                if (folders.isNotEmpty()) {
                    folderAdapter.setList(folders)
                    folderAdapter.notifyDataSetChanged()
                }
            }
        }

        newsAdapter.setOnClickListener(object : OnItemClickListener {
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
                if (isChecked) {
                    article.title?.let { Log.d("Checked", it) }
                } else {
                    roomViewModel.deleteArticle(article){ success ->
                        if (success) {
                            Toast.makeText(requireContext(), "즐겨찾기가 삭제되었습니다", Toast.LENGTH_SHORT).show()
                            val position = newsAdapter.getItems().indexOf(article)
                            if (position != -1) {
                                newsAdapter.removeItem(position)
                            }
                        } else {
                            Toast.makeText(requireContext(), "즐겨찾기 삭제 실패. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })

        folderAdapter.setOnClickListener(object : OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                val folder = folderAdapter.getItem(position)
                roomViewModel.getArticleByFolderId(folder.id) { articles ->
                    binding.folderRecycler.layoutManager = LinearLayoutManager(requireContext())
                    val articleList: MutableList<Article> = mutableListOf()
                    articles.forEach { articleEntity ->
                        articleList.add(
                            articleEntity.toArticle(Source(articleEntity.sourceId, articleEntity.sourceName))
                        )
                    }
                    newsAdapter.setList(articleList)
                    binding.folderRecycler.adapter = newsAdapter
                    isNewsAdapterVisible = true
                }
            }
        }, object : OnItemClickListener{
            override fun onItemClick(v: View, position: Int) {
                val folder = folderAdapter.getItem(position)
                roomViewModel.deleteFolder(folder)
            }

        })
    }
}