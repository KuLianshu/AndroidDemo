package com.example.databinding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.databinding.databinding.LayoutRecyclerViewItemBinding
import com.example.databinding.entity.Idol
import com.example.databinding.entity.NewIdol

class RecyclerViewAdapter(private val idols: MutableList<NewIdol>)
    : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = LayoutRecyclerViewItemBinding
            .inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return idols.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val idol = idols[position]
        holder.binding.idol = idol
    }

    class MyViewHolder(val binding: LayoutRecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}