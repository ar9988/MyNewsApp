package com.example.mynewsapp.datasource.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.mynewsapp.datasource.network.dto.Article
import com.example.mynewsapp.datasource.network.dto.Source

@Entity(
    foreignKeys = [ForeignKey(
        entity = FolderEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("folderId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["folderId"])]
)
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String,
    val content: String,
    val url: String,
    val sourceId: String,
    val sourceName: String,
    val urlToImage: String,
    val folderId: Int,
    val title: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val isFavorite: Boolean
)

@Entity
data class FolderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val createdAt: String,
    val updatedAt:String,
    val articleCount:Int
)

fun ArticleEntity.toArticle(source: Source? = null): Article {
    return Article(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt ?: "", // 기본값으로 빈 문자열을 사용
        source = source,
        title = this.title ?: "", // 기본값으로 빈 문자열을 사용
        url = this.url,
        urlToImage = this.urlToImage,
        isFavorite = this.isFavorite
    )
}