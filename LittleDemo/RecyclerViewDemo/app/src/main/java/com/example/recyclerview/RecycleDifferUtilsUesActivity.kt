package com.example.recyclerview

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.recyclerview.databinding.ActivityRecyclerDifferUtilsUseBinding
import com.example.recyclerview.databinding.ItemTextBinding
import com.example.recyclerview.entity.Name
import com.example.recyclerview.utils.randomColor


/**
 * 使用 DiffUtil.Callback 进行局部刷新数据
 * 注意，ListAdapter中用到DiffUtil.ItemCallback，
 * 和 DiffUtil.Callback是不一样的类
 * 别粗心搞错了
 */
class RecycleDifferUtilsUesActivity : AppCompatActivity() {

    private var data = mutableListOf(
        Name("数据1"),
        Name("数据2"),
        Name("数据3"),
        Name("数据4"),
        Name("数据5"),
        Name("数据6"),
        Name("数据7")
    )

    private var adapter: TAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerDifferUtilsUseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycle.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = TAdapter(data){name->
                Toast.makeText(this@RecycleDifferUtilsUesActivity,name.name,Toast.LENGTH_SHORT).show()
            }.apply {
                adapter = this
            }
        }


        binding.btnShuffle.setOnClickListener {
            adapter?.let {
                it.submit { old,new->
                    //洗牌
                    new.shuffle()
                    val diffResult = DiffUtil.calculateDiff(DifferCallback(old,new))
                    diffResult.dispatchUpdatesTo(it)
                }
            }
        }

        binding.btnChangeData.setOnClickListener {
            adapter?.let {
                it.submit { old, new ->
                    //每次更换相邻的两组数据
                    val r = (0 until new.size)
                    val index = r.random()
                    val buffer = new[index]
                    if (index+1==new.size){
                        new[index] = new[0]
                        new[0] = buffer
                    }else{
                        new[index] = new[index+1]
                        new[index+1] = buffer
                    }
                    val diffResult = DiffUtil.calculateDiff(DifferCallback(old, new))
                    diffResult.dispatchUpdatesTo(it)
                }
            }
        }
    }

    class TAdapter(var adapterdata: MutableList<Name>) :
        RecyclerView.Adapter<TAdapter.RecycleDemoHolder>() {
        private lateinit var onItemClickListener: (Name)->Unit
        constructor(adapterData: MutableList<Name>,
                    onItemClickListener: (Name)->Unit):this(adapterData){
                        this.onItemClickListener = onItemClickListener
                    }

        fun submit(callback: (MutableList<Name>, MutableList<Name>) -> Unit) {
            val newData = adapterdata.toMutableList()
            callback(adapterdata, newData)
            //这里一定要更改adapter中的数据列表，否则内容是不会变的
            adapterdata = newData
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleDemoHolder {
            return RecycleDemoHolder(
                ItemTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: RecycleDemoHolder, position: Int) {
            holder.bind(position, adapterdata[position], onItemClickListener)
        }

        override fun getItemCount(): Int {
            return adapterdata.size
        }

        class RecycleDemoHolder(private val binding: ItemTextBinding)
            : ViewHolder(binding.root) {
            fun bind(position: Int, data: Name, onItemClickListener: (Name)->Unit) {
                val content = "$position  ${data.name}"
                binding.tv.text = content
                binding.root.setBackgroundColor(Color.rgb(randomColor(), randomColor(), randomColor()))
                binding.root.setOnClickListener {
                    onItemClickListener(data)
                }
            }
        }

    }

    class DifferCallback(private val oldData: List<Name>, private val newData: List<Name>)
        : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldData.size

        override fun getNewListSize(): Int = newData.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].name == newData[newItemPosition].name
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition] == newData[newItemPosition]
        }

    }
}






