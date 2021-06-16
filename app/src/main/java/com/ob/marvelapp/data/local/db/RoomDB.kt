package com.ob.marvelapp.data.local.db

import com.ob.data.datasources.LocalDataSource
import com.ob.domain.*
import com.ob.marvelapp.data.local.DBMapper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class RoomDB(private val dbMapper: DBMapper, database: DataBase) : LocalDataSource {

  private val dao: HeroDao = database.heroDao()

  object EmptyList : Failure.CustomFailure()

  @ExperimentalCoroutinesApi
  override suspend fun getHeroes(): Flow<Either<Failure, List<Hero>>> = withContext(Dispatchers.IO) {
    dao.getHeroes().distinctUntilChanged().mapLatest { list ->
      if (list.isEmpty()) Either.Left(EmptyList)
      else Either.Right(dbMapper.convertDBHeroesToDomain(list))
    }
  }

  @ExperimentalCoroutinesApi
  override suspend fun getHero(id: Int): Flow<Either<Failure, Hero>> = withContext(Dispatchers.IO) {
    dao.getHero(id).distinctUntilChanged().mapLatest { item ->
      if (item == null) Either.Left(Failure.NullResult)
      else Either.Right(dbMapper.convertDBHeroToDomain(item))
    }
  }

  override suspend fun insertHeroes(heroes: List<Hero>) {
    withContext(Dispatchers.IO) {
      dao.insertHeroes(dbMapper.convertHeroesToDBHeroes(heroes))
    }
  }
}