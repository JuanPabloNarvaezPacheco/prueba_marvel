package com.ob.marvelapp.data.local.model

import androidx.room.*
import com.ob.marvelapp.data.local.Converters

@Entity(primaryKeys = ["id"])
@TypeConverters(value = [Converters::class])
class DBHero(val id: Int, val name: String, val description: String, val thumbnail: String, val comics: List<String>, val stories: List<String>)