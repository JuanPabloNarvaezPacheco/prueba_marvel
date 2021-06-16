package com.ob.marvelapp.ui.viewmodels.list

import android.os.Parcelable
import androidx.lifecycle.*
import com.ob.domain.*
import com.ob.marvelapp.di.PerFragment
import com.ob.marvelapp.ui.UIMapper
import com.ob.marvelapp.ui.events.Event
import com.ob.marvelapp.ui.screenstates.list.HeroListState
import com.ob.marvelapp.ui.screenstates.navigation.NavigationEvent
import com.ob.usecases.GetHeroes
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@PerFragment
class HeroListViewModel @Inject constructor(private val getHeroes: GetHeroes, private val uiMapper: UIMapper, var state: Parcelable?) : ViewModel() {

  private var _heroesStateList = MutableLiveData<HeroListState>()
  private val _navigation = MutableLiveData<Event<NavigationEvent>>()

  val heroesStateList: LiveData<HeroListState> get() = _heroesStateList

  val navigation: LiveData<Event<NavigationEvent>> get() = _navigation

  fun navigateToDetail(navEvent: NavigationEvent) {
    _navigation.value = Event(navEvent)
  }

  fun getHeroes(force: Boolean = false) {
    _heroesStateList.value = HeroListState.IsLoading(true)
    viewModelScope.launch(Dispatchers.Main) {
      getHeroes(GetHeroes.Params(force)).collect {
        _heroesStateList.value = HeroListState.IsLoading(false)
        it.fold(::handleFailure, ::handleResponse)
      }
    }
  }

  private fun handleResponse(heroList: List<Hero>) {
    _heroesStateList.value = HeroListState.ShowItems(uiMapper.convertHeroesToUIHeroes(heroList))
  }

  private fun handleFailure(failure: Failure) {
    _heroesStateList.value = HeroListState.Error(failure)
  }
}