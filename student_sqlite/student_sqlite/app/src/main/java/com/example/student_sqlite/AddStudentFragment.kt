package com.example.student_sqlite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class AddStudentFragment : Fragment() {

    private lateinit var dbHelper: DBHelper
    private lateinit var editTextName: EditText
    private lateinit var editTextMSSV: EditText
    private lateinit var buttonAdd: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_student, container, false)
        dbHelper = DBHelper(requireContext())
        editTextName = view.findViewById(R.id.editTextName)
        editTextMSSV = view.findViewById(R.id.editTextMSSV)
        buttonAdd = view.findViewById(R.id.buttonAdd)

        buttonAdd.setOnClickListener {
            addStudent()
        }

        return view
    }

    private fun addStudent() {
        val name = editTextName.text.toString()
        val mssv = editTextMSSV.text.toString()

        if (name.isNotEmpty() && mssv.isNotEmpty()) {
            val student = Student(name = name, mssv = mssv)
            dbHelper.addStudent(student)
            Toast.makeText(requireContext(), "Student added", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressed() // Quay lại fragment danh sách sinh viên
        } else {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}