package com.ob.marvelapp.ui

import com.ob.domain.Hero
import com.ob.marvelapp.ui.model.UIHero

class UIMapper {

  fun convertHeroesToUIHeroes(heroes: List<Hero>): List<UIHero> = heroes.map { domain ->
    convertHeroToUIHero(domain)
  }

  fun convertHeroToUIHero(hero: Hero): UIHero = with(hero) {
    UIHero(id = id, name = name, description = description, thumbnail = thumbnail, comics = comics, stories = stories)
  }
}