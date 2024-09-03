package com.example.mynewsapp.datasource.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Folder::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("listID"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["listID"])]
)
data class NewsUrl(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val url:String,
    val listID:Int
)

@Entity
data class Folder(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)