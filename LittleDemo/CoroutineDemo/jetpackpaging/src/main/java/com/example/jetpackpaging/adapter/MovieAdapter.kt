package com.example.jetpackpaging.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.jetpackpaging.databinding.MovieItemBinding
import com.example.jetpackpaging.model.Movie
import kotlinx.coroutines.Dispatchers

class MovieAdapter(private val context: Context): PagingDataAdapter<Movie,BindingViewHolder>(object: DiffUtil.ItemCallback<Movie>(){
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        //== 比较内容；===比较引用
        return oldItem == newItem
    }

    }) {
    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            val binding = holder.binding as MovieItemBinding
            binding.networkImage= it.cover
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return BindingViewHolder(binding)

    }
}