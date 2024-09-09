package com.example.altntakip.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.altntakip.databinding.ItemJewelryBinding
import com.example.altntakip.databinding.ItemJewelryImagesBinding

class JewelryImageAdapter : RecyclerView.Adapter<JewelryImageAdapter.JewelryImageViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var imageUriList: List<Uri>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    inner class JewelryImageViewHolder(val binding: ItemJewelryImagesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JewelryImageViewHolder {
        val binding = ItemJewelryImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JewelryImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JewelryImageViewHolder, position: Int) {
        val imageUri = imageUriList[position]
        holder.binding.ivOtherJewelryImage.setImageURI(imageUri)
    }

    override fun getItemCount(): Int {
        return imageUriList.size
    }
}
