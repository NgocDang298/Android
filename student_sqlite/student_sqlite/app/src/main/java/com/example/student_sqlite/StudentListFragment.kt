package com.example.student_sqlite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class StudentListFragment : Fragment() {

    private lateinit var dbHelper: DBHelper
    private lateinit var listView: ListView
    private lateinit var studentAdapter: ArrayAdapter<Student>
    private var studentList: MutableList<Student> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Để hiển thị OptionMenu
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)
        listView = view.findViewById(R.id.listView)
        dbHelper = DBHelper(requireContext())

        loadStudents()

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedStudent = studentList[position ]
            // Mở fragment để cập nhật thông tin sinh viên
            val action = StudentListFragmentDirections.actionStudentListFragmentToEditStudentFragment(selectedStudent.id)
            findNavController().navigate(action)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val selectedStudent = studentList[position]
            // Hiển thị context menu với các tùy chọn
            showContextMenu(selectedStudent)
            true
        }

        return view
    }

    private fun loadStudents() {
        studentList.clear()
        studentList.addAll(dbHelper.getAllStudents())
        studentAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, studentList)
        listView.adapter = studentAdapter
    }

    private fun showContextMenu(student: Student) {
        // Hiển thị dialog hoặc menu để chọn Edit hoặc Remove
        // Thực hiện hành động tương ứng khi người dùng chọn
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_new -> {
                // Mở fragment để thêm sinh viên mới
                findNavController().navigate(R.id.action_studentListFragment_to_addStudentFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}