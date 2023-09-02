package com.example.room.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.room.entity.Student

@Dao
interface StudentDao {

    @Insert
    fun insertStudent(vararg students: Student)

    @Delete
    fun deleteStudent(vararg students: Student)

    @Query("DELETE FROM student")
    fun deleteAllStudents()

    @Update
    fun updateStudent(vararg students: Student)

    @Query("SELECT * FROM student")
    fun getAllStudents(): List<Student>

    @Query("SELECT * FROM student")
    fun getAllStudentsLiveData(): LiveData<List<Student>>


    @Query("SELECT * FROM student WHERE id = :id")
    fun getStudentById(id: Int): List<Student>

}
