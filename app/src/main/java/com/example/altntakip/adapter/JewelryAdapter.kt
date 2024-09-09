package com.example.altntakip.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.altntakip.R
import com.example.altntakip.databinding.ItemJewelryBinding
import com.example.altntakip.model.JewelryModel
import com.example.altntakip.view.HomeFragmentDirections


class JewelryAdapter : RecyclerView.Adapter<JewelryAdapter.JewelryViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<JewelryModel>() {
        override fun areItemsTheSame(oldItem: JewelryModel, newItem: JewelryModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: JewelryModel, newItem: JewelryModel): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var jewelryList: List<JewelryModel>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    inner class JewelryViewHolder(val binding: ItemJewelryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JewelryViewHolder {
        val binding = ItemJewelryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JewelryViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: JewelryViewHolder, position: Int) {
        val jewelry = jewelryList[position]
            Glide.with(holder.itemView.context).load(jewelry.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.binding.productImage)

            holder.binding.productTitle.text = jewelry.name ?: "Beautiful Jewelry"
            holder.binding.productPrice.text = "₹${jewelry.price?.toInt() ?: 1000}"
            holder.binding.productCategory.text = jewelry.category + "| Kadane" ?: "Unknown Category"

        holder.itemView.setOnClickListener{
            jewelry.id?.let {id->
                val action = HomeFragmentDirections.actionHomeFragmentToJewelryDetailsFragment(id)
                Navigation.findNavController(it).navigate(action)
            }
        }
            // If there's a discount field in the JewelryModel, uncomment and use the following line
            // holder.binding.discount.text = "${jewelry.discountPercentage ?: 0}% OFF"
    }

    override fun getItemCount(): Int {
        return jewelryList.size
    }
}
