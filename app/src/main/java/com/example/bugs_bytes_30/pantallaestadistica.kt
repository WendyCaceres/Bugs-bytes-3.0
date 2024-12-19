package com.example.bugs_bytes_30

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bugs_bytes_30.databinding.ActivityPantallaestadisticaBinding

class PantallaEstadistica : AppCompatActivity() {
    private lateinit var binding: ActivityPantallaestadisticaBinding
    private val gastos = mutableMapOf(
        "Pasajes" to Pair(50, 80),
        "Comida" to Pair(100, 60),
        "Gimnasio" to Pair(150, 40),
        "Parqueo" to Pair(20, 20)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaestadisticaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvTotal.text = "Total\n${calcularTotal()} Bs"
        mostrarCategorias()
        actualizarProgresos()
    }

    private fun actualizarProgresos() {
        binding.botonperfil.setOnClickListener {
            val intent = Intent(this, PantallaUsuarioActivity::class.java)
            startActivity(intent)
        }

        binding.botoninicio.setOnClickListener {
            val intent = Intent(this, PantallaPrincipal::class.java)
            startActivity(intent)
        }
        binding.btnIngresos.setOnClickListener {
            val intent = Intent(this, Ingresos::class.java)
            startActivity(intent)
        }
        binding.btnEgresos.setOnClickListener {
            val intent = Intent(this, Egresos::class.java)
            startActivity(intent)
        }
    }

    private fun calcularTotal(): Int {
        return gastos.values.sumOf { it.first }
    }

    private fun mostrarCategorias() {
        for ((categoria, datos) in gastos) {
            val progressBar = ProgressBar(this)
            val progreso = (datos.second.toFloat() / datos.first.toFloat() * 100).toInt()
            progressBar.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                20.dpToPx(this)
            )
            progressBar.progress = progreso
            progressBar.max = 100
            progressBar.setProgressDrawable(resources.getDrawable(android.R.color.holo_red_dark, null))

            val textView = TextView(this)
            textView.text = "$categoria: ${datos.second} Bs"
            textView.setTextColor(resources.getColor(android.R.color.black))

            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.gravity = Gravity.CENTER_VERTICAL
            linearLayout.addView(textView)
            linearLayout.addView(progressBar)

            binding.categoryList.addView(linearLayout)
        }
    }

}

fun Int.dpToPx(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}
