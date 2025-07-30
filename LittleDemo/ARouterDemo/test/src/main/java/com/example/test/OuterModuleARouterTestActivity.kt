package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.bean.User

@Route(path = com.example.common.utils.Constants.PATH_OUTER_MODULE)
class OuterModuleARouterTestActivity : AppCompatActivity() {

    //发送方定义的key，必须和这里定义的全局变量的名字保持一致，否则传值失败
    //不要定义String?，也就是不要定义空值，会导致传值失败
    //Kotlin中使用ARouter的话，必须有@JvmField，否则也会传值失败
    @JvmField
    @Autowired
    var name: String = ""

    //不要定义Long?，会导致传值失败
    @JvmField
    @Autowired
    var key: Long = 0L

    //这里也一样，不要定义User?
    @JvmField
    @Autowired
    var user: User = User("",0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.outer_module_activity_arouter_test)
        //如果要获取发送方传递过来的数据，这一步必不可少
        //给全局变量name、key、user直接注入发送方传递过来的值
        ARouter.getInstance().inject(this)

        val textView1 = findViewById<TextView>(R.id.textView)
        val textView2 = findViewById<TextView>(R.id.textView2)
        val textView3 = findViewById<TextView>(R.id.textView3)
        val button = findViewById<Button>(R.id.button)

        //数据会被直接注入，所以可以直接使用全局变量
        textView1.text = name
        textView2.text = "$key"
        textView3.text= user.let { "name:$name key:$key" }
        button.setOnClickListener {
            setResult(200, Intent().putExtra("id",300))
            finish()
        }

    }
}