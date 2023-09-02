package com.example.room.student

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.room.databinding.ItemBinding
import com.example.room.entity.Student

class StudentRecyclerViewAdapter : ListAdapter<Student, StudentRecyclerViewAdapter.CustomListHolder>(CustomDiffCallback) {

    companion object{
            private val CustomDiffCallback = object : DiffUtil.ItemCallback<Student>(){
                override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
                    return oldItem == newItem
                }
            }
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomListHolder {
            val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CustomListHolder(binding)
        }

        override fun onBindViewHolder(holder: CustomListHolder, position: Int) {
            holder.bind(getItem(position))
        }

        class CustomListHolder(private val binding: ItemBinding): RecyclerView.ViewHolder(binding.root){

            fun bind(student: Student){
                binding.tvId.text = student.id.toString()
                binding.tvName.text = student.name
                binding.tvAge.text = student.age.toString()
            }

        }



}

        
