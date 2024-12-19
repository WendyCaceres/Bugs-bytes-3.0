package com.example.bugs_bytes_30

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bugs_bytes_30.adapter.AhorrosAdapter
import com.example.bugs_bytes_30.adapter.GastosAdapter
import com.example.bugs_bytes_30.databinding.ActivityDetallegastosBinding
import com.example.bugs_bytes_30.dataclass.Ahorro
import com.example.bugs_bytes_30.dataclass.Gasto

class Detallegastos : AppCompatActivity() {
    private lateinit var binding: ActivityDetallegastosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallegastosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewGastos.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewAhorros.layoutManager = LinearLayoutManager(this)

        binding.recyclerViewGastos.adapter = GastosAdapter(getGastos())
        binding.recyclerViewAhorros.adapter = AhorrosAdapter(getAhorros())

        val selectedDate = intent.getStringExtra("SELECTED_DATE")
        if (selectedDate != null) {
            filterDataByDate(selectedDate)
        }

        binding.doneButton.setOnClickListener {
            val intent = Intent(this, PantallaPrincipal::class.java)
            startActivity(intent)
        }
    }

    private fun getGastos(): List<Gasto> {
        return listOf(
            Gasto("Pasaje Universidad", 5.0, "pasajes", "25/11/2024"),
            Gasto("Almuerzo", 20.0, "comida", "26/11/2024"),
            Gasto("Powerade", 7.0, "gimnasio", "27/11/2024"),
            Gasto("Parque Universidad", 80.0, "parque", "28/11/2024")
        )
    }

    private fun getAhorros(): List<Ahorro> {
        return listOf(
            Ahorro("Mesada diaria de papá", 20.0, "25/11/2024"),
            Ahorro("5 pesos del suelo", 5.0, "26/11/2024")
        )
    }

    private fun filterDataByDate(date: String) {
        val gastosFiltrados = getGastos().filter { it.fecha == date }
        val ahorrosFiltrados = getAhorros().filter { it.fecha == date }

        binding.recyclerViewGastos.adapter = GastosAdapter(gastosFiltrados)
        binding.recyclerViewAhorros.adapter = AhorrosAdapter(ahorrosFiltrados)
    }
}
