package com.example.bugs_bytes_30

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.DatePickerDialog
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class Formulario2 : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario2)

        val buttonSiguiente = findViewById<Button>(R.id.button_siguiente)
        buttonSiguiente.setOnClickListener {
            val intent = Intent(this, Formulario3::class.java)
            startActivity(intent)
        }

        val buttonAtras: Button = findViewById(R.id.button_atras)
        buttonAtras.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dateEditText: EditText = findViewById(R.id.textInputEditTextDate)
        setupDatePicker(dateEditText)

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        if (email != null && provider != null) {
            setup(email,provider)
        }
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

    private fun setup(email: String,provider: String) {
        title = "Ingresos/Egresos"

        val textInputEditTextType: EditText = findViewById(R.id.textInputEditText)
        val textInputEditTextAmount: EditText = findViewById(R.id.textInputEditText2)
        val textInputEditTextDate: EditText = findViewById(R.id.textInputEditTextDate)

        val buttonGuardarIngreso = findViewById<Button>(R.id.button_siguiente)

        buttonGuardarIngreso.setOnClickListener {
            if (textInputEditTextType.text.isNullOrBlank() ||
                textInputEditTextAmount.text.isNullOrBlank() ||
                textInputEditTextDate.text.isNullOrBlank()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val monto = textInputEditTextAmount.text.toString().toDoubleOrNull()
            if (monto == null) {
                Toast.makeText(this, "El monto debe ser un número válido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val ingreso = hashMapOf(
                "Tipo" to textInputEditTextType.text.toString(),
                "Monto" to monto,
                "Fecha" to textInputEditTextDate.text.toString()
            )

            db.collection("users")
                .document(email)
                .collection("ingresos")
                .add(ingreso)
                .addOnSuccessListener {
                    Toast.makeText(this, "Ingreso guardado correctamente.", Toast.LENGTH_SHORT).show()
                    textInputEditTextType.text.clear()
                    textInputEditTextAmount.text.clear()
                    textInputEditTextDate.text.clear()

                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al guardar el ingreso: ${e.message}", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
        }
    }

}

