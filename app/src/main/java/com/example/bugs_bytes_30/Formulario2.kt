package com.example.bugs_bytes_30

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import android.app.DatePickerDialog
import java.util.Calendar

class Formulario2 : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario2)

        val buttonSiguiente = findViewById<Button>(R.id.button_siguiente)
        val buttonAtras: Button = findViewById(R.id.button_atras)
        val dateEditText: EditText = findViewById(R.id.textInputEditTextDate)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonSiguiente.setOnClickListener {
            val intent = Intent(this, Formulario3::class.java)
            startActivity(intent)
        }

        buttonAtras.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        setupDatePicker(dateEditText)

        val email = intent.getStringExtra("email") ?: "Sin correo"
        val fechaNacimiento = intent.getStringExtra("Fecha_nacimiento") ?: "Sin fecha"
        val nombreUsuario = intent.getStringExtra("Nombre_usuario") ?: "Sin nombre"
        val telefono = intent.getStringExtra("Telefono") ?: "Sin teléfono"

        setup(email, fechaNacimiento, nombreUsuario, telefono)
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

    private fun setup(email: String, fechaNacimiento: String, nombreUsuario: String, telefono: String) {
        title = "General"

        val textInputEditTextType: EditText = findViewById(R.id.textInputEditText)
        val textInputEditTextAmount: EditText = findViewById(R.id.textInputEditText2)
        val textInputEditTextDate: EditText = findViewById(R.id.textInputEditTextDate)
        val buttonSiguiente = findViewById<Button>(R.id.button_siguiente)

        buttonSiguiente.setOnClickListener {
            if (textInputEditTextType.text.isNullOrBlank() ||
                textInputEditTextAmount.text.isNullOrBlank() ||
                textInputEditTextDate.text.isNullOrBlank()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val ingreso = hashMapOf(
                "Ingreso Inicial" to textInputEditTextType.text.toString(),
                "Meta de ahorro" to textInputEditTextAmount.text.toString(),
                "Fecha" to textInputEditTextDate.text.toString()
            )

            db.collection("users")
                .document(email)
                .collection("General")
                .add(ingreso)
                .addOnSuccessListener {
                    val intent = Intent(this, PantallaUsuarioActivity::class.java).apply {
                        putExtra("email", email)
                        putExtra("Fecha_nacimiento", fechaNacimiento)
                        putExtra("Nombre_usuario", nombreUsuario)
                        putExtra("Telefono", telefono)
                        putExtra("Ingreso Inicial", textInputEditTextType.text.toString())
                        putExtra("Meta de ahorro", textInputEditTextAmount.text.toString())
                        putExtra("Fecha", textInputEditTextDate.text.toString())
                    }
                    Toast.makeText(this, "Ingreso guardado correctamente.", Toast.LENGTH_SHORT).show()
                    textInputEditTextType.text.clear()
                    textInputEditTextAmount.text.clear()
                    textInputEditTextDate.text.clear()
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al guardar el ingreso: ${e.message}", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
        }
    }
}
