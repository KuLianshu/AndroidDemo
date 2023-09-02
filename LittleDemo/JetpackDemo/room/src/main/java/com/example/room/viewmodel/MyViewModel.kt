package com.example.room.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.room.entity.Student

class MyViewModel: ViewModel() {

    private  var students : MutableLiveData<List<Student>> ? = null

    fun  getStudents(): MutableLiveData<List<Student>>?{
        if (students == null){
            students = MutableLiveData<List<Student>>()
        }
        return students
    }



}