package com.ob.usecases

import com.ob.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

abstract class UseCase<Params, Return> {

  suspend operator fun invoke(params: Params): Flow<Either<Failure, Return>> = execute(params).flowOn(Dispatchers.IO)

  protected abstract suspend fun execute(params: Params): Flow<Either<Failure, Return>>
}