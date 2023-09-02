package com.example.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.databinding.ActivityRoomDemoBinding
import com.example.room.entity.Student
import com.example.room.student.StudentRecyclerViewAdapter
import com.example.room.viewmodel.MyNewViewModel

class RoomDemoActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRoomDemoBinding
    private lateinit var adapter : StudentRecyclerViewAdapter
    private lateinit var viewModel: MyNewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        initData()

        initEvent()
    }

    private fun initView() {
        adapter = StudentRecyclerViewAdapter()
        binding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }

    }

    private fun initData() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MyNewViewModel::class.java]

        viewModel.getAllStudentsLiveData().observe(this){
            viewModel.students.value = it
            adapter.submitList(it)
        }
    }

    private fun initEvent() {
        binding.btnInsert.setOnClickListener {
            insert()
        }
        binding.btnClear.setOnClickListener {
            clear()
        }

        binding.btnUpdate.setOnClickListener {
            update()
        }

        binding.btnDelete.setOnClickListener {
            delete()
        }

    }

    private fun insert() {
        val s1 = Student("Jack",20)
        val s2 = Student("Rose",21)
        viewModel.insertStudent(s1,s2)
    }


    private fun delete() {
        viewModel.students.value?.let {
            if (it.isNotEmpty()){
                viewModel.deleteStudent(it.last())
            }
        }

    }

    private fun update() {
        viewModel.students.value?.let {
            if (it.isNotEmpty()) {
                val index = (20..40).random()
                //Student 必须是新的对象，否则RecyclerView无变化
                val s = Student("list $index 号", index)
                s.id = it.first().id
                viewModel.updateStudent(s)

            }
        }
    }

    private fun clear(){
        viewModel.deleteAllStudents()

    }

}