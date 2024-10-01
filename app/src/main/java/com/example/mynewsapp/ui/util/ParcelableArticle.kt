package com.example.mynewsapp.ui.util

import android.os.Parcelable
import com.example.mynewsapp.datasource.network.dto.Article
import kotlinx.parcelize.Parcelize


fun Article.toParcelableArticle(): ParcelableArticle {
    return ParcelableArticle(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        sourceId = this.source?.id.toString(), // Assuming Source has an id property
        sourceName = this.source?.name, // Assuming Source has a name property
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage ?: "default_image_url"
    )
}

@Parcelize
data class ParcelableArticle(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val sourceId: String?,
    val sourceName: String?,
    val title: String?,
    val url: String,
    val urlToImage: String,
) : Parcelable
