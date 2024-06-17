package com.example.mynewsapp.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface NewsDao{
    @Query("SELECT * FROM newsurl")
    fun getAllURls():List<NewsUrl>
}