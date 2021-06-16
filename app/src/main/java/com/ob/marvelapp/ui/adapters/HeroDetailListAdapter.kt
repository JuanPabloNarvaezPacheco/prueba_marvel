package com.ob.marvelapp.ui.adapters

import android.view.*
import androidx.recyclerview.widget.*
import com.ob.marvelapp.databinding.ItemHeroDetailBinding
import com.ob.marvelapp.ui.adapters.HeroDetailListAdapter.ViewHolder

class HeroDetailListAdapter : ListAdapter<String, ViewHolder>(object : DiffUtil.ItemCallback<String>() {
  override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

  override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(ItemHeroDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.onBind(getItem(position))
  }

  inner class ViewHolder(private val itemBinding: ItemHeroDetailBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun onBind(item: String) {
      itemBinding.txtGroupName.text = item
    }
  }
}

