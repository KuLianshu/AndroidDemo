package com.example.jetpackpaging.net

import com.example.jetpackpaging.model.Movies
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("pkds.do")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,

    ): Movies

}