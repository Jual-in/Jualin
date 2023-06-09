package com.jualin.apps.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jualin.apps.R
import com.jualin.apps.data.local.entity.Business
import com.jualin.apps.databinding.ItemRecommendedBusinessBinding

class RecommendedBusinessAdapter(
    private val items: List<Business>,
    private val listener: OnBusinessClickListener
) :
    RecyclerView.Adapter<RecommendedBusinessAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecommendedBusinessBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemRecommendedBusinessBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Business) {
            binding.apply {
                tvBusinessName.text = item.name
                tvBusinessDesc.text = item.description
                Glide.with(itemView.context)
                    .load(R.drawable.default_business)
                    .into(ivBusiness)
            }
            itemView.setOnClickListener {
                listener.onBusinessClick(item)
            }
        }

    }

    interface OnBusinessClickListener {
        fun onBusinessClick(business: Business)
    }
}