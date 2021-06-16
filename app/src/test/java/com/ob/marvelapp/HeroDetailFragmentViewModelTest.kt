package com.ob.marvelapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.refEq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ob.domain.*
import com.ob.marvelapp.data.remote.service.RetrofitService
import com.ob.marvelapp.helpers.CoroutinesMainDispatcherRule
import com.ob.marvelapp.ui.UIMapper
import com.ob.marvelapp.ui.model.UIHero
import com.ob.marvelapp.ui.screenstates.detail.HeroDetailState
import com.ob.marvelapp.ui.viewmodels.detail.HeroDetailViewModel
import com.ob.usecases.GetHeroById
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HeroDetailFragmentViewModelTest {

  @get:Rule
  var mainCoroutineRule = CoroutinesMainDispatcherRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Mock
  lateinit var getHero: GetHeroById

  @Mock
  lateinit var uiMapper: UIMapper

  @Mock
  lateinit var observer: Observer<HeroDetailState>

  @InjectMocks
  private lateinit var viewModel: HeroDetailViewModel

  private var hero1 =
    Hero(id = 1234, name = "Thor", description = "Son of odin", thumbnail = "http://simplethumbnailurl.jpg", comics = listOf(), stories = listOf())

  @Test
  fun testIsLoadingState() {

    mainCoroutineRule.testDispatcher.runBlockingTest {

      viewModel.heroState.observeForever(observer)

      val expected = UIHero(id = 1234,
        name = "Thor",
        description = "Son of odin",
        thumbnail = "http://simplethumbnailurl.jpg",
        comics = listOf(),
        stories = listOf())

      val mainFlow = flow {
        emit(Either.Right(hero1))
      }

      whenever(getHero.invoke(any())).thenReturn(mainFlow)
      whenever(uiMapper.convertHeroToUIHero(any())).thenReturn(expected)

      viewModel.getHero(anyInt())

      verify(observer).onChanged(refEq(HeroDetailState.IsLoading))
    }
  }

  @Test
  fun testShowItemsState() {

    mainCoroutineRule.testDispatcher.runBlockingTest {

      viewModel.heroState.observeForever(observer)

      val expected = UIHero(id = 1234,
        name = "Thor",
        description = "Son of odin",
        thumbnail = "http://simplethumbnailurl.jpg",
        comics = listOf(),
        stories = listOf())

      val mainFlow = flow {
        emit(Either.Right(hero1))
      }

      whenever(getHero.invoke(any())).thenReturn(mainFlow)
      whenever(uiMapper.convertHeroToUIHero(any())).thenReturn(expected)

      viewModel.getHero(anyInt())

      verify(observer).onChanged(refEq(HeroDetailState.ShowItem(expected)))
    }

  }

  @Test
  fun showErrorState() {
    mainCoroutineRule.testDispatcher.runBlockingTest {

      viewModel.heroState.observeForever(observer)

      val error = RetrofitService.ParsingError

      val mainFlow = flow {
        emit(Either.Left(error))
      }

      whenever(getHero.invoke(any())).thenReturn(mainFlow)
      viewModel.getHero(anyInt())
      verify(observer).onChanged(refEq(HeroDetailState.Error(error)))
    }
  }
}