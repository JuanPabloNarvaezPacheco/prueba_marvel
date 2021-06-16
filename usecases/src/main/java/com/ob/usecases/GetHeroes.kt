package com.ob.usecases

import com.ob.data.MarvelRepository
import com.ob.domain.*
import kotlinx.coroutines.flow.Flow

class GetHeroes(private val marvelRepository: MarvelRepository) : UseCase<GetHeroes.Params, List<Hero>>() {

  override suspend fun execute(params: Params): Flow<Either<Failure, List<Hero>>> = marvelRepository.getHeroes(params.force)

  class Params(val force: Boolean = false)
}