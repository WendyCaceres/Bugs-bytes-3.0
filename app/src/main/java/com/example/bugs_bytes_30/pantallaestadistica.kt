package com.example.bugs_bytes_30

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.bugs_bytes_30.databinding.ActivityPantallaestadisticaBinding

class PantallaEstadistica : AppCompatActivity() {
    private lateinit var binding: ActivityPantallaestadisticaBinding
    private val gastos = mutableMapOf(
        "Pasajes" to Pair(250, 80),
        "Comida" to Pair(200, 60),
        "Gimnasio" to Pair(150, 40),
        "Parqueo" to Pair(100, 20)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaestadisticaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTotal.text = "Total\n${calcularTotal()} Bs"
        iniciarAnimacionProgreso()

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
    }

    private fun calcularTotal(): Int {
        return gastos.values.sumOf { it.first }
    }
    private fun iniciarAnimacionProgreso() {
        val handler = Handler()
        val totalProgreso = 80
        val delay: Long = 50


        Thread(Runnable {
            for (i in 0..totalProgreso) {
                try {
                    Thread.sleep(delay)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                handler.post {
                    binding.progressSemanal.progress = i
                }
            }
        }).start()
    }
}
