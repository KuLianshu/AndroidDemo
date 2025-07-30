package com.example.recyclerview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.ActivityRecyclerViewPayloadsUseBinding
import com.example.recyclerview.databinding.ItemDiffBinding
import com.example.recyclerview.entity.TestBean
import com.example.recyclerview.utils.randomColor


/**
 * 使用 payloads 进行局部刷新数据
 *
 */
class RecyclerViewPayloadsUseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerViewPayloadsUseBinding
    private lateinit var mRv: RecyclerView
    private lateinit var mAdapter: DiffAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_recycler_view_payloads_use)

        iniView()
        iniEvent()
    }

    private fun iniEvent() {

        binding.btnChangeName.setOnClickListener {
            mAdapter.updateNameAt(0,"程序员0")
        }

        binding.btnChangeDesc.setOnClickListener {
            mAdapter.updateDescAt(0,"手撕人事")
        }

        binding.btnChangeId.setOnClickListener {
            mAdapter.updateIdAt(0,Color.BLUE)
        }

    }


    private fun iniView() {
        val data = mutableListOf<TestBean>()
        data.add(TestBean("程序员1", "Android",getColorId()))
        data.add(TestBean("程序员2", "Java",getColorId()))
        data.add(TestBean("程序员3", "背锅",getColorId()))
        data.add(TestBean("程序员4", "手撕产品",getColorId()))
        data.add(TestBean("程序员5", "手撕测试",getColorId()))
        mAdapter = DiffAdapter(data)
        mRv = binding.rv
        mRv.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = mAdapter
        }
    }


    private fun getColorId(): Int = Color.rgb(
        randomColor(),
        randomColor(),
        randomColor()
    )



    class DiffAdapter(
        private var mData: MutableList<TestBean>)
        : RecyclerView.Adapter<DiffAdapter.DiffVH>() {
        inner class DiffVH(val binding: ItemDiffBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(bean: TestBean){
                binding.tv1.text = bean.name
                binding.tv2.text = bean.desc
                binding.root.setBackgroundColor(bean.colorId)
//                setBackgroundColor(binding)
            }
        }

        fun setBackgroundColor(binding: ItemDiffBinding){
            binding.root.setBackgroundColor(
                Color.rgb(
                    randomColor(),
                    randomColor(),
                    randomColor()
                )
            )
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffVH {
            return DiffVH(
                ItemDiffBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent, false)
            )
        }

        override fun onBindViewHolder(holder: DiffVH, position: Int) {
            holder.bind(mData[position])
        }


        override fun onBindViewHolder(holder: DiffVH, position: Int, payloads: MutableList<Any>) {
            if (payloads.isEmpty()){
                //当payloads为空时，进行全量绑定
                super.onBindViewHolder(holder, position, payloads)
            }else{
                //使用payloads刷新局部内容
                val payload = payloads[0] as Bundle
                val bean = mData[position]
                for (key in payload.keySet()){
                    when(key){
                        "KEY_NAME" ->{
                            bean.name = payload.getString("KEY_NAME","")
                            holder.binding.tv1.text = bean.name
                        }
                        "KEY_DESC" -> {
                            bean.desc = payload.getString("KEY_DESC","")
                            holder.binding.tv2.text = bean.desc
                        }
                        "KEY_ID" -> {
                            bean.colorId = payload.getInt("KEY_ID",Color.RED)
                            holder.binding.root.setBackgroundColor(bean.colorId)
                        }
                    }
                }
            }

        }

        override fun getItemCount(): Int {
            return mData.size
        }

        //通过payloads更新页面
        fun updateNameAt(position: Int, name: String){
            val payload = Bundle().apply { putString("KEY_NAME", name) }
            notifyItemChanged(position, payload)
        }

        //通过payloads更新页面
        fun updateDescAt(position: Int, desc: String) {
            val payload = Bundle().apply { putString("KEY_DESC", desc) }
            notifyItemChanged(position, payload)
        }

        //通过payloads更新页面
        fun updateIdAt(position: Int, id: Int) {
            val payload = Bundle().apply { putInt("KEY_ID", id) }
            notifyItemChanged(position, payload)
        }
    }

}