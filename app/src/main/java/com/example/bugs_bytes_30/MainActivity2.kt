package com.example.progra3

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bugs_bytes_30.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity2 : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var etiquetaEditText: EditText
    private lateinit var cantidadEditText: EditText
    private lateinit var categoriaEditText: EditText
    private lateinit var btnGuardar: Button
    private lateinit var recyclerView: RecyclerView

    private val registros = mutableListOf<Map<String, String>>()
    private lateinit var registroAdapter: RegistroAdapter

    private val sharedPrefFile = "com.example.prograiii2024.sharedpreferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        nombreEditText = findViewById(R.id.nombre)
        etiquetaEditText = findViewById(R.id.etiqueta)
        cantidadEditText = findViewById(R.id.cantidad)
        categoriaEditText = findViewById(R.id.categoria)
        btnGuardar = findViewById(R.id.btn_guardar)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        registroAdapter = RegistroAdapter(registros)
        recyclerView.adapter = registroAdapter

        cargarRegistros()

        btnGuardar.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val etiqueta = etiquetaEditText.text.toString()
            val cantidad = cantidadEditText.text.toString()
            val categoria = categoriaEditText.text.toString()

            if (nombre.isNotEmpty() && etiqueta.isNotEmpty() && cantidad.isNotEmpty() && categoria.isNotEmpty()) {
                val nuevoRegistro = mapOf(
                    "nombre" to nombre,
                    "etiqueta" to etiqueta,
                    "cantidad" to cantidad,
                    "categoria" to categoria
                )
                registros.add(nuevoRegistro)
                registroAdapter.notifyItemInserted(registros.size - 1)
                guardarRegistros()
                clearFields()
            }
        }
    }

    private fun clearFields() {
        nombreEditText.text.clear()
        etiquetaEditText.text.clear()
        cantidadEditText.text.clear()
        categoriaEditText.text.clear()
    }

    private fun cargarRegistros() {
        val sharedPref = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val json = sharedPref.getString("registros", null)

        if (json != null) {
            val gson = Gson()
            val type = object : TypeToken<List<Map<String, String>>>() {}.type
            val registrosGuardados: List<Map<String, String>> = gson.fromJson(json, type)
            registros.clear()
            registros.addAll(registrosGuardados)
            registroAdapter.notifyDataSetChanged()
        }
    }

    private fun guardarRegistros() {
        val sharedPref = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val gson = Gson()
        val json = gson.toJson(registros)

        editor.putString("registros", json)
        editor.apply()
    }

    inner class RegistroAdapter(private val registros: List<Map<String, String>>) : RecyclerView.Adapter<RegistroAdapter.RegistroViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
            return RegistroViewHolder(view)
        }

        override fun onBindViewHolder(holder: RegistroViewHolder, position: Int) {
            val registro = registros[position]
            holder.bind(registro)
        }

        override fun getItemCount(): Int = registros.size

        inner class RegistroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val text1: TextView = itemView.findViewById(android.R.id.text1)
            private val text2: TextView = itemView.findViewById(android.R.id.text2)

            fun bind(registro: Map<String, String>) {
                text1.text = "Nombre: ${registro["nombre"]}, Etiqueta: ${registro["etiqueta"]}"
                text2.text = "Cantidad: ${registro["cantidad"]}, Categoría: ${registro["categoria"]}"
            }
        }
    }
}