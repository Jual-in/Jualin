package com.jualin.apps.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jualin.apps.data.local.entity.Product
import com.jualin.apps.databinding.ItemAddContentBinding
import com.jualin.apps.databinding.ItemProductContentBinding
import com.jualin.apps.utils.StringUtils

class BusinessContentProductAdapter(
    private var items: List<Product>,
    private val listener: OnBusinessClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateItems(newItems: List<Product>) {
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
                val binding = ItemProductContentBinding.inflate(
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

    inner class ItemViewHolder(private val binding: ItemProductContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.apply {
                tvProductName.text = item.name
                tvProductPrice.text = StringUtils.formatCurrency(item.price.toString())
                tvProductDiscount.text = item.discount.toString()

                Glide.with(itemView.context)
                    .load(item.photoUrl)
                    .into(ivProductImage)

                btnDeleteProduct.setOnClickListener { listener.onBusinessProductRemoveClick(item.id) }
                itemView.setOnClickListener { listener.onBusinessProductEditClick(item.id) }
            }
        }
    }

    inner class AddViewHolder(private val binding: ItemAddContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.btnAddItem.setOnClickListener { listener.onBusinessProductAddClick() }
        }
    }

    private inner class ItemDiffCallback(
        private val oldItems: List<Product>,
        private val newItems: List<Product>
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
        fun onBusinessProductRemoveClick(productId: Int)
        fun onBusinessProductAddClick()
        fun onBusinessProductEditClick(productId: Int)
    }
}