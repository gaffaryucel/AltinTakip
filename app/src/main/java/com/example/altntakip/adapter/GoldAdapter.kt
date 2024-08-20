package com.example.altntakip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.altntakip.R
import com.example.altntakip.databinding.ItemGoldBinding
import com.example.altntakip.model.GoldInfo

class GoldAdapter : ListAdapter<GoldInfo, GoldAdapter.GoldViewHolder>(GoldInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoldViewHolder {
        val binding = ItemGoldBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoldViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoldViewHolder, position: Int) {
        val goldInfo = getItem(position)
        holder.bind(goldInfo)
    }

    class GoldViewHolder(private val binding: ItemGoldBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(goldInfo: GoldInfo) {
            binding.goldInfo = goldInfo
            // Renklerin dinamik olarak güncellenmesini sağlayın
            val color = if (goldInfo.priceChangeDirection == "up") R.color.green else R.color.red
            binding.priceChange.setTextColor(ContextCompat.getColor(binding.root.context, color))
            binding.executePendingBindings()
        }
    }

    class GoldInfoDiffCallback : DiffUtil.ItemCallback<GoldInfo>() {
        override fun areItemsTheSame(oldItem: GoldInfo, newItem: GoldInfo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GoldInfo, newItem: GoldInfo): Boolean {
            return oldItem == newItem
        }
    }
}

