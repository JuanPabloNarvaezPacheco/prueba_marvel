package com.ob.marvelapp.ui.factory

import androidx.lifecycle.*
import javax.inject.*

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory @Inject constructor(private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>) :
  ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    var creator: Provider<out ViewModel>? = creators[modelClass]
    if (creator == null) {
      for ((key, value) in creators) if (modelClass.isAssignableFrom(key)) {
        creator = value
        break
      }
    }
    if (creator == null) throw IllegalArgumentException("Unknown model class: $modelClass")
    try {
      @Suppress("UNCHECKED_CAST") return creator.get() as T
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }
}