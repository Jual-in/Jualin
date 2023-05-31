package com.jualin.apps.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jualin.apps.data.local.entity.Service
import com.jualin.apps.databinding.ItemBusinessProductBinding
import com.jualin.apps.databinding.ItemBusinessServiceBinding

class GridListAdapter(
    private val list: List<Service>
) : BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemBusinessServiceBinding

        if (convertView==null) {
            binding = ItemBusinessServiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemBusinessServiceBinding
        }

        val item = getItem(position) as Service

        binding.apply {
            tvBusinessServiceName.text = item.name
            tvBusinessServicePrice.text = item.price
        }

        return binding.root
    }
}