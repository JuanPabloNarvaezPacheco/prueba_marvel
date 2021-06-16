package com.ob.marvelapp.ui.viewmodels.detail

import androidx.lifecycle.*
import com.ob.domain.*
import com.ob.marvelapp.ui.UIMapper
import com.ob.marvelapp.ui.screenstates.detail.HeroDetailState
import com.ob.usecases.GetHeroById
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class HeroDetailViewModel @Inject constructor(private val getHero: GetHeroById, private val uiMapper: UIMapper) : ViewModel() {

  private var _heroDetailState = MutableLiveData<HeroDetailState>()

  val heroState: LiveData<HeroDetailState> get() = _heroDetailState

  fun getHero(id: Int) {
    viewModelScope.launch {
      getHero(GetHeroById.Params(id)).onStart { _heroDetailState.value = HeroDetailState.IsLoading }.collect {
        _heroDetailState.value = HeroDetailState.IsLoading
        it.fold(::handleFailure, ::handleResponse)
      }
    }
  }

  private fun handleResponse(hero: Hero) {
    _heroDetailState.value = HeroDetailState.ShowItem(uiMapper.convertHeroToUIHero(hero))
  }

  private fun handleFailure(failure: Failure) {
    _heroDetailState.value = HeroDetailState.Error(failure)
  }
}