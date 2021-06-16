package com.ob.marvelapp.di

import androidx.lifecycle.ViewModelProvider
import com.ob.marvelapp.ui.factory.ViewModelFactory
import dagger.*

@Module
abstract class ViewModelFactoryModule {

  @Binds
  abstract fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}