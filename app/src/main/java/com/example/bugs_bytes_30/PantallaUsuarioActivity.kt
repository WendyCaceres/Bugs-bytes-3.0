package com.example.bugs_bytes_30

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PantallaUsuarioActivity : AppCompatActivity() {


    private lateinit var botoninicio: ImageButton
    private lateinit var botonestadistica: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pantalla_usuario)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        botoninicio = findViewById(R.id.botoninicio)
        botonestadistica = findViewById(R.id.botonestadistica)
        botoninicio.setOnClickListener {
            val intent = Intent(this, PantallaPrincipal::class.java)
            startActivity(intent)
        }
        botonestadistica.setOnClickListener {
            val intent = Intent(this, PantallaEstadistica::class.java)
            startActivity(intent)
        }
    }
}
