package com.example.databinding.utils

import com.example.databinding.entity.NewIdol

object IdolUtils {

    fun get(): MutableList<NewIdol>{
        val idols = mutableListOf<NewIdol>()
        idols.add(NewIdol("猫咪1号","cat1","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw%2Fa39aa531-1b24-49f9-80e9-f4c3c681c867%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1692896083&t=de7cec5b75e14c2140bf89a74fe7bb30"))
        idols.add(NewIdol("猫咪2号","cat2","https://img1.baidu.com/it/u=2143686139,2979533173&fm=253&fmt=auto&app=138&f=JPEG?w=705&h=500"))
        idols.add(NewIdol("猫咪3号","cat3","https://img0.baidu.com/it/u=1397191483,3703430450&fm=253&fmt=auto&app=138&f=JPEG?w=755&h=500"))
        idols.add(NewIdol("猫咪4号","cat4","https://img2.baidu.com/it/u=2435393293,835340991&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500"))
        idols.add(NewIdol("猫咪5号","cat5","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2Fbca032b2-b92f-4061-8a13-bb9a5feb151f%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1692695812&t=a8e4202c1c0a12000ef5fb00ba2fcd34"))
        return idols
    }


}