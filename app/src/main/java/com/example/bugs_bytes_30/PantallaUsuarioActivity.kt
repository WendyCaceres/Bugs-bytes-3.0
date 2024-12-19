package com.example.bugs_bytes_30

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PantallaUsuarioActivity : AppCompatActivity() {
    private lateinit var botonInicio: ImageButton
    private lateinit var botonEstadistica: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pantalla_usuario)

        // Configuración de insets para vista principal
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración de botones
        botonInicio = findViewById(R.id.botoninicio)
        botonEstadistica = findViewById(R.id.botonestadistica)

        botonInicio.setOnClickListener {
            val intent = Intent(this, PantallaPrincipal::class.java)
            startActivity(intent)
        }

        botonEstadistica.setOnClickListener {
            val intent = Intent(this, Formulario3::class.java)
            startActivity(intent)
        }

        // Recuperar datos enviados desde la actividad anterior
        val bundle = intent.extras
        val email = intent.getStringExtra("email") ?: "Sin correo"
        val fechaNacimiento = bundle?.getString("Fecha_nacimiento") ?: "Sin fecha"
        val nombreUsuario = bundle?.getString("Nombre_usuario") ?: "Sin nombre"
        val telefono = bundle?.getString("Telefono") ?: "Sin teléfono"

        // Configurar vista con datos
        setup(email,fechaNacimiento, nombreUsuario, telefono)
    }

    private fun setup(email: String, fechaNacimiento: String, nombreUsuario: String, telefono: String) {
        val emailTextView: TextView = findViewById(R.id.email_text) // Asegúrate de que este ID exista en tu XML
        val nombreCompleto: TextView = findViewById(R.id.nombre_completo)
        val numeroDeTelefono: TextView = findViewById(R.id.numero_de_telefono)
        val fechaDeNacimiento: TextView = findViewById(R.id.fecha_de_nacimiento)

        title = "Perfil de Usuario"

        // Asignar valores a las vistas
        emailTextView.text = email
        nombreCompleto.text = nombreUsuario
        numeroDeTelefono.text = telefono
        fechaDeNacimiento.text = fechaNacimiento
    }
}