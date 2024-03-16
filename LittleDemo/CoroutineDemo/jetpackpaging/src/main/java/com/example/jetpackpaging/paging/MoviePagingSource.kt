package com.example.jetpackpaging.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetpackpaging.model.Movie
import com.example.jetpackpaging.net.MovieApi
import com.example.jetpackpaging.net.RetrofitClient

class MoviePagingSource : PagingSource<Int,Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }

    //请求服务器
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val currentPage = 1
        val pageSize = 8
        val movies = RetrofitClient.createApi(MovieApi::class.java).getMovies(currentPage,pageSize)


    }
}