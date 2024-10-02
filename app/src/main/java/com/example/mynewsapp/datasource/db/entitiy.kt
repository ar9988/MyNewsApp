package com.example.mynewsapp.datasource.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
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
    val publishedAt: String? = null
)

@Entity
data class FolderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)