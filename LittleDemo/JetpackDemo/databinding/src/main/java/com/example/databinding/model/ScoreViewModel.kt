package com.example.databinding.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel: ViewModel() {
    private var aTeamScore : MutableLiveData<Int>? = null
    private var bTeamScore : MutableLiveData<Int>? = null

//    private var aLast : Int ? = 0
//    private var bLast : Int ? = 0

    private var aLast =  mutableListOf<Int?>()
    private var bLast =  mutableListOf<Int?>()
    
    fun getaTeamScore(): MutableLiveData<Int> {
        if (aTeamScore == null) {
            aTeamScore = MutableLiveData()
            aTeamScore!!.value = 0
        }
        return aTeamScore!!
    }

    fun getbTeamScore(): MutableLiveData<Int> {
        if (bTeamScore == null) {
            bTeamScore = MutableLiveData()
            bTeamScore!!.value = 0
        }
        return bTeamScore!!
    }

    fun aTeamAdd(i: Int){
        saveLastScore()
        aTeamScore!!.value = aTeamScore!!.value?.plus(i)
    }


    fun bTeamAdd(i: Int){
        saveLastScore()
        bTeamScore!!.value = bTeamScore!!.value?.plus(i)
    }

    fun undo(){

        if (aLast.size>1){
            aTeamScore!!.value = aLast[aLast.size-1]
            aLast.remove(aTeamScore!!.value )
        }else{
            aTeamScore!!.value = 0
            aLast.clear()
        }
        if (bLast.size>1){
            bTeamScore!!.value = bLast[bLast.size-1]
            bLast.remove(bTeamScore!!.value)
        }else{
            bTeamScore!!.value = 0
            bLast.clear()
        }


        Log.i("WLY","aLast:$aLast, bLast:$bLast")
    }

    fun reset(){
        aTeamScore!!.value =0
        bTeamScore!!.value = 0
        aLast.clear()
        bLast.clear()
    }

    fun saveLastScore(){
        aTeamScore?.let {
            aLast.add(aTeamScore!!.value)
        }

        bTeamScore?.let {
            bLast.add(bTeamScore!!.value)

        }
        Log.i("WLY","1 aLast:$aLast, bLast:$bLast")

    }



}

