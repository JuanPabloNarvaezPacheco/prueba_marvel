package com.ob.data.datasources

import com.ob.domain.*
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
  suspend fun getHeroes(): Flow<Either<Failure, List<Hero>>>
  suspend fun getHero(id: Int): Flow<Either<Failure, Hero>>
  suspend fun insertHeroes(heroes: List<Hero>)
}