package com.klc.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var textViewCalculo: TextView
    private lateinit var textViewResultado: TextView
    private var isResultShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewCalculo = findViewById(R.id.calculo)
        textViewResultado = findViewById(R.id.resultado)

        configureButtonListeners()
    }

    private fun configureButtonListeners() {
        val buttons = arrayOf(
            R.id.zero, R.id.um, R.id.dois, R.id.tres, R.id.quatro,
            R.id.cinco, R.id.seis, R.id.sete, R.id.oito, R.id.nove,
            R.id.soma, R.id.subtracao, R.id.multiplicacao, R.id.divisao
        )

        for (buttonId in buttons) {
            findViewById<Button>(buttonId).setOnClickListener { v ->
                if (isResultShown) {
                    clearCalculation()
                    isResultShown = false
                }
                val buttonText = (v as Button).text
                appendTextToCalculation(buttonText.toString())
            }
        }

        findViewById<Button>(R.id.ce).setOnClickListener {
            clearCalculation()
        }

        findViewById<Button>(R.id.igual).setOnClickListener {
            calculateResult()
        }
    }

    private fun appendTextToCalculation(text: String) {
        val currentText = textViewCalculo.text.toString()
        if (currentText == "0" || isResultShown) {
            textViewCalculo.text = text
        } else {
            textViewCalculo.text = currentText + text
        }
        isResultShown = false
    }

    private fun clearCalculation() {
        textViewCalculo.text = "0"
        textViewResultado.text = ""
    }

    private fun calculateResult() {
        val expression = textViewCalculo.text.toString()

        try {
            val result = ExpressionBuilder(expression).build().evaluate()
            val formattedResult = formatResult(result)
            textViewResultado.text = formattedResult
            isResultShown = true
        } catch (e: Exception) {
            textViewResultado.text = "Erro"
            e.printStackTrace()
        }
    }

    private fun formatResult(result: Double): String {
        return if (result.isNaN() || result.isInfinite()) {
            "Erro"
        } else {
            val formattedResult = if (result.toLong().toDouble() == result) {
                result.toLong().toString()
            } else {
                String.format("%.6f", result)
                    .replace("0*$".toRegex(), "")
                    .replace("\\.$".toRegex(), "")
            }
            formattedResult
        }}
}