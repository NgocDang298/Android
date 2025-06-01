package com.example.student_sqlite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class EditStudentFragment : Fragment() {

    private lateinit var dbHelper: DataBaseHelper
    private lateinit var editTextName: EditText
    private lateinit var editTextMSSV: EditText
    private lateinit var buttonUpdate: Button
    private var studentId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        studentId = EditStudentFragmentArgs.fromBundle(requireArguments()).studentId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_student, container, false)
        dbHelper = DataBaseHelper(requireContext())
        editTextName = view.findViewById(R.id.editTextName)
        editTextMSSV = view.findViewById(R.id.editTextMSSV)
        buttonUpdate = view.findViewById(R.id.buttonUpdate)

        loadStudentData()

        buttonUpdate.setOnClickListener {
            updateStudent()
        }

        return view
    }
    private fun loadStudentData() {
        val student = dbHelper.getAllStudents().find { it.id == studentId }
        student?.let {
            editTextName.setText(it.name)
            editTextMSSV.setText(it.mssv)
        }
    }

    private fun updateStudent() {
        val name = editTextName.text.toString()
        val mssv = editTextMSSV.text.toString()

        if (name.isNotEmpty() && mssv.isNotEmpty()) {
            val student = Student(id = studentId, name = name, mssv = mssv)
            dbHelper.updateStudent(student)
            Toast.makeText(requireContext(), "Student updated", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressed() // Quay lại fragment danh sách sinh viên
        } else {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
