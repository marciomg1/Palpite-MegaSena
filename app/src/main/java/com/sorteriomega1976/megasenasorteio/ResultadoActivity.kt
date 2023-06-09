package com.sorteriomega1976.megasenasorteio

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ResultadoActivity : AppCompatActivity() {
    private lateinit var tvR: TextView
    private lateinit var mainActivity: MainActivity


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
        val btnLimpar = findViewById<Button>(R.id.btnLimpar)
        btnLimpar.setOnClickListener {
            val mainActivity = Intent(this, MainActivity::class.java)
            mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            mainActivity.putExtra("limparResultados", true)
            startActivity(mainActivity)
            finish()
        }
        val btAv: Button = findViewById(R.id.btAv)
        btAv.setOnClickListener {
            val uri: Uri = Uri.parse("https://play.google.com/store/apps/details?id=com.sorteriomega1976.megasenasorteio")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }
}
