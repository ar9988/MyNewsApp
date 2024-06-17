package com.example.mynewsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsUrl(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val url:String,
    val listID:Int
)