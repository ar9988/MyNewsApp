package com.example.mynewsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.databinding.NewsRecyclerItemBinding
import com.example.mynewsapp.datasource.network.dto.Article

class NewsRecyclerAdapter() : RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>(){
    private lateinit var items:List<Article>

    inner class ViewHolder( binding: NewsRecyclerItemBinding):RecyclerView.ViewHolder(binding.root) {
        val title = binding.titleTv
        val description = binding.descriptionTv
        val source = binding.sourceTv
        val author = binding.authorTv
        val publishedAt = binding.publishedAtTv
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsRecyclerAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsRecyclerItemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsRecyclerAdapter.ViewHolder, position: Int) {
        holder.title.text = items[position].title
        holder.author.text = items[position].author.toString()
        holder.description.text = items[position].description
        holder.publishedAt.text = items[position].publishedAt
        holder.source.text = items[position].source.name
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setList(articles: List<Article>) {
        items = articles
    }

}