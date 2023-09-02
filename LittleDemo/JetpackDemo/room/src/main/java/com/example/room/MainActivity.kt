package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.databinding.ActivityMainBinding
import com.example.room.db.MyDatabase
import com.example.room.db.StudentDao
import com.example.room.entity.Student
import com.example.room.student.StudentRecyclerViewAdapter
import com.example.room.viewmodel.MyViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : StudentRecyclerViewAdapter
    private lateinit var database: MyDatabase
    private lateinit var studentDao: StudentDao
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initEvent()
        initData()

    }

    private fun initEvent() {
        binding.btnInsert.setOnClickListener {
            insert()
        }

        binding.btnQuery.setOnClickListener {
            query()
        }

        binding.btnUpdate.setOnClickListener {
            update()
        }

        binding.btnDelete.setOnClickListener {
            delete()
        }

    }

    private fun delete() {
        viewModel.getStudents()?.value?.let {
            if (it.isNotEmpty()){
                Thread{
                    studentDao.deleteStudent(it.last())
                    query()
                }.start()
            }
        }

    }

    private fun update() {
        viewModel.getStudents()?.value?.let {
            if (it.size>0){
                val index = (20..40).random()
                //Student 必须是新的对象，否则RecyclerView无变化
                val s = Student("list $index 号", index)
                s.id = it.first().id
                Thread {
                    studentDao.updateStudent(s)
                    query()
                }.start()
            }
        }

    }

    private fun query(){
        Thread{
            val students = studentDao.getAllStudents()
            Log.i("WLY", students.toString())
            runOnUiThread {
                viewModel.getStudents()?.postValue(students)
            }
        }.start()
    }

    private fun insert() {
        Thread{
            val s1 = Student("Jack",20)
            val s2 = Student("Rose",21)
            studentDao.insertStudent(s1,s2)
            query()
        }.start()
    }

    private fun initData() {

        database = MyDatabase.getInstance(this)
        studentDao = database.getStudentDao()

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MyViewModel::class.java]

        viewModel.getStudents()?.observe(this){
            adapter.submitList(it)
        }
        query()
    }

    private fun initView() {
        adapter = StudentRecyclerViewAdapter()
        binding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }

    }
}