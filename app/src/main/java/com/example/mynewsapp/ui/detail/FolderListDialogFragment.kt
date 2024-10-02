package com.example.mynewsapp.ui.detail

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewsapp.databinding.FragmentFolderDialogBinding
import com.example.mynewsapp.databinding.DialogCreateFolderBinding
import com.example.mynewsapp.datasource.network.dto.Article
import com.example.mynewsapp.di.room.RoomViewModel
import com.example.mynewsapp.ui.adapter.FolderRecyclerAdapter
import com.example.mynewsapp.ui.adapter.OnItemClickListener
import com.example.mynewsapp.ui.util.ItemSpacingDecoration
import com.example.mynewsapp.ui.util.ParcelableArticle
import com.example.mynewsapp.ui.util.toParcelableArticle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FolderListDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFolderDialogBinding? = null
    private val roomViewModel: RoomViewModel by viewModels()
    private val adapter = FolderRecyclerAdapter()
    private val binding get() = _binding!!
    private var article: ParcelableArticle? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFolderDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_ARTICLE, ParcelableArticle::class.java)
        } else {
            arguments?.getParcelable(ARG_ARTICLE)
        }
        if (article == null) {
            Toast.makeText(requireContext(), "Error retrieving article", Toast.LENGTH_SHORT).show()
            dismiss()
            return
        }
        binding.closeTv.setOnClickListener {
            dismiss()
        }
        binding.folderCreateBtn.setOnClickListener {
            showCreateFolderDialog()
        }
        setUpRecyclerView()
    }

    private fun showCreateFolderDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogCreateFolderBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.createFolderButton.setOnClickListener {
            val folderName = dialogBinding.folderNameEditText.text.toString().trim()
            if (folderName.isNotEmpty()) {
                roomViewModel.createFolder(folderName)
                Toast.makeText(requireContext(), "Folder created: $folderName", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Folder name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        dialogBinding.cancelFolderButton.setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }

    private fun setUpRecyclerView() {
        adapter.setOnClickListener(object : OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                val folder = adapter.getItem(position)
                article?.let { roomViewModel.saveArticleToFolder(it.toArticleEntity(folder.id), folder.id) }
            }
        })
        viewLifecycleOwner.lifecycleScope.launch {
            roomViewModel.folders.collect {
                binding.folderList.layoutManager = LinearLayoutManager(requireContext())
                adapter.setList(it)
            }
        }
        binding.folderList.addItemDecoration(ItemSpacingDecoration(10))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_ARTICLE = "article"
        fun newInstance(article: Article): FolderListDialogFragment {
            return FolderListDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("article", article.toParcelableArticle())
                }
            }
        }
    }
}