package com.example.mynewsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.databinding.CategoryRecyclerItemBinding
import com.example.mynewsapp.ui.model.CategoryItemModel

class CategoryRecyclerAdapter(private val items: List<CategoryItemModel>,private val context: Context,private val clickListener: OnItemClickListener):
    RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(binding:CategoryRecyclerItemBinding):RecyclerView.ViewHolder(binding.root) {
        val title = binding.categoryTitle
        val image = binding.categoryImage
    }
    interface OnItemClickListener{
        fun onItemClick(v: View, position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CategoryRecyclerItemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val displayMetrics = context.resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height = screenHeight / 5
        holder.itemView.layoutParams = layoutParams
        holder.title.text = items[position].title
        holder.image.setImageResource(items[position].image)
        holder.itemView.setOnClickListener{
            clickListener.onItemClick(holder.itemView,position)
        }
    }

}