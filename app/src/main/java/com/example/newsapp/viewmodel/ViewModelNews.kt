package com.example.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.repository.NewsRepo
import retrofit2.Callback
import java.util.*

class ViewModelNews(private val repository: NewsRepo) : ViewModel() {

    val NewsList = MutableLiveData<NewsResponse>()
    val AllNewsList = MutableLiveData<NewsResponse>()
    val error = MutableLiveData<String>()


    fun GetAllNewsList(){
        val dataresponse = repository.GetAllNews()
        dataresponse.enqueue(object : Callback<NewsResponse>{
            override fun onFailure(call: retrofit2.Call<NewsResponse>, t: Throwable) {
                error.postValue(t.message)
            }

            override fun onResponse(call: retrofit2.Call<NewsResponse>, response: retrofit2.Response<NewsResponse>) {
                AllNewsList.postValue(response.body())
            }
        })
    }

    fun GetNewsList(){
        val dataresponse = repository.GetNews()
        dataresponse.enqueue(object : Callback<NewsResponse>{
            override fun onFailure(call: retrofit2.Call<NewsResponse>, t: Throwable) {
                error.postValue(t.message)
            }

            override fun onResponse(call: retrofit2.Call<NewsResponse>, response: retrofit2.Response<NewsResponse>) {
                NewsList.postValue(response.body())
            }
        })
    }

    fun SearchNews(search: String) {
        val filtername = NewsList.value?.articles?.filter {
            it.title.lowercase(Locale.ROOT).contains(search.lowercase(Locale.ROOT))
        }
        NewsList.postValue(NewsList.value?.copy(articles = filtername!!))
    }


    fun SearchFavNews(search: String) {
        val filtername = AllNewsList.value?.articles?.filter {
            it.title.lowercase(Locale.ROOT).contains(search.lowercase(Locale.ROOT))
        }
        AllNewsList.postValue(AllNewsList.value?.copy(articles = filtername!!))
    }
}