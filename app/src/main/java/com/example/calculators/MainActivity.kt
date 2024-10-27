package com.example.calculators

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Display result
    lateinit var textResult: TextView

    // State and operator variables
    var state: Int = 1
    var op: Int = 0
    var op1: Int = 0
    var op2: Int = 0

    // Currency conversion variables
    private lateinit var etSourceAmount: EditText
    private lateinit var etDestinationAmount: EditText
    private lateinit var spnSourceCurrency: Spinner
    private lateinit var spnDestinationCurrency: Spinner

   private val exchangeRates = mapOf(
    Pair("USD", "EUR") to 0.85,
    Pair("EUR", "USD") to 1.18,
    Pair("USD", "VND") to 23000.0,
    Pair("VND", "USD") to 0.000043,
    Pair("USD", "JPY") to 110.0,
    Pair("JPY", "USD") to 0.0091,
    Pair("USD", "CNY") to 6.45,
    Pair("CNY", "USD") to 0.16,
    Pair("USD", "KRW") to 1180.0,
    Pair("KRW", "USD") to 0.00085
)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize result display
        textResult = findViewById(R.id.text_result)

        // Set up button click listeners
        findViewById<Button>(R.id.btn0).setOnClickListener(this)
        findViewById<Button>(R.id.btn1).setOnClickListener(this)
        findViewById<Button>(R.id.btn2).setOnClickListener(this)
        findViewById<Button>(R.id.btn3).setOnClickListener(this)
        findViewById<Button>(R.id.btn4).setOnClickListener(this)
        findViewById<Button>(R.id.btn5).setOnClickListener(this)
        findViewById<Button>(R.id.btn6).setOnClickListener(this)
        findViewById<Button>(R.id.btn7).setOnClickListener(this)
        findViewById<Button>(R.id.btn8).setOnClickListener(this)
        findViewById<Button>(R.id.btn9).setOnClickListener(this)
        findViewById<Button>(R.id.btnAdd).setOnClickListener(this)
        findViewById<Button>(R.id.btnSub).setOnClickListener(this)
        findViewById<Button>(R.id.btnMul).setOnClickListener(this)
        findViewById<Button>(R.id.btnDiv).setOnClickListener(this)
        findViewById<Button>(R.id.btnEqual).setOnClickListener(this)
        findViewById<Button>(R.id.btnC).setOnClickListener(this)
        findViewById<Button>(R.id.btnCE).setOnClickListener(this)
        findViewById<Button>(R.id.btnBS).setOnClickListener(this)
        findViewById<Button>(R.id.btnDot).setOnClickListener(this)
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener(this)

        // Initialize currency conversion UI elements
        etSourceAmount = findViewById(R.id.et_sourceAmount)
        etDestinationAmount = findViewById(R.id.et_destinationAmount)
        spnSourceCurrency = findViewById(R.id.spn_sourceCurrency)
        spnDestinationCurrency = findViewById(R.id.spn_destinationCurrency)

        val currencies = arrayOf("USD", "EUR", "VND", "JPY", "CNY", "KRW")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnSourceCurrency.adapter = adapter
        spnDestinationCurrency.adapter = adapter

        // TextWatcher for source amount
        etSourceAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = convertCurrency()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        spnSourceCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spnDestinationCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun onClick(p0: View?) {
        val id = p0?.id

        when (id) {
            R.id.btn0 -> addDigit(0)
            R.id.btn1 -> addDigit(1)
            R.id.btn2 -> addDigit(2)
            R.id.btn3 -> addDigit(3)
            R.id.btn4 -> addDigit(4)
            R.id.btn5 -> addDigit(5)
            R.id.btn6 -> addDigit(6)
            R.id.btn7 -> addDigit(7)
            R.id.btn8 -> addDigit(8)
            R.id.btn9 -> addDigit(9)
            R.id.btnAdd -> setOperation(1)  // Addition
            R.id.btnSub -> setOperation(2)  // Subtraction
            R.id.btnMul -> setOperation(3)  // Multiplication
            R.id.btnDiv -> setOperation(4)  // Division
            R.id.btnEqual -> calculateResult()
            R.id.btnC -> clearAll()
            R.id.btnCE -> clearEntry()
            R.id.btnBS -> backspace()
            R.id.btnPlusMinus -> toggleSign()
        }
    }

    // Handle digit input
    private fun addDigit(digit: Int) {
        if (state == 1) {
            op1 = op1 * 10 + digit
            textResult.text = "$op1"
        } else {
            op2 = op2 * 10 + digit
            textResult.text = "$op2"
        }
    }

    // Set the operation and switch state to 2
    private fun setOperation(operation: Int) {
        op = operation
        state = 2
    }

    // Perform the calculation based on the selected operation
    private fun calculateResult() {
        val result = when (op) {
            1 -> op1 + op2  // Addition
            2 -> op1 - op2  // Subtraction
            3 -> op1 * op2  // Multiplication
            4 -> if (op2 != 0) op1 / op2 else 0  // Division (avoid division by zero)
            else -> 0
        }
        textResult.text = "$result"
        resetCalculator()
    }

    // Clear all values and reset the calculator
    private fun clearAll() {
        op1 = 0
        op2 = 0
        op = 0
        state = 1
        textResult.text = "0"
    }

    // Clear the current entry (op1 or op2 depending on state)
    private fun clearEntry() {
        if (state == 1) {
            op1 = 0
            textResult.text = "0"
        } else {
            op2 = 0
            textResult.text = "0"
        }
    }

    // Remove the last entered digit
    private fun backspace() {
        if (state == 1) {
            op1 /= 10
            textResult.text = "$op1"
        } else {
            op2 /= 10
            textResult.text = "$op2"
        }
    }

    // Toggle the sign of the current number
    private fun toggleSign() {
        if (state == 1) {
            op1 = -op1
            textResult.text = "$op1"
        } else {
            op2 = -op2
            textResult.text = "$op2"
        }
    }

    // Reset calculator after result
    private fun resetCalculator() {
        state = 1
        op1 = 0
        op2 = 0
        op = 0
    }

    // Convert currency based on selected rates
    private fun convertCurrency() {
        val sourceAmount = etSourceAmount.text.toString().toDoubleOrNull() ?: return
        val sourceCurrency = spnSourceCurrency.selectedItem.toString()
        val destinationCurrency = spnDestinationCurrency.selectedItem.toString()

        val rate = exchangeRates[Pair(sourceCurrency, destinationCurrency)] ?: 1.0
        val convertedAmount = sourceAmount * rate
        etDestinationAmount.setText(convertedAmount.toString())
    }
}