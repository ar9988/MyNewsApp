package com.example.mynewsapp.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewsapp.datasource.network.dto.Article
import com.example.mynewsapp.di.network.NetworkViewModel
import com.example.mynewsapp.di.room.RoomViewModel
import com.example.mynewsapp.ui.adapter.NewsRecyclerAdapter
import com.example.mynewsapp.ui.util.ItemSpacingDecoration
import com.example.mynewsapp.ui.util.OnCheckBoxClickListener
import com.example.mynewsapp.ui.util.OnItemClickListener
import kotlinx.coroutines.launch
import androidx.fragment.app.viewModels

abstract class BaseNewsFragment : Fragment() {
    protected val networkViewModel: NetworkViewModel by viewModels()
    protected val roomViewModel: RoomViewModel by viewModels()
    protected val adapter = NewsRecyclerAdapter()

    abstract fun getRecyclerView(): androidx.recyclerview.widget.RecyclerView
    abstract fun getProgressBar(): View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter.setOnClickListener(object : OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                val url = adapter.getItems()[position].url
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }
                startActivity(intent)
            }
        })

        adapter.setOnCheckBoxListener(object : OnCheckBoxClickListener {
            override fun onCheckBoxClick(article: Article, isChecked: Boolean) {
                if (isChecked) {
                    val bottomSheetDialogFragment = FolderListDialogFragment.newInstance(article)
                    bottomSheetDialogFragment.setOnArticleSavedListener { savedArticle ->
                        updateArticleFavoriteStatus(savedArticle.url, true)
                    }
                    bottomSheetDialogFragment.setOnDismissListener {
                        if (!bottomSheetDialogFragment.isArticleSaved) {
                            updateArticleFavoriteStatus(article.url, false)
                        }
                    }
                    bottomSheetDialogFragment.show(parentFragmentManager, "folderListDialog")
                } else {
                    roomViewModel.deleteArticle(article.url) { success ->
                        if (success) {
                            Toast.makeText(requireContext(), "즐겨찾기가 삭제되었습니다", Toast.LENGTH_SHORT).show()
                            updateArticleFavoriteStatus(article.url, false)
                        } else {
                            Toast.makeText(requireContext(), "즐겨찾기 삭제 실패. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })

        getRecyclerView().adapter = adapter
        getRecyclerView().layoutManager = LinearLayoutManager(requireContext())
        getRecyclerView().addItemDecoration(ItemSpacingDecoration(16))
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            networkViewModel.articles.collect { articles ->
                adapter.setList(articles)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            networkViewModel.isLoading.collect { isLoading ->
                getProgressBar().isVisible = isLoading
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            networkViewModel.error.collect { error ->
                error?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateArticleFavoriteStatus(articleUrl: String, isFavorite: Boolean) {
        val updatedList = adapter.getItems().map { article ->
            if (article.url == articleUrl) {
                article.copy(isFavorite = isFavorite)
            } else {
                article
            }
        }
        adapter.setList(updatedList)
    }
}
