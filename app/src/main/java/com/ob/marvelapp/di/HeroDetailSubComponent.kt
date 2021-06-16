package com.ob.marvelapp.di

import com.ob.marvelapp.ui.fragments.detail.HeroDetailFragment
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [HeroDetailModule::class])
interface HeroDetailSubComponent {

  fun inject(fragment: HeroDetailFragment)

  @Subcomponent.Factory
  interface Factory {
    fun create(): HeroDetailSubComponent
  }
}