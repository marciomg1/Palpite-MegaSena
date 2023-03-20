package com.sorteriomega1976.megasenasorteio
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.net.Uri
import android.view.animation.Animation
import android.widget.TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bt1 = findViewById<Button>(R.id.bt1)
        val bt2 = findViewById<Button>(R.id.bt2)
        val tx1 = findViewById<TextView>(R.id.tx1)
        val tv3 = findViewById<TextView>(R.id.tv3)
        val anim = android.view.animation.AnimationUtils.loadAnimation(applicationContext, R.anim.button_fade_out)
        val fadeInAnimation = android.view.animation.AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        val fadeInAnimation2 = android.view.animation.AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in2)

        anim.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                val random = java.util.Random()
                val numbers = mutableSetOf<Int>()
                while (numbers.size < 6) {
                    val n = random.nextInt(60) + 1
                    if (n !in numbers) {
                        numbers.add(n)
                    }
                }

                val sortedNumbers = numbers.sorted().map { it.toString().padStart(2, '0') }.joinToString(separator = " - ")
                 bt1.startAnimation(fadeInAnimation2)  //criar uma nova animação dedicada ao bt1
                bt1.text = "Boa sorte!\n $sortedNumbers"
                bt1.setBackgroundResource(androidx.appcompat.R.drawable.abc_ab_share_pack_mtrl_alpha)
                val randomIndex = Random.nextInt(messages.size)
                tx1.text = messages[randomIndex]
                tx1.startAnimation(fadeInAnimation)
                tv3.text = "Se vc ganhar faça um PIX como\nAgradecimento (35)992469549"
                tv3.startAnimation(fadeInAnimation)
                //bt1.startAnimation(fadeInAnimation2)

            }
        })
        bt1.setOnClickListener {
            bt1.startAnimation(anim)
            tx1.startAnimation(anim)
            tv3.startAnimation(anim)

        }

        bt2.setOnClickListener {
            val uri: Uri = Uri.parse("https://loterias.caixa.gov.br/Paginas/Mega-Sena.aspx")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}