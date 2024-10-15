package com.example.calculators

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Display result
    lateinit var textResult: TextView

    // State and operator variablesgit remote add origin git@github.com:dolinhchi/Mobile_BK.git
    var state: Int = 1
    var op: Int = 0
    var op1: Int = 0
    var op2: Int = 0

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
}
