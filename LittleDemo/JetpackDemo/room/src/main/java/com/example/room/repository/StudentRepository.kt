package com.example.room.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.room.db.MyDatabase
import com.example.room.db.StudentDao
import com.example.room.entity.Student

class StudentRepository(context: Context) {

    private var studentDao: StudentDao
    init {
        val database = MyDatabase.getInstance(context)
        studentDao = database.getStudentDao()
    }

    fun insertStudent(vararg students: Student){
        insert(*students)
    }

    fun updateStudent(vararg students: Student){
        update(*students)
    }

    fun deleteStudent(vararg students: Student){
        delete(*students)
    }

    fun deleteAllStudents(){
        deleteAll()
    }

    fun getAllStudentsLiveData(): LiveData<List<Student>> {
        return studentDao.getAllStudentsLiveData()
    }


    private fun deleteAll() {
        Thread{
            studentDao.deleteAllStudents()
        }.start()

    }

    private fun delete(vararg students: Student) {
        Thread{
            studentDao.deleteStudent(*students)
        }.start()

    }

    private fun update(vararg students: Student) {
        Thread {
            studentDao.updateStudent(*students)
        }.start()

    }


    private fun insert(vararg students: Student) {
        Thread{
            studentDao.insertStudent(*students)
        }.start()
    }

}