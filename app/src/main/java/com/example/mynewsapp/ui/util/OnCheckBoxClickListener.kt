package com.example.mynewsapp.ui.util

import com.example.mynewsapp.datasource.network.dto.Article

interface OnCheckBoxClickListener {
    fun onCheckBoxClick(article: Article,isChecked:Boolean)
}