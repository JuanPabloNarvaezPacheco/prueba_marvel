package com.ob.marvelapp.di

import android.app.Application
import com.ob.marvelapp.ui.activities.MainActivity
import dagger.*
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AppSubComponents::class, UseCasesModule::class, ViewModelFactoryModule::class])
interface AppComponent {

  val heroListSubComponent: HeroListSubComponent.Factory
  val heroDetailSubComponent: HeroDetailSubComponent.Factory

  fun inject(activity: MainActivity)

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Application): AppComponent
  }
}