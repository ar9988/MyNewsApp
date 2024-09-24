package com.example.mynewsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.databinding.NewsRecyclerItemBinding
import com.example.mynewsapp.datasource.network.dto.Article

class NewsRecyclerAdapter : RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>(){
    private lateinit var items:List<Article>
    private lateinit var clickListener: OnItemClickListener
    private lateinit var checkBoxListener: OnCheckBoxClickListener

    inner class ViewHolder( binding: NewsRecyclerItemBinding):RecyclerView.ViewHolder(binding.root) {
        val title = binding.titleTv
        val description = binding.descriptionTv
        val source = binding.sourceTv
        val author = binding.authorTv
        val publishedAt = binding.publishedAtTv
        val line = binding.line2
        val checkBox = binding.favoriteCheckbox
    }
    interface OnItemClickListener{
        fun onItemClick(v: View, position: Int)
    }
    fun setOnClickListener(clickListener: OnItemClickListener) {
        this.clickListener = clickListener
    }
    fun setOnCheckBoxListener(checkBoxClickListener: OnCheckBoxClickListener){
        this.checkBoxListener = checkBoxClickListener
    }

    fun getItems() : List<Article>{
        return items
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsRecyclerAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsRecyclerItemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.title.text = item.title

        val authorText = item.author?.toString() ?: ""
        val sourceText = item.source.name

        if (authorText.isNotEmpty() && authorText == sourceText) {
            holder.author.visibility = View.GONE
            holder.source.text = sourceText
            holder.source.visibility = View.VISIBLE
        } else {
            holder.author.text = authorText
            holder.author.visibility = if (authorText.isNotEmpty()) View.VISIBLE else View.GONE
            holder.source.text = sourceText
            holder.source.visibility = View.VISIBLE
        }

        val descriptionText = item.description ?: ""
        if (descriptionText.isNotEmpty()) {
            holder.description.text = descriptionText
            holder.description.visibility = View.VISIBLE
            holder.line.visibility = View.VISIBLE
        } else {
            holder.description.visibility = View.GONE
            holder.line.visibility = View.GONE
        }

        holder.publishedAt.text = item.publishedAt

        holder.itemView.setOnClickListener{
            clickListener.onItemClick(holder.itemView,position)
        }

        holder.checkBox.setOnCheckedChangeListener{_,isChecked ->
            checkBoxListener.onCheckBoxClick(items[position],isChecked)
        }

    }


    override fun getItemCount(): Int {
        return items.size
    }

    fun setList(articles: List<Article>) {
        items = articles
    }

}