package com.example.recyclerview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.databinding.ItemDiffBinding
import com.example.recyclerview.entity.TestBean
import com.example.recyclerview.utils.randomColor


/**
 * 使用 DiffUtil + payloads 进行局部刷新数据
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mRv: RecyclerView
    private lateinit var mAdapter: DiffAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        iniView()
        iniEvent()
    }

    private fun iniEvent() {
        binding.btnRefresh.setOnClickListener {
            val oldData = mAdapter.getData()
            val newData = mAdapter.getData().shuffled().toMutableList()
            val diffResult = DiffUtil.calculateDiff(DiffCallBack(oldData,newData))
            mAdapter.setData(newData)
            diffResult.dispatchUpdatesTo(mAdapter)
        }

        binding.btnChangeData.setOnClickListener {
            val thread = Thread{
                //先获取list
                val newList = mAdapter.getData()
                //建一个新的list去装旧值
                val oldList = newList.toMutableList()
                val colorId = getColorId()
                //获取旧的item
                val oldItem = oldList[0]
                //新建一个item
                val newItem = TestBean(oldItem.name,"显示个颜色值:$colorId",colorId)
                //更新list。不能直接在newList[0]改，引用传递会导致 calculateDiff 失效
                newList[0] = newItem
                val diffResult = DiffUtil.calculateDiff(DiffCallBack(oldList,newList))
                runOnUiThread {
                    mAdapter.setData(newList)
                    diffResult.dispatchUpdatesTo(mAdapter)
                }
            }
            thread.start()
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

    /**
     * 使用 DiffUtil.Callback 实现对比新旧数据集。
     */
        class DiffCallBack(
        private val mOldData: List<TestBean>,
        private val mNewData: List<TestBean>)
        : DiffUtil.Callback(){
        override fun getOldListSize() = mOldData.size
        override fun getNewListSize() = mNewData.size

        /**
         * 该方法用来判断两个对象是否是相同的Item
         * 例如，如果你的Item有唯一的name字段，这个方法就判断name是否相等。
         * 如果这里返回true，会调用方法areContentsTheSame()
         */
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return mOldData[oldItemPosition].name == mNewData[newItemPosition].name
        }

        /**
         * 该方法用来检查 两个item是否含有相同的数据
         * 这个方法仅在areItemsTheSame()返回true时调用。
         *
         * 注意：
         * 如果areContentsTheSame()返回false，Adapter中onBindViewHolder()将会被调用，
         * 所以bind()方法被调用，setBackgroundColor()会被调用，item背景颜色值被重新计算，所以item背景颜色改变；
         * 如果返回true，那么界面不会改变
         */
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            //Kotlin == 比较的是类对象的内容，而不是地址
            return mOldData[oldItemPosition] == mNewData[newItemPosition]
        }

            //重写它，定向改变某个item时，界面上就不闪白光了
        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                val oldBean = mOldData[oldItemPosition]
                val newBean = mNewData[newItemPosition]
                val payload = Bundle()

                if (oldBean.name != newBean.name ){
                    payload.putString("KEY_NAME",newBean.name)
                }
                if (oldBean.desc != newBean.desc ){
                    payload.putString("KEY_DESC",newBean.desc)
                }
                if (oldBean.colorId != newBean.colorId){
                    payload.putInt("KEY_ID",newBean.colorId)
                }
                if (payload.size() == 0){
                    return null
                }
                return payload
        }
    }


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

        fun setData(data: MutableList<TestBean>){
            mData = data
        }

        fun getData() = mData

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


    }

}