package com.example.flowpractice.net


import com.example.flowpractice.model.Article
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {

    @GET("article")
    suspend fun searchArticles(
        @Query("key") key: String
    ): List<Article>

}