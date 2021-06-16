package com.ob.marvelapp.di

import android.os.Parcelable
import com.ob.marvelapp.ui.fragments.list.HeroListFragment
import dagger.*

@PerFragment
@Subcomponent(modules = [HeroListModule::class])
interface HeroListSubComponent {

  fun inject(fragment: HeroListFragment)

  @Subcomponent.Factory
  interface Factory {
    fun create(@BindsInstance state: Parcelable?): HeroListSubComponent
  }
}