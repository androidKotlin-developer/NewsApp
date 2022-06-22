package com.example.newsapp.fragment


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.behavior.SwipeToDeleteCallback
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.model.Article
import com.example.newsapp.network.ApiNetwork
import com.example.newsapp.repository.NewsRepo
import com.example.newsapp.viewmodel.NewsViewModelFactory
import com.example.newsapp.viewmodel.ViewModelNews

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: ViewModelNews
    private val retrofitService = ApiNetwork.getInstance()
    private var myadapter: NewsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initViewModel()
        searchView()

        return root
    }

    private fun setAdapter(newsData: List<Article>) {
        myadapter = NewsAdapter(newsData, context!!)
        binding.rvMainrecyclerview.adapter = myadapter
        val item  = binding.rvMainrecyclerview.adapter!!.itemCount
        binding.totalCount.text = item.toString()
        binding.rvMainrecyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        myadapter!!.notifyDataSetChanged()

        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.rvMainrecyclerview.adapter as NewsAdapter
                adapter.deleteItem(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvMainrecyclerview)
    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(this, NewsViewModelFactory(NewsRepo(retrofitService))).get(ViewModelNews::class.java)
        viewModel.NewsList.observe(viewLifecycleOwner) { setAdapter(it.articles.reversed()) }
        viewModel.error.observe(viewLifecycleOwner) { }
        viewModel.GetNewsList()
    }

    private fun searchView() {
        viewModel = ViewModelProvider(this, NewsViewModelFactory(NewsRepo(retrofitService))).get(ViewModelNews::class.java)
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.isEmpty()) {
                    viewModel.GetNewsList()
                }
                else viewModel.SearchNews(p0.toString())
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }


}