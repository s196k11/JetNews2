package com.example.jetnews.Network

import com.example.jetnews.Model.News
import com.example.jetnews.constants.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

//https://newsapi.org/v2/top-headlines?q=adani&country=in&category=business&apiKey=2e5c50e075d44b61acfe63aad6d25997
//https://newsapi.org/v2/everything?q=adani&language=hi&apiKey=2e5c50e075d44b61acfe63aad6d25997

//https://newsapi.org/v2/country=in&language=hi&category=sports&top-headlines?apiKey=2e5c50e075d44b61acfe63aad6d25997

@Singleton
interface NewsAPI {

    @GET("top-headlines?apiKey=${Constants.API_KEY}")
    suspend fun topHeadlines(
        @Query("country") country:String = "in",
        @Query("category") category:String = "sports",
    ) : News

    @GET("everything?apiKey=${Constants.API_KEY}")
    suspend fun getSearchNews(
        @Query("language") language: String = "en",
        @Query("q") search:String
    ) : News
}