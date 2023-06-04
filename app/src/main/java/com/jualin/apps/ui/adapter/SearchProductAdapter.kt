package com.jualin.apps.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jualin.apps.data.local.entity.Product
import com.jualin.apps.databinding.ItemProductBinding
import com.jualin.apps.utils.StringUtils

class SearchProductAdapter(
    private val list: List<Product>,
) : RecyclerView.Adapter<SearchProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                tvProductName.text = product.name
                tvPrice.text = StringUtils.formatCurrency(product.price)

                Glide.with(itemView.context)
                    .load(product.photoUrl)
                    .into(ivProduct)
            }
        }
    }

}