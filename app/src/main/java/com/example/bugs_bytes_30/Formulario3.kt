package com.example.bugs_bytes_30

import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class Formulario3 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario3)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val buttonSiguiente=findViewById<Button>(R.id.botton_terminar)
        buttonSiguiente.setOnClickListener {
            val intent = Intent(this, PantallaPrincipal::class.java)
            startActivity(intent)
        }
        val buttonAtras: Button = findViewById(R.id.botton_atras)
        buttonAtras.setOnClickListener {
            val intent = Intent(this, Formulario1::class.java)
            startActivity(intent)
        }
        val textInputEditText: TextInputEditText = findViewById(R.id.textInputEditText)
        val addCategoryButton: Button = findViewById(R.id.addCategoryButton)
        val categoryList: LinearLayout = findViewById(R.id.categoryList)

        addCategoryButton.setOnClickListener {
            val categoryName = textInputEditText.text.toString().trim()

            if (categoryName.isNotEmpty()) {
                // Agregar una nueva categoría
                addCategory(categoryList, categoryName)
                textInputEditText.text = null
            } else {
                textInputEditText.error = "Por favor, ingrese un nombre válido"
            }
        }
    }

    private fun addCategory(parent: LinearLayout, categoryName: String) {
        val categoryContainer = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.HORIZONTAL
            setPadding(16, 16, 16, 16)
        }

        val categoryTextView = TextView(this).apply {
            text = categoryName
            textSize = 18f
            setTextColor(resources.getColor(android.R.color.black))
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        val editButton = ImageButton(this).apply {
            setImageResource(android.R.drawable.ic_menu_edit)
            setBackgroundColor(resources.getColor(android.R.color.transparent))
            setOnClickListener { editCategory(categoryTextView) }
        }

        val deleteButton = ImageButton(this).apply {
            setImageResource(android.R.drawable.ic_menu_delete)
            setBackgroundColor(resources.getColor(android.R.color.transparent))
            setOnClickListener { parent.removeView(categoryContainer) }
        }

        categoryContainer.addView(categoryTextView)
        categoryContainer.addView(editButton)
        categoryContainer.addView(deleteButton)

        parent.addView(categoryContainer)
    }

    private fun editCategory(categoryTextView: TextView) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Categoría")

        val input = EditText(this).apply {
            setText(categoryTextView.text)
        }
        builder.setView(input)

        builder.setPositiveButton("Guardar") { _, _ ->
            categoryTextView.text = input.text.toString().trim()
        }

        builder.setNegativeButton("Cancelar", null)

        builder.show()
    }
}