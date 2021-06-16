package com.ob.marvelapp.data.remote

import com.ob.domain.*
import com.ob.marvelapp.data.remote.model.EntityHero

class RemoteMapper {

  fun convertEntityHeroToDomain(entityHero: EntityHero): Either<Failure, Hero> = Either.Right(with(entityHero) {
    Hero(id = id,
      name = name,
      description = description,
      thumbnail = thumbnail.path + "." + thumbnail.extension,
      comics = comics.items.map { it.name },
      stories = stories.items.map { it.name })
  })
}