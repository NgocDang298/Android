package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var display: TextView
    private var currentNumber = ""
    private var firstNumber = ""
    private var operation: String? = null
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val buttons = listOf(
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
            R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7,
            R.id.button_8, R.id.button_9
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { numberClick(it as Button) }
        }

        findViewById<Button>(R.id.button_add).setOnClickListener { operationClick("+") }
        findViewById<Button>(R.id.button_subtract).setOnClickListener { operationClick("-") }
        findViewById<Button>(R.id.button_multiply).setOnClickListener { operationClick("*") }
        findViewById<Button>(R.id.button_divide).setOnClickListener { operationClick("/") }
        findViewById<Button>(R.id.button_equals).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.button_c).setOnClickListener { clear() }
        findViewById<Button>(R.id.button_ce).setOnClickListener { clearEntry() }
        findViewById<Button>(R.id.button_bs).setOnClickListener { backspace() }
        findViewById<Button>(R.id.button_decimal).setOnClickListener { addDecimal() }
        findViewById<Button>(R.id.button_plus_minus).setOnClickListener { toggleSign() }
    }

    private fun numberClick(button: Button) {
        if (isNewOperation) {
            currentNumber = ""
            isNewOperation = false
        }
        currentNumber += button.text
        display.text = currentNumber
    }

    private fun operationClick(op: String) {
        if (currentNumber.isNotEmpty()) {
            firstNumber = currentNumber
            operation = op
            isNewOperation = true
        }
    }

    private fun calculateResult() {
        if (firstNumber.isNotEmpty() && currentNumber.isNotEmpty() && operation != null) {
            val num1 = firstNumber.toDouble()
            val num2 = currentNumber.toDouble()
            val result = when (operation) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> if (num2 != 0.0) num1 / num2 else "Error"
                else -> "Error"
            }
            display.text = result.toString()
            currentNumber = result.toString()
            firstNumber = ""
            operation = null
            isNewOperation = true
        }
    }

    private fun clear() {
        currentNumber = ""
        firstNumber = ""
        operation = null
        display.text = "0"
        isNewOperation = true
    }

    private fun clearEntry() {
        currentNumber = ""
        display.text = "0"
    }

    private fun backspace() {
        if (currentNumber.isNotEmpty()) {
            currentNumber = currentNumber.dropLast(1)
            display.text = if (currentNumber.isEmpty()) "0" else currentNumber
        }
    }

    private fun addDecimal() {
        if (!currentNumber.contains(".")) {
            currentNumber += "."
            display.text = currentNumber
        }
    }

    private fun toggleSign() {
        if (currentNumber.isNotEmpty() && currentNumber != "0") {
            currentNumber = if (currentNumber.startsWith("-")) {
                currentNumber.substring(1)
            } else {
                "-$currentNumber"
            }
            display.text = currentNumber
        }
    }
}
