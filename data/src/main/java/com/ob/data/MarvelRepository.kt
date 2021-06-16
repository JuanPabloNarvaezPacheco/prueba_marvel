package com.ob.data

import com.ob.data.datasources.*
import com.ob.domain.*
import com.ob.domain.Constants.Server.API_KEY
import com.ob.domain.Constants.Server.PRIVATE_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

interface MarvelRepository {
  suspend fun getHeroes(fromNetwork: Boolean = true): Flow<Either<Failure, List<Hero>>>
  suspend fun getHero(id: Int): Flow<Either<Failure, Hero>>
}

class MarvelRepositoryImplementation(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource) :
  MarvelRepository {

  @ExperimentalCoroutinesApi
  override suspend fun getHeroes(fromNetwork: Boolean): Flow<Either<Failure, List<Hero>>> {
    val timeStampString = (System.currentTimeMillis() / 1000L).toString()
    val hash = Utils.md5(timeStampString + PRIVATE_KEY + API_KEY)

    return when (fromNetwork) {
      true -> networkDataSourceSearchHeroes(timeStampString, API_KEY, hash)
      false -> localDataSource.getHeroes().distinctUntilChanged().flatMapLatest {
          networkDataSourceSearchHeroes(timeStampString, API_KEY, hash)
        }
    }
  }

  override suspend fun getHero(id: Int): Flow<Either<Failure, Hero>> = localDataSource.getHero(id)

  private suspend fun networkDataSourceSearchHeroes(timeStamp: String, apiKey: String, hash: String): Flow<Either<Failure, List<Hero>>> {
    remoteDataSource.getHeroes(timeStamp, apiKey, hash).flatMapToRight { heroes ->
      localDataSource.insertHeroes(heroes)
      Either.Right(heroes)
    }
    return localDataSource.getHeroes()
  }
}