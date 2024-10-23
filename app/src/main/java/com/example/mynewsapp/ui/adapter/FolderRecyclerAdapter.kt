package com.example.mynewsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.databinding.FragmentFolderDialogItemBinding
import com.example.mynewsapp.datasource.db.FolderEntity
import com.example.mynewsapp.ui.util.OnItemClickListener
import java.util.Locale

class FolderRecyclerAdapter :
    RecyclerView.Adapter<FolderRecyclerAdapter.ViewHolder>() {
    private var folderList: List<FolderEntity> = emptyList()
    private lateinit var clickListener: OnItemClickListener
    private lateinit var closeButtonListener: OnItemClickListener
    inner class ViewHolder(binding: FragmentFolderDialogItemBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.folderName
        val created = binding.createdDate
        val count = binding.articleCnt
        val updated = binding.updatedDate
        init {
            binding.root.setOnClickListener {
                clickListener.onItemClick(binding.root,layoutPosition)
            }
            binding.btnClose.setOnClickListener{
                closeButtonListener.onItemClick(binding.root,layoutPosition)
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
        val folder = folderList[position]
        holder.title.text = folder.name
        holder.created.text = String.format(Locale.getDefault(), "생성일 : %s", folder.createdAt)
        holder.updated.text = String.format(Locale.getDefault(), "변경일 : %s", folder.updatedAt)
        holder.count.text = String.format(Locale.getDefault(), "저장 수 : %d", folder.articleCount)
    }


    fun setList(it: List<FolderEntity>) {
        folderList = it
    }

    fun getItem(idx: Int):FolderEntity{
        return folderList[idx]
    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener,onCloseButtonListener: OnItemClickListener) {
        clickListener = onItemClickListener
        closeButtonListener = onCloseButtonListener
    }
}