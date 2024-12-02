package com.d.foodapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d.foodapp.databinding.RowFavoriteItemBinding
import com.d.foodapp.model.OverPopularItem

class CategoryFragmentAdapter (): RecyclerView.Adapter<CategoryFragmentAdapter.ViewHolder>() {
    private val diffUtil = object: DiffUtil.ItemCallback<OverPopularItem>(){
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
    class ViewHolder (val binding: RowFavoriteItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryFragmentAdapter.ViewHolder {
        return ViewHolder(RowFavoriteItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryFragmentAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(data.strMealThumb)
            .into(holder.binding.ivFavoriteItem)

        holder.binding.tvTitleFavoriteItem.text = data.strMeal
    }

    override fun getItemCount() = differ.currentList.size
}