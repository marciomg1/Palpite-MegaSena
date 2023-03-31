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

        // Inicializa a TextView a partir do seu ID definido no arquivo de layout
        tvR = findViewById<TextView>(R.id.tvR)

        // Verifica se há um estado anterior salvo da atividade
        if (savedInstanceState != null) {
            // Se houver um estado anterior, atualiza o texto da TextView com o valor correspondente
            val resultadoText = savedInstanceState.getString("resultado")
            tvR.text = resultadoText
        } else {
            // Caso contrário, atualiza o texto da TextView com o valor passado pela Intent que iniciou a atividade
            val resultadoText = intent.getStringExtra("resultado")
            tvR.text = resultadoText
        }

        // Configura o botão "Voltar" para iniciar a MainActivity quando pressionado
        val btV: Button = findViewById(R.id.btV)
        btV.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}
