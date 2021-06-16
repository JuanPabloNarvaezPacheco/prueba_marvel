package com.ob.marvelapp.di

import androidx.lifecycle.ViewModel
import com.ob.marvelapp.ui.viewmodels.detail.HeroDetailViewModel
import dagger.*
import dagger.multibindings.IntoMap

@Module
abstract class HeroDetailModule {

  @Binds
  @IntoMap
  @ViewModelKey(HeroDetailViewModel::class)
  abstract fun provideViewModel(viewModel: HeroDetailViewModel): ViewModel
}