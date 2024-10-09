package com.example.mynewsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.databinding.FragmentFolderDialogItemBinding
import com.example.mynewsapp.datasource.db.FolderEntity
import com.example.mynewsapp.datasource.network.dto.Article
import kotlinx.coroutines.channels.ticker

class FolderRecyclerAdapter() :
    RecyclerView.Adapter<FolderRecyclerAdapter.ViewHolder>() {
    private var folderList: List<FolderEntity> = emptyList()
    private lateinit var clickListener: OnItemClickListener
    inner class ViewHolder(binding: FragmentFolderDialogItemBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.folderName
        init {
            binding.root.setOnClickListener {
                clickListener.onItemClick(binding.root,layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentFolderDialogItemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return folderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = folderList[position].name
    }

    fun setList(it: List<FolderEntity>) {
        folderList = it
    }

    fun getItem(idx: Int):FolderEntity{
        return folderList[idx]
    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        clickListener = onItemClickListener
    }
}