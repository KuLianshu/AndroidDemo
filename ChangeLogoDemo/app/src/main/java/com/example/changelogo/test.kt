package com.example.changelogo

import android.util.Log

const val KtBaseMapFunTest01_TAG = "KtBaseMapFunTest01"

class KtBaseMapFunTest01 {
    fun testMapFun01(){
        val list = listOf("zhangsan", "lisi", "wangwu")

        /**
         * 集合(T,T,T)---》map转换之后变成新的集合(R,R,R)
         * 原理：就是把匿名函数的最后一行作为返回值，加入新的集合，新集合的泛型类型是R,并且返回新集合。
         * map里持有的it等价于list里的元素
         */
        val list2 = list.map {
            if (it=="lisi"){
                "88$it"
            }else{
                it
            }
        }
        println(list2)

        val list3: List<String> = list.map {
            "姓名是：$it"
        }
            .map {
            "[$it]"
        }.map {
            "元素：$it"
        }
        list3.forEach {
            println(it)
        }
    }

    fun testFlatMapFun01(){
        val list = listOf("zhangsan", "lisi", "wangwu")
        val list2: List<String> = list.flatMap {
//            println(it)
            listOf("$it 学习Kotlin", "$it 学习java")
        }
        println(list2)
    }

    fun testFilterFun01(){
        val nameList:List<List<String>> =  listOf(
            listOf("zhangsan", "lisi", "wangwu"),
            listOf("zhanghong", "maqi", "liumang"),
            listOf("zhangqiang", "lixiaolong", "wangbadan")
        )

        nameList.map {
//            println(it)
        }

//        println("=================================")

        nameList.flatMap {
//            println(it)
            listOf("")
        }

//        println("==================================")

//        nameList.flatMap {
//            it -> it.filter {
//            println(it)
//            it.contains("li")
//         }
//        }.map {
//            println("-----------------")
//            println(it)
//        }

        val numbers1 = listOf(213, 4, 534, 646, 757, 8, 97, 9)
        val numbers2 = listOf(214, 5, 535, 647, 758, 9, 98, 10)
        val numbers3 = listOf(numbers1, numbers2)

        println(numbers3)

        val res = numbers3.flatMap {
            it
        }.filter {
            it<10
        }
        println(res)

        val res1 = numbers1.filter {
            it>10
        }

        println(res1)




    }


}


fun main(args: Array<String>){
//    KtBaseMapFunTest01().testMapFun01()
//    KtBaseMapFunTest01().testFlatMapFun01()
    KtBaseMapFunTest01().testFilterFun01()

}


