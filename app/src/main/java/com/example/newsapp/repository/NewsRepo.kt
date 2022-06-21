package com.example.newsapp.repository

import com.example.newsapp.network.ApiNetwork

class NewsRepo(private val apinetwork: ApiNetwork) {

     fun GetNews() = apinetwork.getnewsdata()

     fun GetAllNews() = apinetwork.getallnews()


}