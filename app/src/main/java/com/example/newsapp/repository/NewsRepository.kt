package com.example.newsapp.repository

import com.example.newsapp.network.ApiServices

class NewsRepository(private val apiService: ApiServices) {

     fun getNews() = apiService.getnewsdata()


}