package com.example.bugs_bytes_30

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.DatePickerDialog
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import java.util.Calendar
import com.google.firebase.firestore.FirebaseFirestore

enum class ProviderType {
    BASIC
}

class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dateEditText: EditText = findViewById(R.id.textInputEditTextDate)
        setupDatePicker(dateEditText)

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        setup(email ?: "", provider ?: "")
    }

    private fun setupDatePicker(editText: EditText) {
        editText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                editText.setText(formattedDate)
            }, year, month, day)

            datePickerDialog.show()
        }
    }

    private fun setup(email: String, provider: String) {
        title = "Inicio"

        val buttonSiguiente: Button = findViewById(R.id.button_siguiente)
        val nombreUsuarioEditText: EditText = findViewById(R.id.textInputEditText)
        val telefonoEditText: EditText = findViewById(R.id.textInputEditText3)
        val fechaNacimientoEditText: EditText = findViewById(R.id.textInputEditTextDate)

        buttonSiguiente.setOnClickListener {
            // Escribir datos en Firestore
            db.collection("users").document(email).set(
                hashMapOf(
                    "provider" to provider,
                    "Nombre_usuario" to nombreUsuarioEditText.text.toString(),
                    "Telefono" to telefonoEditText.text.toString(),
                    "Fecha_nacimiento" to fechaNacimientoEditText.text.toString()
                )
            ).addOnSuccessListener {
                // Datos guardados, iniciar la nueva actividad
                val intent = Intent(this, Formulario2::class.java).apply {
                    putExtra("email", email)
                    putExtra("Fecha_nacimiento", fechaNacimientoEditText.text.toString())
                    putExtra("Nombre_usuario", nombreUsuarioEditText.text.toString())
                    putExtra("Telefono", telefonoEditText.text.toString())
                }
                startActivity(intent)
            }
        }
    }
}
