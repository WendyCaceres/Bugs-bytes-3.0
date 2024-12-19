package com.example.bugs_bytes_30

import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bugs_bytes_30.databinding.ActivityPantallaPrincipalBinding
import java.util.*

class PantallaPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityPantallaPrincipalBinding
    private var totalSavings = 1500.0
    private var totalExpenses = 500.0
    private val historyList = mutableListOf(
        "25/11/2024 - TOTAL: 102Bs",
        "26/11/2024 - TOTAL: 100Bs",
        "27/11/2024 - TOTAL: 192Bs",
        "28/11/2024 - TOTAL: 210Bs",
        "29/11/2024 - TOTAL: 112Bs",
        "30/11/2024 - TOTAL: 85Bs"
    )
    private var filteredHistoryList = historyList.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textInputEditTextDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona una fecha")
                .build()

            datePicker.show(supportFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { selection ->
                val selectedDate = formatDate(selection)
                binding.textInputEditTextDate.setText(selectedDate)
                filterHistoryByDate(selectedDate)
            }
        }

        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewHistory.adapter = HistoryAdapter(filteredHistoryList)

        binding.botoningresos.setOnClickListener {
            totalSavings += 100
            historyList.add("${getCurrentDate()} - TOTAL: 100Bs")
            updateStatistics()
            binding.recyclerViewHistory.adapter?.notifyDataSetChanged()
        }

        binding.botonegresos.setOnClickListener {
            totalExpenses += 50
            historyList.add("${getCurrentDate()} - TOTAL: 50Bs")
            updateStatistics()
            binding.recyclerViewHistory.adapter?.notifyDataSetChanged()
        }
        binding.txtFechaSeleccionada.setOnClickListener {
            val intent = Intent(this, Detallegastos::class.java)
            startActivity(intent)
        }

        binding.botonperfil.setOnClickListener {
            val intent = Intent(this, PantallaUsuarioActivity::class.java)
            startActivity(intent)
        }

        binding.botonestadistica.setOnClickListener {
            val intent = Intent(this, PantallaEstadistica::class.java)
            startActivity(intent)
        }
        binding.txtFechaSeleccionada.setOnClickListener {
            val selectedDate = binding.textInputEditTextDate.text.toString()
            val intent = Intent(this, Detallegastos::class.java)
            intent.putExtra("SELECTED_DATE", selectedDate)
            startActivity(intent)
        }
        val bundle = intent.extras
        val email = intent.getStringExtra("email") ?: "Sin correo"
        val fechaNacimiento = bundle?.getString("Fecha_nacimiento") ?: "Sin fecha"
        val nombreUsuario = bundle?.getString("Nombre_usuario") ?: "Sin nombre"
        val telefono = bundle?.getString("telefono") ?: "Sin teléfono"
        val I_inicial = bundle?.getString("Ingreso Inicial") ?: "Sin teléfono"
        val M_ahorro = bundle?.getString("Meta de ahorro") ?: "Sin datos"
        val fecha = bundle?.getString("Fecha") ?: "Sin datos"

        setup(email,fechaNacimiento,nombreUsuario,telefono,I_inicial,M_ahorro,fecha)
    }

    private fun formatDate(selection: Long?): String {
        return if (selection != null) {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formatter.format(Date(selection))
        } else ""
    }

    private fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date())
    }

    private fun filterHistoryByDate(date: String) {
        filteredHistoryList = historyList.filter {
            it.startsWith(date)
        }.toMutableList()
        binding.recyclerViewHistory.adapter = HistoryAdapter(filteredHistoryList)
    }

    private fun updateStatistics() {
        binding.textoegresos.text = "Bs. $totalExpenses"
        binding.total.text = "Bs. $totalSavings"
        val total = totalSavings - totalExpenses
        binding.montototal.text = "Total\n${"%.2f".format(total)} Bs"
    }

    inner class HistoryAdapter(private val history: List<String>) :
        RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

        inner class HistoryViewHolder(itemView: TextView) : RecyclerView.ViewHolder(itemView) {
            val tvItem: TextView = itemView

            init {
                tvItem.setOnClickListener {
                    val intent = Intent(this@PantallaPrincipal, Detallegastos::class.java)
                    startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): HistoryViewHolder {
            val button = Button(parent.context).apply {
                text = "Ver detalle"
                setTextColor(resources.getColor(android.R.color.holo_blue_dark, null))
                textSize = 16f
                setPadding(16, 8, 16, 8)
            }
            return HistoryViewHolder(button)
        }


        override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
            holder.tvItem.text = history[position]
            holder.tvItem.setOnClickListener {
                val intent = Intent(this@PantallaPrincipal, Detallegastos::class.java)
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int = history.size
    }
    private fun setup(email: String, fechaNacimiento: String, nombreUsuario: String, telefono: String, I_inicial: String,
                      M_ahorro: String, fecha:String){
        val button_siguiente = findViewById<Button>(R.id.button_siguiente)
        button_siguiente.setOnClickListener{
            val intent = Intent(this, Formulario2::class.java).apply {
                putExtra("email", email)
                putExtra("Fecha_nacimiento", fechaNacimiento)
                putExtra("Nombre_usuario",nombreUsuario )
                putExtra("Telefono", telefono)
                putExtra("I_inicial", I_inicial)
                putExtra("M_ahorro", M_ahorro)
                putExtra("fecha", fecha)
            }
            startActivity(intent)
        }
    }
}
