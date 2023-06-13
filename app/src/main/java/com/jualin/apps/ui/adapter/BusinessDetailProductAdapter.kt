package com.jualin.apps.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jualin.apps.data.local.entity.Product
import com.jualin.apps.databinding.ItemBusinessProductBinding
import com.jualin.apps.utils.StringUtils

class BusinessDetailProductAdapter(
    private val items: List<Product>
) : RecyclerView.Adapter<BusinessDetailProductAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBusinessProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    inner class ViewHolder(
        private val binding: ItemBusinessProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.apply {
                tvProductName.text = item.name
                tvProductPrice.text = StringUtils.formatCurrency(item.price.toString())

                Glide.with(itemView.context)
                    .load(item.photoUrl)
                    .into(ivProduct)
            }
        }

    }
}