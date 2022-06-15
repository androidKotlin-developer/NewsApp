package com.example.newsapp.network

import com.example.newsapp.model.NewsData
import com.example.newsapp.util.Utils.BASE_URL
import com.example.newsapp.util.Utils.api_key
import com.example.newsapp.util.Utils.country_name
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServices {

    @GET("top-headlines")
    fun getnewsdata(
        @Query("country")   country: String = country_name,
        @Query("apiKey") apiKey: String = api_key,
    ): Call<NewsData>


    companion object {

        var apiServices: ApiServices? = null

        fun getInstance(): ApiServices {
            if (apiServices == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                apiServices = retrofit.create(ApiServices::class.java)
            }
            return apiServices!!
        }
    }
}
