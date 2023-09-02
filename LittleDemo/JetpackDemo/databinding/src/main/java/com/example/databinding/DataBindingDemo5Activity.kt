package com.example.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databinding.adapter.RecyclerViewAdapter
import com.example.databinding.databinding.ActivityDataBinding5DemoBinding
import com.example.databinding.model.MyUserViewModel
import com.example.databinding.utils.IdolUtils

class DataBindingDemo5Activity: AppCompatActivity() {

    private lateinit var binding: ActivityDataBinding5DemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding5DemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = RecyclerViewAdapter(IdolUtils.get())

        binding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }



    }
}