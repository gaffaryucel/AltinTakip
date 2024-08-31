package com.example.altntakip.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.altntakip.R
import com.example.altntakip.databinding.ItemGoldBinding
import com.example.altntakip.model.CurrencyData
import com.example.altntakip.model.GoldInfo
import com.example.altntakip.util.CurrencyType
import com.example.altntakip.view.HomeFragmentDirections

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
            binding.currentPrice.setOnClickListener{
                val action = HomeFragmentDirections.actionHomeFragmentToAddJewelryFragment()
                Navigation.findNavController(it).navigate(action)
            }
            if (goldInfo.type.equals(CurrencyType.USD)){
                binding.ivCurrency.setImageResource(R.drawable.dollar)
                binding.purity.text = "Satış fiyatı : "+goldInfo.selling
                binding.tvType.text = "Döviz"
                binding.tvCurrencyName.text = "Amerikan Doları"
                binding.weight.text =  "Son Güncelleme : Bugün"
            }
            if (goldInfo.type.equals(CurrencyType.EUR)){
                binding.ivCurrency.setImageResource(R.drawable.euro)
                binding.purity.text = "Satış fiyatı : "+goldInfo.selling
                binding.tvType.text = "Döviz"
                binding.tvCurrencyName.text = "Euro GÜncel Kur"
                binding.weight.text =  "Son Güncelleme : Bugün"
            }
            if (goldInfo.type.equals(CurrencyType.GOLD)){
                binding.tvType.text = "Altın"
                binding.tvCurrencyName.text = "24 Ayar Gram Altın"
            }
            binding.goldInfo = goldInfo
            // Renklerin dinamik olarak güncellenmesini sağlayın
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

