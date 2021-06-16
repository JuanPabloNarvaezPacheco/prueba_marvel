package com.ob.marvelapp.ui.screenstates.navigation

sealed class NavigationEvent {
  class ToHeroDetail(val arg: Int) : NavigationEvent()
}