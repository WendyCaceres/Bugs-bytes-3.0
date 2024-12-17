package com.example.bugs_bytes_30

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class PantallaPrincipal : AppCompatActivity() {
    private lateinit var tvUserName: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvExpenses: TextView
    private lateinit var tvSavings: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnAddIncome: Button
    private lateinit var btnAddExpense: Button
    private lateinit var rvHistory: RecyclerView
    private lateinit var etSearchDate: EditText
    private lateinit var btnSearch: ImageButton

    private var totalSavings = 1500.0
    private var totalExpenses = 500.0
    private val historyList = mutableListOf(
        "25/11/2024 - TOTAL: 102Bs",
        "26/11/2024 - TOTAL: 100Bs",
        "27/11/2024 - TOTAL: 192Bs",
        "28/11/2024 - TOTAL: 210Bs",
        "29/11/2024 - TOTAL: 200Bs",
        "30/11/2024 - TOTAL: 85Bs"
    )
    private var filteredHistoryList = historyList.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)

        tvUserName = findViewById(R.id.tvUserName)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvExpenses = findViewById(R.id.tvExpenses)
        tvSavings = findViewById(R.id.tvSavings)
        progressBar = findViewById(R.id.progressBar)
        btnAddIncome = findViewById(R.id.btnAddIncome)
        btnAddExpense = findViewById(R.id.btnAddExpense)
        rvHistory = findViewById(R.id.rvHistory)
        etSearchDate = findViewById(R.id.etSearchDate)
        btnSearch = findViewById(R.id.btnSearch)

        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.adapter = HistoryAdapter(filteredHistoryList)
        updateStatistics()

        btnAddIncome.setOnClickListener {
            totalSavings += 100
            historyList.add("Nuevo ingreso - TOTAL: 100Bs")
            updateStatistics()
            rvHistory.adapter?.notifyDataSetChanged()
        }

        btnAddExpense.setOnClickListener {
            totalExpenses += 50
            historyList.add("Nuevo egreso - TOTAL: 50Bs")
            updateStatistics()
            rvHistory.adapter?.notifyDataSetChanged()
        }

        btnSearch.setOnClickListener {
            val searchDate = etSearchDate.text.toString().trim()
            if (searchDate.isEmpty()) {
                val calendar = Calendar.getInstance()
                val datePicker = DatePickerDialog(
                    this,
                    { _, year, month, dayOfMonth ->
                        val selectedDate = "$dayOfMonth/${month + 1}/$year"
                        etSearchDate.setText(selectedDate)
                        filterHistoryByDate(selectedDate)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                datePicker.show()
            } else {
                filterHistoryByDate(searchDate)
            }
        }
    }

    private fun updateStatistics() {
        tvExpenses.text = "Bs. ${totalExpenses}"
        tvSavings.text = "Bs. ${totalSavings}"
        val total = totalSavings - totalExpenses
        tvTotalAmount.text = "Total\n${"%.2f".format(total)} Bs"

        val maxAmount = 1000.0
        val progress = if (totalExpenses > maxAmount) 100 else (totalExpenses / maxAmount * 100).toInt()
        progressBar.progress = progress
    }

    private fun filterHistoryByDate(date: String) {
        val formattedDate = date.trim()

        filteredHistoryList = historyList.filter {
            val historyDate = it.substring(0, 10)
            historyDate == formattedDate
        }.toMutableList()

        rvHistory.adapter?.notifyDataSetChanged()
    }

    inner class HistoryAdapter(private val history: List<String>) :
        RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

        inner class HistoryViewHolder(itemView: TextView) : RecyclerView.ViewHolder(itemView) {
            val tvItem: TextView = itemView
        }

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): HistoryViewHolder {
            val textView = TextView(parent.context).apply {
                textSize = 16f
                setPadding(16, 16, 16, 16)
            }
            return HistoryViewHolder(textView)
        }

        override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
            holder.tvItem.text = history[position]
        }

        override fun getItemCount(): Int = history.size
    }
}
