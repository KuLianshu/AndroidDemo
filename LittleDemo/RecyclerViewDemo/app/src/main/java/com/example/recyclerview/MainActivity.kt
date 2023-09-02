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

class MainActivity : AppCompatActivity() {

    private lateinit var mDatas: MutableList<TestBean>
    private lateinit var binding: ActivityMainBinding
    private lateinit var mRv: RecyclerView
    private lateinit var mAdapter: DiffAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        iniView()
        iniEvent()
    }

    private fun iniEvent() {
        binding.btnRefresh.setOnClickListener {

       val newDatas = mDatas.toMutableList().apply {
                shuffle()
            }

            //这里新数据都被打乱了，areItemsTheSame返回都是false，不会调用到areContentsTheSame
            val diffResult = DiffUtil.calculateDiff(DiffCallBack(mDatas,newDatas))
            diffResult.dispatchUpdatesTo(mAdapter)
            mDatas = newDatas
            mAdapter.setDatas(mDatas)

        }

        binding.btnChangeData.setOnClickListener {
//            changeData()
            changeDataInChildThread()
        }

    }

    //在子线程修改数据
    private fun changeDataInChildThread() {
        val thread = Thread{
            val newDatas = mDatas.toMutableList()
            newDatas[0] = TestBean("程序员1","显示个颜色值:${getColorId()}",getColorId())
            val diffResult = DiffUtil.calculateDiff(DiffCallBack(mDatas,newDatas))
            runOnUiThread {
                diffResult.dispatchUpdatesTo(mAdapter)
                mDatas = newDatas
                mAdapter.setDatas(mDatas)
            }

        }
        thread.start()

    }


    fun changeData1(){
        //            //这里新老数据地址一样
//            val newDatas = mDatas.toMutableList()
//            //因为地址不变，这里也会改到旧数据的newDatas[0].desc，
//            //areItemsTheSame和areContentsTheSame返回都是true，
//            //所以界面不改变
//            newDatas[0].desc = "Android+"

//            //这里新老数据地址不一样
//            val newDatas = mutableListOf<TestBean>()
//            for (data in mDatas){
//                newDatas.add(TestBean(data.name,data.desc,getColorId()))
//            }
//            //两组list对应的第一个数据地址不一样
//            //所以不会改到旧数据的desc
//            //areItemsTheSame返回true时，系统调用areContentsTheSame
//            //areContentsTheSame返回false，所以adapter中onBindViewHolder会被调用
//            //setBackground()Color被调用，item背景值会被重新计算，所以背景颜色改变
//            newDatas[0].desc = "Android+"

        val newDatas = mDatas.toMutableList()
        newDatas[0] = TestBean("程序员1","显示个颜色值:${getColorId()}",getColorId())
        val diffResult = DiffUtil.calculateDiff(DiffCallBack(mDatas,newDatas))
        diffResult.dispatchUpdatesTo(mAdapter)
        mDatas = newDatas
        mAdapter.setDatas(mDatas)
    }


    private fun iniView() {
        mAdapter = DiffAdapter(mDatas)
        mRv = binding.rv
        mRv.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = mAdapter
        }
    }

    private fun initData() {
        mDatas = mutableListOf()
        mDatas.add(TestBean("程序员1", "Android",getColorId()))
        mDatas.add(TestBean("程序员2", "Java",getColorId()))
        mDatas.add(TestBean("程序员3", "背锅",getColorId()))
        mDatas.add(TestBean("程序员4", "手撕产品",getColorId()))
        mDatas.add(TestBean("程序员5", "手撕测试",getColorId()))

    }

    private fun getColorId(): Int = Color.rgb(
        randomColor(),
        randomColor(),
        randomColor()
    )

        class DiffCallBack(
        private val mOldDatas: MutableList<TestBean>,
        private val mNewDatas: MutableList<TestBean>)
        : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return mOldDatas.size
        }

        override fun getNewListSize(): Int {
            return mNewDatas.size
        }

        /**
         * 该方法用来判断两个对象是否是相同的Item
         * 例如，如果你的Item有唯一的id字段，这个方法就判断id是否相等。
         * 本例判断name字段是否一致
         * 如果这里返回true，会调用方法areContentsTheSame()
         */
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

            return mOldDatas[oldItemPosition].name == mNewDatas[newItemPosition].name
        }

        /**
         * 该方法用来检查 两个item是否含有相同的数据
         * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
         *
         * 注意：
         * 如果areContentsTheSame()返回false，Adapter中onBindViewHolder()将会被调用，
         * 所以bind()方法被调用，setBackgroundColor()会被调用，item背景颜色值被重新计算，所以item背景颜色改变；
         * 如果返回true，那么界面不会改变
         */
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

            return mOldDatas[oldItemPosition] == mNewDatas[newItemPosition]
        }

            //重写它，定向改变某个item时，界面上就不闪白光了
        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldBean = mOldDatas[oldItemPosition]
            val newBean = mNewDatas[newItemPosition]
            val payload = Bundle()
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
        var mDatas: MutableList<TestBean>)
        : RecyclerView.Adapter<DiffAdapter.DiffVH>() {

        inner class DiffVH(val binding: ItemDiffBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(bean: TestBean){
                Log.i("WLY","----------BIND")
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

        fun setDatas(datas: MutableList<TestBean>){
            mDatas = datas
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
            holder.bind(mDatas[position])
//            Log.i("WLY","----------onBindViewHolder")
        }

        override fun onBindViewHolder(holder: DiffVH, position: Int, payloads: MutableList<Any>) {
            super.onBindViewHolder(holder, position, payloads)
            if (payloads.isEmpty()){
                onBindViewHolder(holder,position)
            }else{
                val payload = payloads[0] as Bundle
                val bean = mDatas[position]
                for (key in payload.keySet()){
                    when(key){
                        "KEY_DESC" -> holder.binding.tv2.text = bean.desc
                        "KEY_ID" -> holder.binding.root.setBackgroundColor(bean.colorId)

                    }
                }
            }

        }

        override fun getItemCount(): Int {
            return mDatas.size
        }

    }

}