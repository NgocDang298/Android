package com.example.student_sqlite

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView


import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataBaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DataBaseHelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add sample students
        dbHelper.addStudent("Nguyen Van A", "123456")
        dbHelper.addStudent("Tran Thi B", "654321")

        // Retrieve students and set adapter
        val students = dbHelper.getAllStudents()
        studentAdapter = StudentAdapter(students)
        recyclerView.adapter = studentAdapter
    }
}