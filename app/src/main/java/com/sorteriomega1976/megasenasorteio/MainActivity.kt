package com.sorteriomega1976.megasenasorteio


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val messages = arrayOf(
        "Se você gosta de jogos, é importante lembrar que a vida real também pode ser emocionante e desafiadora!",
        "Lembre-se de manter uma vida equilibrada e saudável, com atividades variadas e momentos de lazer.",
        "Jogos podem ser uma ótima forma de passar o tempo, mas é importante não deixar que eles dominem sua vida e suas escolhas.",
        "Não deixe que os jogos te afaste do que realmente importa na vida, como amigos, família e seus objetivos pessoais.",
        "Lembre-se de que jogar é uma atividade divertida, mas não é a única coisa que importa na vida.",
        "Se você perceber que está jogando demais, tente estabelecer limites, ou mesmo fazer uma pausa por um tempo.",
        "O jogo pode te dar uma sensação de prazer e recompensa, mas também pode te causar ansiedade e irritabilidade. Não deixe que o jogo controle suas emoções.",
        "O jogo pode ser uma forma de escapar dos problemas, porém pode criar mais dificuldades. Enfrente suas questões com equilibrio e disposição",
        "Você é muito mais do que um jogador. Você tem talentos e potenciais, explore outras possibilidades!",
        "Você é um ser humano completo e incrivel! Não se veja apenas como um jogador!",
        "Seus sonhos vão muito além dos jogos, vá atrás deles!",
        "Não se limite ao jogo, explore todas as novas possibilidades!",
        "Você é capaz de muito mais do que imagina, vá em frente!",
        "O mundo está cheio de oportunidades, concentre-se nas suas!",
        "Você é capaz de conquistar grandes coisas, vá além do jogo!",
        "Não tenha medo de experimentar coisas novas e descobrir seu potencial!",
        "Você é mais do que um jogador, você é um ser humano incrível e único!"
    )

    private lateinit var bt1: Button
    private lateinit var bt2: Button
    private lateinit var tx1: TextView
    private lateinit var tv3: TextView

    private val resultados = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt1 = findViewById(R.id.bt1)
        bt2 = findViewById(R.id.bt2)
        tx1 = findViewById(R.id.tx1)
        tv3 = findViewById(R.id.tv3)

        val animFadeOut = AnimationUtils.loadAnimation(applicationContext, R.anim.button_fade_out)
        val animFadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        val animFadeIn2 = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in2)

        animFadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {

                val random = Random
                val numbers = mutableSetOf<Int>()
                while (numbers.size < 6) {
                    val n = random.nextInt(60) + 1
                    if (n !in numbers) {
                        numbers.add(n)
                    }
                }

                val sortedNumbers = numbers.sorted()
                    .joinToString(separator = " - ") { it.toString().padStart(2, '0') }
                resultados.add(sortedNumbers)

                val resultadoText = resultados.joinToString(separator = "\n")
                val intent = Intent(this@MainActivity, ResultadoActivity::class.java)
                intent.putExtra("resultado", resultadoText)


                bt1.startAnimation(animFadeIn2)
                bt1.text = "Boa sorte!\n $sortedNumbers"
                bt1.setBackgroundResource(androidx.appcompat.R.drawable.abc_ab_share_pack_mtrl_alpha)

                tx1.text = messages.random()
                tx1.startAnimation(animFadeIn)
                tv3.text = "Se você ganhar, faça um PIX como\n agradecimento (35)992469549"
                tv3.startAnimation(animFadeIn)

            }
        })

        bt1.setOnClickListener {
            bt1.startAnimation(animFadeOut)
            tx1.startAnimation(animFadeOut)
            tv3.startAnimation(animFadeOut)

        }

        bt2.setOnClickListener {
            val uri: Uri = Uri.parse("https://loterias.caixa.gov.br/Paginas/Mega-Sena.aspx")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        val btRes: Button = findViewById(R.id.btRes)
        btRes.setOnClickListener {
            val resultadoText = resultados.joinToString(separator = "\n\n")
            val intent = Intent(this, ResultadoActivity::class.java)
            intent.putExtra("resultado", resultadoText)
            startActivity(intent)
        }
    }
}
