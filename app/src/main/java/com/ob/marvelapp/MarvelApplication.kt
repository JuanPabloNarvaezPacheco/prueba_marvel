package com.ob.marvelapp

import android.app.Application
import com.ob.marvelapp.di.*

class MarvelApplication : Application() {

  lateinit var component: AppComponent
    private set

  override fun onCreate() {
    component = getAppComponent()
    super.onCreate()
  }

  private fun getAppComponent(): AppComponent = DaggerAppComponent.factory().create(this)
}