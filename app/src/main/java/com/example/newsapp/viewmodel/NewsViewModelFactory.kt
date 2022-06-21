package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.repository.NewsRepo
import com.example.quizapp.utill.Constant.Viewmodel_not_found

class NewsViewModelFactory constructor(private val repository: NewsRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ViewModelNews::class.java)) {
            ViewModelNews(this.repository) as T
        } else {
            throw IllegalArgumentException(Viewmodel_not_found)
        }
    }

}