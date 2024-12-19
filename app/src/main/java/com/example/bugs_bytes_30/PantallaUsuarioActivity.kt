package com.example.bugs_bytes_30

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class PantallaUsuarioActivity : AppCompatActivity() {
    private lateinit var botonInicio: ImageButton
    private lateinit var botonEstadistica: ImageButton
    private val db = FirebaseFirestore.getInstance() // Instancia de Firestore

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

        // Obtener correo del Intent
        val email = intent.getStringExtra("email") ?: "Sin correo"

        // Obtener datos desde Firestore
        obtenerDatosDeFirestore(email)
    }

    private fun obtenerDatosDeFirestore(email: String) {
        // Referencia al documento del usuario en Firestore
        val userDocRef = db.collection("users").document(email)

        // Obtener datos del documento
        userDocRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val fechaNacimiento = document.getString("Fecha_nacimiento") ?: "Sin fecha"
                val nombreUsuario = document.getString("Nombre_usuario") ?: "Sin nombre"
                val telefono = document.getString("Telefono") ?: "Sin teléfono"

                // Actualizar las vistas con los datos obtenidos
                setup(email, fechaNacimiento, nombreUsuario, telefono)
            } else {
                Toast.makeText(this, "No se encontró el usuario en Firestore.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Error al obtener los datos: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setup(email: String, fechaNacimiento: String, nombreUsuario: String, telefono: String) {
        val emailTextView: TextView = findViewById(R.id.email_text)
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
