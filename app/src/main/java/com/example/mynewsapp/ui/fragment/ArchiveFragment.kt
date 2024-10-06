package com.example.mynewsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewsapp.databinding.ArchiveFragmentBinding
import com.example.mynewsapp.di.room.RoomViewModel
import com.example.mynewsapp.ui.adapter.FolderRecyclerAdapter
import com.example.mynewsapp.ui.adapter.NewsRecyclerAdapter
import com.example.mynewsapp.ui.adapter.OnItemClickListener
import com.example.mynewsapp.ui.util.ItemSpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchiveFragment:Fragment() {
    private var _binding: ArchiveFragmentBinding? = null
    private val binding get() = _binding!!
    private val folderAdapter = FolderRecyclerAdapter()
    private val newsAdapter = NewsRecyclerAdapter()
    private val roomViewModel: RoomViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArchiveFragmentBinding.inflate(inflater,container,false)
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
        folderAdapter.setOnClickListener(object : OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                val folder = folderAdapter.getItem(position)
                // set news recyclerview adapter
            }
        })
        binding.folderRecycler.addItemDecoration(ItemSpacingDecoration(10))
    }
}