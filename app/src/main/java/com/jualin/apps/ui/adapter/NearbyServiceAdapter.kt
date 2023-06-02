package com.jualin.apps.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jualin.apps.R
import com.jualin.apps.data.local.entity.Service
import com.jualin.apps.databinding.ItemJasaBinding

class NearbyServiceAdapter(
    private val list: List<Service>,
    private val context: Context
) : RecyclerView.Adapter<NearbyServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemJasaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(
        private val binding: ItemJasaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(service: Service) {
            binding.apply {
                binding.tvNamaJasa.text = service.name
                binding.tvServicePrice.text = context.getString(R.string.rupiah, service.price)
            }
        }

    }
}