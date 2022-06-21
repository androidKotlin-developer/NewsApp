package com.example.newsapp.network

import com.example.newsapp.model.NewsResponse
import com.example.quizapp.utill.Constant.BASE_URL
import com.example.quizapp.utill.Constant.FROM
import com.example.quizapp.utill.Constant.Q
import com.example.quizapp.utill.Constant.SORTBY
import com.example.quizapp.utill.Constant.TO
import com.example.quizapp.utill.Constant.api_key
import com.example.quizapp.utill.Constant.country_name
import com.example.quizapp.utill.Constant.Top_HeadLines
import com.example.quizapp.utill.Constant.Everything
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiNetwork {

    @GET(Top_HeadLines)
    fun getnewsdata(
        @Query("country")   country: String = country_name,
        @Query("apiKey") apiKey: String = api_key,
    ): Call<NewsResponse>

    @GET(Everything)
    fun getallnews(
        @Query("q") q: String = Q ,
        @Query("from") from: String = FROM,
        @Query("to") to: String = TO,
        @Query("sortBy") sortBy: String = SORTBY,
        @Query("apiKey") apiKey: String = api_key,
    ): Call<NewsResponse>


    companion object {

        private var apiNetwork: ApiNetwork? = null

        fun getInstance(): ApiNetwork {
            if (apiNetwork == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                apiNetwork = retrofit.create(ApiNetwork::class.java)
            }
            return apiNetwork!!
        }
    }
}
