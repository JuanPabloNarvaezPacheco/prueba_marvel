package com.ob.data.datasources

import com.ob.domain.*

interface RemoteDataSource {
  suspend fun getHeroes(timeStamp: String, apiKey: String, hash: String): Either<Failure, List<Hero>>
}