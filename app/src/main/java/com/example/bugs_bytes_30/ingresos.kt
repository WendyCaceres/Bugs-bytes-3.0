package com.example.bugs_bytes_30

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bugs_bytes_30.databinding.ActivityIngresosBinding


class Ingresos : AppCompatActivity() {


    private lateinit var binding: ActivityIngresosBinding
    private val recyclerIngresosAdapter by lazy { RecyclerIngresosAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngresosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.botonListoIngresos.setOnClickListener {
            val nombreIngreso = binding.textInputEditText.text.toString()
            val tipoIngreso = binding.textInputEditText2.text.toString()
            val montoIngreso = binding.textInputEditText3.text.toString()
            val ingreso = Ahorros(nombreIngreso, tipoIngreso,montoIngreso)
            recyclerIngresosAdapter.addDataToList(listOf(ingreso))
            recyclerIngresosAdapter.notifyDataSetChanged()
        }

        binding.botonAtras.setOnClickListener {
            val intent = Intent(this, PantallaPrincipal::class.java)
            startActivity(intent)
        }
        binding.botonHecho.setOnClickListener {
            val intent = Intent(this, Egresos::class.java)
            startActivity(intent)
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val listaDatos = listOf<Ahorros>()

        recyclerIngresosAdapter.addDataToList(listaDatos)

        binding.recyclerIngresos.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerIngresosAdapter
        }
    }
}