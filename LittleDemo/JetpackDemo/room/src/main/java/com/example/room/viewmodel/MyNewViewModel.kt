package com.example.room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.room.entity.Student
import com.example.room.repository.StudentRepository

class MyNewViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: StudentRepository
    var students : MutableLiveData<List<Student>>

    init {
        repository = StudentRepository(application)
        students = MutableLiveData<List<Student>>()

    }


    fun insertStudent(vararg students: Student){
        repository.insertStudent(*students)
    }

    fun updateStudent(vararg students: Student){
        repository.updateStudent(*students)
    }

    fun deleteStudent(vararg students: Student){
        repository.deleteStudent(*students)
    }

    fun deleteAllStudents(){
        repository.deleteAllStudents()
    }

    fun getAllStudentsLiveData(): LiveData<List<Student>> {
        return repository.getAllStudentsLiveData()
    }

}