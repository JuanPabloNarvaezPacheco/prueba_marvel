package com.ob.marvelapp.di

import androidx.lifecycle.ViewModel
import com.ob.marvelapp.ui.viewmodels.list.HeroListViewModel
import dagger.*
import dagger.multibindings.IntoMap

@Module
abstract class HeroListModule {
  @Binds
  @IntoMap
  @ViewModelKey(HeroListViewModel::class)
  abstract fun provideViewModel(viewModel: HeroListViewModel): ViewModel
}