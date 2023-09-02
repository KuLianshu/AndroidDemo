package com.example.recyclerview

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.ActivityRecyclerViewListAdapterBinding
import com.example.recyclerview.databinding.ItemTextBinding
import com.example.recyclerview.entity.Name
import com.example.recyclerview.utils.randomColor

/**
 * 使用 ListAdapter 进行局部刷新数据
 * 注意，ListAdapter中用到DiffUtil.ItemCallback，和DiffUtil.Callback是不一样的类
 * 别粗心搞错了
 */
class RecycleViewListAdapterActivity: AppCompatActivity() {

    var datas = mutableListOf(
        Name("数据1"),
        Name("数据2"),
        Name("数据3"),
        Name("数据4"),
        Name("数据5"),
        Name("数据6"),
        Name("数据7")
    )
//    companion object{
//        private val random = (100..200)
//        fun randomColor() = random.random()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewListAdapterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CustomListAdapter()
        binding.btnShuffle.setOnClickListener {
            val newData = datas.toMutableList().apply {
                //洗牌
                shuffle()
            }
            datas = newData
            adapter.submitList(newData)
        }

        binding.btnChangeData.setOnClickListener {
            val r = (0 until datas.size)
            val index = r.random()
            val newDataList = datas.toMutableList().apply {
                //每次更换相邻的两组数据
                val buffer = this[index]
                if (index+1==this.size){
                    this[index] = this[0]
                    this[0] = buffer
                }else{
                    this[index] = this[index+1]
                    this[index+1] = buffer
                }
            }
            newDataList[0] = Name("新数据")
            datas = newDataList
            adapter.submitList(datas)

        }

        binding.recycle.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }

        adapter.submitList(datas)

    }

    class CustomListAdapter: ListAdapter<Name, CustomListAdapter.CustomListHolder>(CustomDiffCallback) {
        companion object{
            private val CustomDiffCallback = object : DiffUtil.ItemCallback<Name>(){
                override fun areItemsTheSame(oldItem: Name, newItem: Name): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(oldItem: Name, newItem: Name): Boolean {
                    return oldItem == newItem
                }
            }
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomListHolder {
            val binding = ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CustomListHolder(binding)
        }

        override fun onBindViewHolder(holder: CustomListHolder, position: Int) {
            val content = "$position ${getItem(position).name}"
            holder.binding.tv.text = content
            holder.binding.root.setBackgroundColor(Color.rgb(randomColor(),randomColor(),randomColor()))
        }

        class CustomListHolder(val binding: ItemTextBinding): RecyclerView.ViewHolder(binding.root)
    }

}