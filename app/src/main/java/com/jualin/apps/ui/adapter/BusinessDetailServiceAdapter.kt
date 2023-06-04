package com.jualin.apps.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jualin.apps.data.local.entity.Service
import com.jualin.apps.databinding.ItemBusinessServiceBinding
import com.jualin.apps.utils.StringUtils

class BusinessDetailServiceAdapter(
    private val list: List<Service>
) : RecyclerView.Adapter<BusinessDetailServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBusinessServiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(
        private val binding: ItemBusinessServiceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Service) {
            binding.apply {
                tvBusinessServiceName.text = item.name
                tvBusinessServicePrice.text = StringUtils.formatCurrency(item.price)
            }
        }

    }

}