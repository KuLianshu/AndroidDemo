package com.example.jetpackpaging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.jetpackpaging.model.Movie
import com.example.jetpackpaging.paging.MoviePagingSource
import kotlinx.coroutines.flow.Flow

class MovieViewModel: ViewModel(){

    fun loadMovie() : Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(
                pageSize = 8
            ),
            pagingSourceFactory = {MoviePagingSource()}
        ).flow
    }

}