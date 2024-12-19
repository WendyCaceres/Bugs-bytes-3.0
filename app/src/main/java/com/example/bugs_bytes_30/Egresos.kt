package com.example.bugs_bytes_30

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bugs_bytes_30.databinding.ActivityEgresosBinding

class Egresos : AppCompatActivity() {

    private lateinit var binding: ActivityEgresosBinding

    private val recyclerEgresosAdapter by lazy { RecyclerEgresosAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEgresosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.botonListo.setOnClickListener {
            val nombreEgreso = binding.textInputEditText.text.toString()
            val tipoEgreso = binding.textInputEditText2.text.toString()
            val montoEgreso = binding.textInputEditText3.text.toString()
            val egreso = Gastos(nombreEgreso, tipoEgreso,montoEgreso)
            recyclerEgresosAdapter.addDataToList(listOf(egreso))
            recyclerEgresosAdapter.notifyDataSetChanged()
        }





        setUpRecyclerView()


    }

    private fun setUpRecyclerView() {
        val listaDatos = listOf<Gastos>()

        recyclerEgresosAdapter.addDataToList(listaDatos)

        binding.recyclerEgresos.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerEgresosAdapter
        }

    }
}