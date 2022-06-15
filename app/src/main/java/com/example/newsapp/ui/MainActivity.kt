package com.example.newsapp.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.model.Article
import com.example.newsapp.network.ApiServices
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.Utils.toast
import com.example.newsapp.viewmodel.NewsModelFactory
import com.example.newsapp.viewmodel.NewsViewModel



class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NewsViewModel
    private val retrofitService = ApiServices.getInstance()
    var myadapter: NewsAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        repeatCall()
        searchView()

    }



    fun searchView() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.isEmpty()) {
                    viewModel.getnewslist()
                } else viewModel.searchProject(p0.toString())
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    fun setAdapter(newsData: List<Article>) {
        myadapter = NewsAdapter(newsData, this)
        binding.rvMainrecyclerview.adapter = myadapter
        binding.rvMainrecyclerview.layoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL,false)
        myadapter!!.notifyDataSetChanged()
    }

    fun initviewmodel() {
        viewModel = ViewModelProvider(this, NewsModelFactory(NewsRepository(retrofitService))).get(NewsViewModel::class.java)
        viewModel.newslist.observe(this) {
            setAdapter(it.articles.reversed())
        }
        viewModel.error.observe(this) { }
        viewModel.getnewslist()
    }

    fun repeatCall() {
        object : CountDownTimer(300000, 60000) {

            override fun onTick(millisUntilFinished: Long) {
                initviewmodel()
            }

            override fun onFinish() {
                repeatCall()
                toast("Feed Refreshed")
            }
        }.start()
    }
}