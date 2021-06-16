package com.ob.marvelapp.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UIHero(val id: Int,
                  val name: String,
                  val description: String,
                  val thumbnail: String,
                  val comics: List<String>,
                  val stories: List<String>) : Parcelable
