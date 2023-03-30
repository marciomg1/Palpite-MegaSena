package com.sorteriomega1976.megasenasorteio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultadoActivity : AppCompatActivity() {
    private lateinit var tvR: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        tvR = findViewById<TextView>(R.id.tvR)

        if (savedInstanceState != null) {
            val resultadoText = savedInstanceState.getString("resultado")
            tvR.text = resultadoText
        } else {
            val resultadoText = intent.getStringExtra("resultado")
            tvR.text = resultadoText
        }

        val btV: Button = findViewById(R.id.btV)
        btV.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("resultado", tvR.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val resultadoText = savedInstanceState.getString("resultado")
        tvR.text = resultadoText
    }
}
