package com.example.bugs_bytes_30

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bugs_bytes_30.databinding.ItemIngresosBinding

class RecyclerIngresosAdapter :
    RecyclerView.Adapter<RecyclerIngresosAdapter.ViewHolder>() {

    private val listaDatos = mutableListOf<Ahorros>()
    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context = parent.context
        return ViewHolder(
            ItemIngresosBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(listaDatos[position])
    }

    override fun getItemCount(): Int = listaDatos.size

    inner class ViewHolder(private val binding: ItemIngresosBinding) :
        RecyclerView.ViewHolder(binding.root)  {
        fun binding(data: Ahorros) {
            binding.nombreIngreso.text = data.nombre
            binding.tipoIngreso.text = data.tipo
            binding.montoIngreso.text = data.monto
        }
    }

    fun addDataToList(list: List<Ahorros>) {
        listaDatos.clear()
        listaDatos.addAll(list)
    }


}