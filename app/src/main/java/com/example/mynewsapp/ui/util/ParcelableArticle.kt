package com.example.mynewsapp.ui.util

import android.os.Parcelable
import com.example.mynewsapp.datasource.db.ArticleEntity
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
        urlToImage = this.urlToImage ?: "default_image_url",
        isFavorite = this.isFavorite
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
    val isFavorite:Boolean
) : Parcelable {
    fun toArticleEntity(folderId:Int): ArticleEntity {
        return ArticleEntity(
            author = this.author ?: "Unknown Author", // 기본값 설정
            content = this.content ?: "", // 기본값 설정
            url = this.url,
            sourceId = this.sourceId ?: "", // 기본값 설정
            sourceName = this.sourceName ?: "Unknown Source", // 기본값 설정
            urlToImage = this.urlToImage,
            folderId = folderId, // 폴더 ID를 매개변수로 받음
            title = this.title,
            description = this.description,
            publishedAt = this.publishedAt,
            isFavorite = this.isFavorite
        )
    }
}
