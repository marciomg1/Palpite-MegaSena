package com.sorteriomega1976.megasenasorteio

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // Mensagens exibidas ao sortear os números
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
        "Você é mais do que um jogador, você é um ser humano incrível e único!",
        "Aposte com moderação e mantenha o controle sobre suas escolhas.",
        "Não aposte mais do que pode perder.",
        "Gerencie bem o seu dinheiro e estabeleça um limite para as suas apostas.",
        "Não tente recuperar perdas apostando mais."
    )

    // Views utilizadas na atividade:
    private val REQUEST_CODE_UPDATE = 100
    private lateinit var bt1: Button
    private lateinit var bt2: Button
    private lateinit var tx1: TextView
    private lateinit var tv3: TextView
    private lateinit var btRes: TextView

    // Lista para armazenar os resultados já sorteados
    private val resultados = mutableListOf<String>()
    private fun limparResultados() {
        resultados.clear()

        // Limpa os resultados salvos no SharedPreferences
        val prefs = getPreferences(Context.MODE_PRIVATE)
        prefs.edit().remove("resultados").apply()

        // Limpa os resultados salvos em arquivo
        try {
            val file = File(getExternalFilesDir(null), "resultados.txt")
            file.writeText("")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialização das views
        bt1 = findViewById(R.id.bt1)
        bt2 = findViewById(R.id.bt2)
        tx1 = findViewById(R.id.tx1)
        tv3 = findViewById(R.id.tv3)
        btRes = findViewById(R.id.btRes)

        fun onBackPressed() {
            super.onBackPressed()
            finish()
        }

        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        fun verificarAtualizacao(appUpdateManager: AppUpdateManager) {
            val appUpdateInfoTask = appUpdateManager.appUpdateInfo
            appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    val dialog = AlertDialog.Builder(this)
                        .setTitle("Atualização disponível")
                        .setMessage("Uma nova versão do aplicativo está disponível. Deseja atualizar agora?")
                        .setPositiveButton("Sim") { dialog, which ->
                            appUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo,
                                AppUpdateType.IMMEDIATE,
                                this,
                                REQUEST_CODE_UPDATE
                            )
                        }
                        .setNegativeButton("Não", null)
                        .create()

                    dialog.show()
                }
            }
        }

        @Deprecated("onActivityResult é deprecado, use registerForActivityResult em seu lugar")
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == REQUEST_CODE_UPDATE) {
                when (resultCode) {
                    RESULT_OK -> {
                        // A atualização foi concluída com sucesso
                        Log.i(TAG, "Atualização concluída com sucesso")
                        recreate()
                    }
                    RESULT_CANCELED -> {
                        // O usuário cancelou a atualização
                        Log.i(TAG, "Atualização cancelada pelo usuário")
                    }
                    ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
                        // A atualização falhou
                        Log.i(TAG, "Falha na atualização")
                    }
                }
            }
        }

        // Recupera os resultados já sorteados do SharedPreferences e adiciona na lista resultados
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val resultadosString = prefs.getString("resultados", "")
        if (resultadosString?.isNotBlank() == true) {
            resultados.addAll(resultadosString.split(","))
        }

        // Animações utilizadas na atividade
        val animFadeOut = AnimationUtils.loadAnimation(applicationContext, R.anim.button_fade_out)
        val animFadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        val animFadeIn2 = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in2)

        bt1.setOnClickListener {
            // Inicia a animação de fade out do botão 1
            bt1.startAnimation(animFadeOut)
            tv3.startAnimation(animFadeOut)
        }

        bt2.setOnClickListener {
            val uri: Uri = Uri.parse("https://loterias.caixa.gov.br/Paginas/Mega-Sena.aspx")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


        //Define um listener para o clique no botão btRes. Quando o botão é clicado, é exibido o
        // resultado completo dos sorteios já realizados.
            btRes.setOnClickListener {
            val resultadoText = resultados.joinToString(separator = "\n\n")
            val intent = Intent(this, ResultadoActivity::class.java)
            intent.putExtra("resultado", resultadoText)
            startActivity(intent)
        }

        // Verifica se a flag limparResultados está presente nos extras da intent
        val limparResultados = intent.getBooleanExtra("limparResultados", false)
        if (limparResultados) {
            limparResultados()
        }

        animFadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
            // Sorteia 6 números aleatórios entre 1 e 60 e exibe o resultado
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
                val sortedNumbersWithDate = "${SimpleDateFormat("dd/MM/yy - HH:mm").format(Date())} -       $sortedNumbers"
                resultados.add(sortedNumbersWithDate)


                // Salva os resultados já sorteados no SharedPreferences
                val prefs = getPreferences(MODE_PRIVATE)
                prefs.edit().putString("resultados", resultados.joinToString(",")).apply()

                 fun limparResultados() {
                    val prefs = getPreferences(MODE_PRIVATE)
                    prefs.edit().remove("resultados").apply()
                    resultados.clear()
                }

                // Atualiza a exibição dos botões e textos com as animações apropriadas
                bt1.startAnimation(animFadeIn2)
                bt1.text = "Boa sorte!\n $sortedNumbers"
                bt1.setBackgroundResource(androidx.appcompat.R.drawable.abc_ab_share_pack_mtrl_alpha)
                tx1.text = messages.random()
                tx1.startAnimation(animFadeIn)

                // Define a URL que será aberta ao clicar no texto
                val url = "https://play.google.com/store/apps/details?id=com.sorteriomega1976.megasenasorteio"
                // Define o texto que será exibido na tela
                val linkText = "Se você ganhar, faça um PIX como\n agradecimento (35)992469549\nE avalie o App :)"
                // Cria uma nova SpannableString a partir do texto
                val spannableString = SpannableString(linkText)
                // Cria um novo ClickableSpan para o texto clicável
                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        // Ao clicar no texto, abre a URL definida anteriormente
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)

                        // Remove o sublinhado do texto
                        ds.isUnderlineText = false
                        // Define a transparência do texto em 80%
                        ds.alpha = 60f.toInt() // valor de 0.0f a 1.0f
                        // Define a cor do texto
                        val textColor = ContextCompat.getColor(tv3.context, R.color.linkColor)
                        ds.color = textColor

                    }
                }
                 // Aplica o ClickableSpan ao texto
                spannableString.setSpan(clickableSpan, 0, linkText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                // Define o texto formatado na TextView
                tv3.text = spannableString
                // Aplica a animação de fade-in na TextView
                tv3.startAnimation(animFadeIn)
                // Define o movimento do texto ao clicar no link
                tv3.movementMethod = LinkMovementMethod.getInstance()
                // Define a cor transparente de realce do texto ao clicar no link
                tv3.highlightColor = Color.TRANSPARENT

                // Salva os resultados em um arquivo de texto
                salvarResultados(resultados)
            }
            private fun salvarResultados(resultados: MutableList<String>) {
                try {
                    val file = File(getExternalFilesDir(null), "resultados.txt")
                    val fileWriter = FileWriter(file, true)
                    for (resultado in resultados) {
                        fileWriter.write("$resultado\n")
                    }
                    fileWriter.flush()
                    fileWriter.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

}


