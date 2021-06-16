package com.ob.marvelapp.ui.screenstates.detail

import com.ob.domain.Failure
import com.ob.marvelapp.ui.model.UIHero

sealed class HeroDetailState {
  class Error(val failure: Failure) : HeroDetailState()
  class ShowItem(val item: UIHero) : HeroDetailState()
  object IsLoading : HeroDetailState()
}