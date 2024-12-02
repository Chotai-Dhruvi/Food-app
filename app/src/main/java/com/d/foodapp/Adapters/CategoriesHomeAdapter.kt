package com.d.foodapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d.foodapp.databinding.RowCategoriesItemBinding
import com.d.foodapp.model.Category

class CategoriesHomeAdapter(): RecyclerView.Adapter<CategoriesHomeAdapter.ViewHolder>() {

    lateinit var onCategoryItemClick :((Category) -> Unit)

    private val diffUtil = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)

    class ViewHolder(val binding : RowCategoriesItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowCategoriesItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(data.strCategoryThumb)
            .into(holder.binding.ivCategoriesItem)

        holder.binding.tvTitleCategoriesItem.text = data.strCategory

        holder.itemView.setOnClickListener{
            onCategoryItemClick.invoke(data)
        }
    }
}