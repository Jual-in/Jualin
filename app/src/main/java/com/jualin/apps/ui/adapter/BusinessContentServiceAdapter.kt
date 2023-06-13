package com.jualin.apps.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jualin.apps.data.local.entity.Service
import com.jualin.apps.databinding.ItemAddContentBinding
import com.jualin.apps.databinding.ItemServiceContentBinding
import com.jualin.apps.utils.StringUtils

class BusinessContentServiceAdapter(
    private var items: List<Service>,
    private val listener: OnBusinessClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateItems(newItems: List<Service>) {
        val diffCallback = ItemDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position==items.size) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            0 -> {
                val binding = ItemServiceContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ItemViewHolder(binding)
            }

            else -> {
                val binding = ItemAddContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return AddViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = items.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(items[position])
            is AddViewHolder -> holder.bind()
        }
    }

    inner class ItemViewHolder(private val binding: ItemServiceContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Service) {
            binding.apply {
                tvServiceName.text = item.name
                tvServicePrice.text = StringUtils.formatCurrency(item.price.toString())
                tvServiceDiscount.text = item.discount.toString()

                btnRemove.setOnClickListener { listener.onBusinessServiceRemoveClick(item.id) }
                itemView.setOnClickListener { listener.onBusinessServiceEditClick(item) }
            }
        }
    }

    inner class AddViewHolder(private val binding: ItemAddContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.btnAddItem.setOnClickListener { listener.onBusinessServiceAddClick() }
        }
    }

    private inner class ItemDiffCallback(
        private val oldItems: List<Service>,
        private val newItems: List<Service>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldItems.size
        }

        override fun getNewListSize(): Int {
            return newItems.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].id==newItems[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition]==newItems[newItemPosition]
        }
    }


    interface OnBusinessClickListener {
        fun onBusinessServiceRemoveClick(serviceId: Int)
        fun onBusinessServiceAddClick()
        fun onBusinessServiceEditClick(service: Service)
    }
}