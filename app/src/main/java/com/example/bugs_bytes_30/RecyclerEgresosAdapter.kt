package com.example.bugs_bytes_30

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bugs_bytes_30.databinding.ItemEgresosBinding

class RecyclerEgresosAdapter:
    RecyclerView.Adapter<RecyclerEgresosAdapter.ViewHolder>()  {


    private val listaDatos = mutableListOf<Gastos>()
    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context = parent.context
        return ViewHolder(
            ItemEgresosBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemEgresosBinding) :
        RecyclerView.ViewHolder(binding.root)  {
        fun binding(data: Gastos) {
            binding.nombreEgreso.text = data.nombre
            binding.tipoEgreso.text = data.tipo
            binding.montoEgreso.text = data.monto
        }
    }

    fun addDataToList(list: List<Gastos>) {
        listaDatos.clear()
        listaDatos.addAll(list)
    }





}