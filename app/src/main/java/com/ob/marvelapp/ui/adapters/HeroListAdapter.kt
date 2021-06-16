package com.ob.marvelapp.ui.adapters

import android.view.*
import androidx.recyclerview.widget.*
import com.ob.marvelapp.databinding.ItemHeroBinding
import com.ob.marvelapp.extensions.loadImageUrl
import com.ob.marvelapp.ui.model.UIHero

class HeroListAdapter(val onItemClickListener: OnItemClickListener) :
  ListAdapter<UIHero, HeroListAdapter.ViewHolder>(object : DiffUtil.ItemCallback<UIHero>() {
    override fun areItemsTheSame(oldItem: UIHero, newItem: UIHero): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UIHero, newItem: UIHero): Boolean = oldItem == newItem
  }) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(ItemHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.onBind(getItem(position))
  }

  inner class ViewHolder(private val itemBinding: ItemHeroBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun onBind(item: UIHero) {
      itemBinding.txtHeroName.text = item.name
      itemBinding.imgHero.loadImageUrl(item.thumbnail)
      itemBinding.root.setOnClickListener {
        onItemClickListener.onItemClick(item)
      }
    }
  }

  interface OnItemClickListener {
    fun onItemClick(uiHero: UIHero)
  }
}