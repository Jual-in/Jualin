package com.jualin.apps.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jualin.apps.data.remote.response.nearby.NearbyUmkmResponseItem
import com.jualin.apps.databinding.ItemNearbyUmkmBinding

class NearbyUmkmAdapter(
    private val items: List<NearbyUmkmResponseItem>
): RecyclerView.Adapter<NearbyUmkmAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNearbyUmkmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(
        private val binding: ItemNearbyUmkmBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NearbyUmkmResponseItem) {
            binding.apply {
                tvNamaUmkm.text = item.namaUsaha
                tvDeskripsi.text = item.deskripsi
                tvKategori.text = item.kategori
                tvNoTelp.text = item.noHp
                tvcat.text = "Kategori"
                tvDeskripsii.text = "Deskripsi"
                tvnotelp.text = "No Telp"
            }
        }
    }



}