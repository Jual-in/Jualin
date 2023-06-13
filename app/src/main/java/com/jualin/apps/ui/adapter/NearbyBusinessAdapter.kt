package com.jualin.apps.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jualin.apps.data.local.entity.Business
import com.jualin.apps.databinding.ItemNearbyBusinessBinding

class NearbyBusinessAdapter(
    private val items: List<Business>,
    private val listener: (Business) -> Unit
) : RecyclerView.Adapter<NearbyBusinessAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNearbyBusinessBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(
        private val binding: ItemNearbyBusinessBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Business) {
            binding.apply {
                tvNamaUmkm.text = item.name
                tvDeskripsi.text = item.description
                tvKategori.text = item.category
                tvNoTelp.text = item.phone
            }
            itemView.setOnClickListener { listener(item) }
        }
    }
}