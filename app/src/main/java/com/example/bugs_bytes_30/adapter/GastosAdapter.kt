package com.example.bugs_bytes_30.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bugs_bytes_30.R
import com.example.bugs_bytes_30.databinding.ItemGastoBinding
import com.example.bugs_bytes_30.dataclass.Gasto

class GastosAdapter(private val gastos: List<Gasto>) : RecyclerView.Adapter<GastosAdapter.GastoViewHolder>() {

    inner class GastoViewHolder(private val binding: ItemGastoBinding) : RecyclerView.ViewHolder(binding.root) {
        val descripcion = binding.descripcion
        val monto = binding.monto
        val tipo = binding.tipo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GastoViewHolder {
        val binding = ItemGastoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GastoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GastoViewHolder, position: Int) {
        val gasto = gastos[position]
        holder.descripcion.text = gasto.descripcion
        holder.monto.text = "${gasto.monto} Bs"
        holder.tipo.text = gasto.tipo
    }

    override fun getItemCount() = gastos.size
}
