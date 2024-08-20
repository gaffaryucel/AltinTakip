package com.example.altntakip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.altntakip.databinding.ItemPriceBinding
import com.example.altntakip.model.TimeSeriesData

class PriceAdapter : RecyclerView.Adapter<PriceAdapter.PriceViewHolder>() {

    private val items = mutableListOf<Pair<String, TimeSeriesData>>()

    fun submitList(list: List<Pair<String, TimeSeriesData>>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        val binding = ItemPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PriceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        val (timestamp, data) = items[position]
        holder.bind(timestamp, data)
    }

    override fun getItemCount(): Int = items.size

    class PriceViewHolder(private val binding: ItemPriceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(timestamp: String, data: TimeSeriesData) {
            binding.textViewTimestamp.text = timestamp
            binding.textViewOpen.text = "Open: ${data.open}"
            binding.textViewHigh.text = "High: ${data.high}"
            binding.textViewLow.text = "Low: ${data.low}"
            binding.textViewClose.text = "Close: ${data.close}"
            binding.textViewVolume.text = "Volume: ${data.volume}"
        }
    }
}
