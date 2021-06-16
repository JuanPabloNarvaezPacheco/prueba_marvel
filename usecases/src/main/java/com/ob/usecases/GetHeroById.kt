package com.ob.usecases

import com.ob.data.MarvelRepository
import com.ob.domain.*
import kotlinx.coroutines.flow.Flow

class GetHeroById(private val marvelRepository: MarvelRepository) : UseCase<GetHeroById.Params, Hero>() {

  override suspend fun execute(params: Params): Flow<Either<Failure, Hero>> = marvelRepository.getHero(params.id)

  class Params(val id: Int)
}