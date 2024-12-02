package com.d.foodapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d.foodapp.databinding.RowOverPopularItemBinding
import com.d.foodapp.model.OverPopularItem

class OverPopularItemsAdapter(): RecyclerView.Adapter<OverPopularItemsAdapter.ViewHolder>() {

    lateinit var onOverItemClick: ((OverPopularItem) -> Unit)

    private val diffUtil = object : DiffUtil.ItemCallback<OverPopularItem>() {
        override fun areItemsTheSame(oldItem: OverPopularItem, newItem: OverPopularItem): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(
            oldItem: OverPopularItem,
            newItem: OverPopularItem
        ): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, diffUtil)
    class ViewHolder(val binding: RowOverPopularItemBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowOverPopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(data.strMealThumb)
            .into(holder.binding.ivOverPopularItem)

        holder.itemView.setOnClickListener {
            onOverItemClick.invoke(data)
        }
    }
}