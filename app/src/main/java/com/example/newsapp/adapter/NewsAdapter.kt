package com.example.newsapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.newsapp.R
import com.example.newsapp.fragment.FavFragment
import com.example.newsapp.model.Article
import com.example.newsapp.util.CustomView
import com.example.newsapp.util.Utill
import com.example.quizapp.callback.ISC_AlertClickPositiveOnly

class NewsAdapter(newslist: List<Article>, private val context: Context, ) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var listdata : MutableList<Article> = newslist.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_news_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bind(listdata,position)
        holder.cardView.setOnLongClickListener {
           CustomView.alertDialogToast(context,listdata[position].title, 1, object : ISC_AlertClickPositiveOnly {
               override fun onPositiveButtonClicked(view: View?) {
                   deleteItem(position)
               }
           })
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time: TextView = itemView.findViewById(R.id.time_tittle)
        val title: TextView = itemView.findViewById(R.id.tittle)
        val description: TextView = itemView.findViewById(R.id.desc)
        val img: ImageView = itemView.findViewById(R.id.logo)
        val cardView: CardView = itemView.findViewById(R.id.card_view)


        fun bind(itemView: MutableList<Article>, position: Int) {
            val timeAndSource = "${Utill.dateFormat(itemView[position].publishedAt)}" +
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

    fun deleteItem(index: Int) {
        listdata.removeAt(index)
        notifyDataSetChanged()
    }

}



