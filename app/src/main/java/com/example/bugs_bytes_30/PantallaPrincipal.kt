package com.example.bugs_bytes_30

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bugs_bytes_30.databinding.ActivityPantallaPrincipalBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class PantallaPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityPantallaPrincipalBinding
    private val db = FirebaseFirestore.getInstance()
    private var userEmail = ""
    private var ingresosDelMes = 0.0
    private val historialFiltrado = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener información pasada por Intent
        val bundle = intent.extras
        userEmail = bundle?.getString("email") ?: "Sin correo"

        // Configurar datos iniciales de la UI
        setupUI()

        // Manejar clics
        setupClickListeners()
    }

    private fun setupUI() {
        // Obtener datos del usuario desde Firestore
        db.collection("users").document(userEmail).get().addOnSuccessListener { document ->
            if (document.exists()) {
                val nombreUsuario = document.getString("Nombre_usuario") ?: "Usuario"
                val ingresoInicial = document.getDouble("Ingreso Inicial") ?: 0.0

                // Mostrar datos en la interfaz
                binding.nombreinicio.text = "Hola!\n$nombreUsuario"
                binding.montototal.text = "Total\n${"%.2f".format(ingresoInicial)} Bs"
            }
        }

        // Calcular ingresos del mes
        calcularIngresosDelMes()

        // Configurar RecyclerView para el historial
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewHistory.adapter = HistoryAdapter(historialFiltrado)
    }

    private fun setupClickListeners() {
        // Selección de fecha para el historial
        binding.textInputEditTextDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona una fecha")
                .build()

            datePicker.show(supportFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { selection ->
                val selectedDate = formatDate(selection)
                binding.textInputEditTextDate.setText(selectedDate)
                filtrarIngresosPorFecha(selectedDate)
            }
        }

        // Botón de ingresos
        binding.botoningresos.setOnClickListener {
            val intent = Intent(this, PantallaUsuarioActivity::class.java)
            startActivity(intent)
        }

        // Botón de egresos
        binding.botonegresos.setOnClickListener {
            val intent = Intent(this, PantallaEstadistica::class.java)
            startActivity(intent)
        }

        // Navegación a perfil
        binding.botonperfil.setOnClickListener {
            val intent = Intent(this, PantallaUsuarioActivity::class.java)
            startActivity(intent)
        }

        // Navegación a estadísticas
        binding.botonestadistica.setOnClickListener {
            val intent = Intent(this, PantallaEstadistica::class.java)
            startActivity(intent)
        }
    }

    private fun calcularIngresosDelMes() {
        val calendar = Calendar.getInstance()
        val inicioMes = calendar.apply {
            set(Calendar.DAY_OF_MONTH, 1)
        }.time
        val inicioMesTimestamp = inicioMes.time

        db.collection("ingresos")
            .whereEqualTo("userEmail", userEmail)
            .whereGreaterThanOrEqualTo("fecha", inicioMesTimestamp)
            .get()
            .addOnSuccessListener { querySnapshot ->
                ingresosDelMes = querySnapshot.documents.sumOf { it.getDouble("monto") ?: 0.0 }
                binding.textoegresos.text = "Ingresos de este mes: Bs. ${"%.2f".format(ingresosDelMes)}"
            }
    }

    private fun filtrarIngresosPorFecha(fecha: String) {
        db.collection("ingresos")
            .whereEqualTo("userEmail", userEmail)
            .whereEqualTo("fechaTexto", fecha)
            .get()
            .addOnSuccessListener { querySnapshot ->
                historialFiltrado.clear()
                historialFiltrado.addAll(querySnapshot.documents.map {
                    "${it.getString("fechaTexto") ?: ""} - TOTAL: ${it.getDouble("monto") ?: 0.0} Bs"
                })
                binding.recyclerViewHistory.adapter?.notifyDataSetChanged()
            }
    }

    private fun formatDate(selection: Long?): String {
        return if (selection != null) {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formatter.format(Date(selection))
        } else ""
    }

    // Adapter para mostrar el historial en la RecyclerView
    inner class HistoryAdapter(private val history: List<String>) :
        RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

        inner class HistoryViewHolder(itemView: TextView) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): HistoryViewHolder {
            val textView = TextView(parent.context)
            return HistoryViewHolder(textView)
        }

        override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
            (holder.itemView as TextView).text = history[position]
        }

        override fun getItemCount(): Int = history.size
    }
}

