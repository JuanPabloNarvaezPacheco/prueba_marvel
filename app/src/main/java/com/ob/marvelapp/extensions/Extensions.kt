package com.ob.marvelapp.extensions

import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ob.marvelapp.MarvelApplication

val Fragment.application: MarvelApplication get() = (requireActivity().application as MarvelApplication)

fun ImageView.loadImageUrl(src: String?) {
  src?.let {
    Glide.with(context).load(it).circleCrop().into(this)
  } ?: kotlin.run {
    this.isVisible = false
  }
}