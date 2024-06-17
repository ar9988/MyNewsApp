package com.example.mynewsapp.data

import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsDao:NewsDao) {

}