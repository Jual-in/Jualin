package com.jualin.apps.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jualin.apps.data.local.entity.Service
import com.jualin.apps.databinding.ItemJasaBinding
import com.jualin.apps.utils.StringUtils

class SearchServiceAdapter(
    private val list: List<Service>,
    private val listener: (businessId: Int) -> Unit
) : RecyclerView.Adapter<SearchServiceAdapter.ViewHolder>() {

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
                tvNamaJasa.text = service.name
                tvServicePrice.text = StringUtils.formatCurrency(service.price.toString())
                btnLihatToko.setOnClickListener { listener(service.businessId ?: 0) }
            }
        }

    }
}