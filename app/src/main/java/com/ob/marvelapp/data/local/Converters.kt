package com.ob.marvelapp.data.local

import androidx.room.TypeConverter
import com.squareup.moshi.*

class Converters {

  private val moshi = Moshi.Builder().build()

  @TypeConverter
  fun fromString(value: String): List<String> =
    moshi.adapter<ArrayList<String>>(Types.newParameterizedType(List::class.java, String::class.java)).fromJson(value) ?: emptyList()

  @TypeConverter
  fun fromArrayList(list: List<String>): String =
    moshi.adapter<List<String>>(Types.newParameterizedType(List::class.java, String::class.java)).toJson(list)
}