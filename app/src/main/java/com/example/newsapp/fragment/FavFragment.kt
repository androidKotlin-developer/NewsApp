package com.example.newsapp.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.behavior.SwipeToDeleteCallback
import com.example.newsapp.databinding.FragmentFavBinding
import com.example.newsapp.model.Article
import com.example.newsapp.network.ApiNetwork
import com.example.newsapp.repository.NewsRepo
import com.example.newsapp.viewmodel.NewsViewModelFactory
import com.example.newsapp.viewmodel.ViewModelNews

class FavFragment : Fragment() {

    lateinit var binding: FragmentFavBinding
    lateinit var viewModel: ViewModelNews
    private val retrofitService = ApiNetwork.getInstance()
    private var myadapter: NewsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavBinding.inflate(layoutInflater, container, false)
        val root: View = binding.root

        InitViewModel()
        SearchView()

        return root
    }

    private fun SetAdapter(newsData: List<Article>) {
        myadapter = NewsAdapter(newsData, context!!)
        binding.favRecyclerview.adapter = myadapter
        val item  = binding.favRecyclerview.adapter!!.itemCount
        binding.totalFavCount.text = item.toString()
        binding.favRecyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        myadapter!!.notifyDataSetChanged()


        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.favRecyclerview.adapter as NewsAdapter
                adapter.deleteItem(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.favRecyclerview)
    }

    private fun InitViewModel() {
        viewModel = ViewModelProvider(this, NewsViewModelFactory(NewsRepo(retrofitService))).get(ViewModelNews::class.java)
        viewModel.AllNewsList.observe(viewLifecycleOwner) {
            SetAdapter(it.articles.reversed())
        }
        viewModel.error.observe(viewLifecycleOwner) {}
        viewModel.GetAllNewsList()
    }

    private fun SearchView() {
        viewModel = ViewModelProvider(this, NewsViewModelFactory(NewsRepo(retrofitService))).get(ViewModelNews::class.java)
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.isEmpty()) {
                    viewModel.GetAllNewsList()
                } else viewModel.SearchFavNews(p0.toString())
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }
}
