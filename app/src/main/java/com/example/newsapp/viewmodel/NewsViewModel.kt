package com.example.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.NewsData
import com.example.newsapp.repository.NewsRepository
import retrofit2.Callback
import java.util.*

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    val newslist = MutableLiveData<NewsData>()
    val error = MutableLiveData<String>()

    fun getnewslist(){
    val dataresponse = repository.getNews()
        dataresponse.enqueue(object : Callback<NewsData>{
            override fun onFailure(call: retrofit2.Call<NewsData>, t: Throwable) {
                error.postValue(t.message)
            }

            override fun onResponse(call: retrofit2.Call<NewsData>, response: retrofit2.Response<NewsData>) {
                newslist.postValue(response.body())
            }
        })
    }

    fun searchProject(search: String) {
        val filtername = newslist.value?.articles?.filter {
            it.title.lowercase(Locale.ROOT).contains(search.lowercase(Locale.ROOT))
        }
        newslist.postValue(newslist.value?.copy(articles = filtername!!))
    }
}