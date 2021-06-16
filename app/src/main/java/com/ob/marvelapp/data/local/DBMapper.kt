package com.ob.marvelapp.data.local

import com.ob.domain.Hero
import com.ob.marvelapp.data.local.model.DBHero

class DBMapper {

  fun convertDBHeroesToDomain(heroes: List<DBHero>): List<Hero> = heroes.map { entity ->
    convertDBHeroToDomain(entity)
  }

  fun convertDBHeroToDomain(hero: DBHero): Hero = with(hero) {
    Hero(id = id, name = name, description = description, thumbnail = thumbnail, comics = comics, stories = stories)
  }

  fun convertHeroesToDBHeroes(heroes: List<Hero>) = heroes.map { domain ->
    convertHeroToDBHero(domain)
  }

  private fun convertHeroToDBHero(hero: Hero) = with(hero) {
    DBHero(id = id, name = name, description = description, thumbnail = thumbnail, comics = comics, stories = stories)
  }
}