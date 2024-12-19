package com.example.bugs_bytes_30.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bugs_bytes_30.R
import com.example.bugs_bytes_30.databinding.ItemAhorroBinding
import com.example.bugs_bytes_30.dataclass.Ahorro

class AhorrosAdapter(private val ahorros: List<Ahorro>) : RecyclerView.Adapter<AhorrosAdapter.AhorroViewHolder>() {

    inner class AhorroViewHolder(private val binding: ItemAhorroBinding) : RecyclerView.ViewHolder(binding.root) {
        val descripcion = binding.descripcionAhorro
        val monto = binding.montoAhorro
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AhorroViewHolder {
        val binding = ItemAhorroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AhorroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AhorroViewHolder, position: Int) {
        val ahorro = ahorros[position]
        holder.descripcion.text = ahorro.descripcion
        holder.monto.text = "${ahorro.monto} Bs"
    }

    override fun getItemCount() = ahorros.size
}
