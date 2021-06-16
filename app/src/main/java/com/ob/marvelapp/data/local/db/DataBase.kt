package com.ob.marvelapp.data.local.db

import androidx.room.*
import com.ob.marvelapp.data.local.model.DBHero

@Database(entities = [DBHero::class], version = 1)
abstract class DataBase : RoomDatabase() {
  abstract fun heroDao(): HeroDao
}