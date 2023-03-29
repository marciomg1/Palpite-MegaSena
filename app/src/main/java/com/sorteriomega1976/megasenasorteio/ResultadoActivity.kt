package com.sorteriomega1976.megasenasorteio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)
        val resultadoText = intent.getStringExtra("resultado")

        findViewById<TextView>(R.id.tvR).text = resultadoText

        val btV: Button = findViewById(R.id.btV)
        btV.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
