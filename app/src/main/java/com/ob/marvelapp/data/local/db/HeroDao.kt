package com.ob.marvelapp.data.local.db

import androidx.room.*
import com.ob.marvelapp.data.local.model.DBHero
import kotlinx.coroutines.flow.Flow

@Dao
interface HeroDao {

  @Query("SELECT * FROM DBHero")
  fun getHeroes(): Flow<List<DBHero>>

  @Query("SELECT * FROM DBHero WHERE id=:id")
  fun getHero(id: Int): Flow<DBHero?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertHeroes(dbHero: List<DBHero>)
}