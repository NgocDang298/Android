package com.example.doitien

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txt1: EditText = findViewById(R.id.txt1)
        val txt2: EditText = findViewById(R.id.txt2)
        val spinner1: Spinner = findViewById(R.id.spiner1)
        val spinner2: Spinner = findViewById(R.id.spiner2)

        val currencies = arrayOf("USD", "EUR", "VND", "AUD","CNY")
        val exchangeRates = mapOf(
            "USD" to 1.0,
            "EUR" to 0.85,
            "VND" to 23000.0,
            "AUD" to 1.5,
            "CNY" to 7,16
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter
        spinner2.adapter = adapter

        txt1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                convertCurrency(txt1, txt2, spinner1, spinner2, exchangeRates)
            }
        })
    }

    private fun convertCurrency(
        txt1: EditText,
        txt2: EditText,
        spinner1: Spinner,
        spinner2: Spinner,
        exchangeRates: Map<String, Double>
    ) {
        val amount = txt1.text.toString().toDoubleOrNull() ?: 0.0
        val fromCurrency = spinner1.selectedItem.toString()
        val toCurrency = spinner2.selectedItem.toString()

        val result = if (fromCurrency in exchangeRates && toCurrency in exchangeRates) {
            val rate = exchangeRates[toCurrency]!! / exchangeRates[fromCurrency]!!
            amount * rate
        } else 0.0

        txt2.setText(String.format("%.2f", result))
    }
}
