package com.ob.marvelapp.ui.screenstates.list

import com.ob.domain.Failure
import com.ob.marvelapp.ui.model.UIHero

sealed class HeroListState {
  class Error(val failure: Failure) : HeroListState()
  class ShowItems(val heroList: List<UIHero>) : HeroListState()
  class IsLoading(val isLoading: Boolean) : HeroListState()
}