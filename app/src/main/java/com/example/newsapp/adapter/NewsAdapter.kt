package com.example.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.util.Utils


class NewsAdapter(  private var newslist : List<Article> , private val context: Context) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_news_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newslist,position)

    }

    override fun getItemCount(): Int {
        return newslist.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val time: TextView = itemView.findViewById(R.id.time_tittle)
        val title: TextView = itemView.findViewById(R.id.tittle)
        val description: TextView = itemView.findViewById(R.id.desc)
        val img: ImageView = itemView.findViewById(R.id.logo)


        fun bind(itemView: List<Article>, position: Int) {
            val timeAndSource = "${Utils.dateFormat(itemView[position].publishedAt)}" +
                    " ${itemView[position].source.name}"

            time.text = timeAndSource
            title.text = itemView[position].title
            description.text = itemView[position].description

            Glide.with(this.itemView)
                .load(itemView[position].urlToImage)
                .transform(CenterCrop())
                .into(img)

        }



    }

}