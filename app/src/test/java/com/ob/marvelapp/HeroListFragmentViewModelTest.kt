package com.ob.marvelapp

import android.os.Parcelable
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
import com.ob.marvelapp.ui.screenstates.list.HeroListState
import com.ob.marvelapp.ui.viewmodels.list.HeroListViewModel
import com.ob.usecases.GetHeroes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HeroListFragmentViewModelTest {

  @get:Rule
  var mainCoroutineRule = CoroutinesMainDispatcherRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Mock
  lateinit var parcelizeState: Parcelable

  @Mock
  lateinit var getHeroes: GetHeroes

  @Mock
  lateinit var uiMapper: UIMapper

  @Mock
  lateinit var observer: Observer<HeroListState>

  @InjectMocks
  private lateinit var viewModel: HeroListViewModel

  private var hero1 =
    Hero(id = 1234, name = "Thor", description = "Son of odin", thumbnail = "http://simplethumbnailurl.jpg", comics = listOf(), stories = listOf())
  private var hero2 =
    Hero(id = 4321, name = "Thor", description = "Son of odin", thumbnail = "http://simplethumbnailurl.jpg", comics = listOf(), stories = listOf())
  private var hero3 =
    Hero(id = 5678, name = "Thor", description = "Son of odin", thumbnail = "http://simplethumbnailurl.jpg", comics = listOf(), stories = listOf())

  private var heroList = listOf(hero1, hero2, hero3)

  @Test
  fun testIsLoadingState() {
    mainCoroutineRule.testDispatcher.runBlockingTest {

      viewModel.heroesStateList.observeForever(observer)

      val mainFlow = flow {
        emit(Either.Right(heroList))
      }

      whenever(getHeroes.invoke(any())).thenReturn(mainFlow)

      viewModel.getHeroes()

      verify(observer).onChanged(refEq(HeroListState.IsLoading(true)))

    }
  }

  @Test
  fun testShowItemsState() {

    mainCoroutineRule.testDispatcher.runBlockingTest {

      viewModel.heroesStateList.observeForever(observer)

      val heroUI1 = UIHero(id = 1234,
        name = "Thor",
        description = "Son of odin",
        thumbnail = "http://simplethumbnailurl.jpg",
        comics = listOf(),
        stories = listOf())
      val heroUI2 = UIHero(id = 4321,
        name = "Thor",
        description = "Son of odin",
        thumbnail = "http://simplethumbnailurl.jpg",
        comics = listOf(),
        stories = listOf())
      val heroUI3 = UIHero(id = 5678,
        name = "Thor",
        description = "Son of odin",
        thumbnail = "http://simplethumbnailurl.jpg",
        comics = listOf(),
        stories = listOf())

      val expected = listOf(heroUI1, heroUI2, heroUI3)

      val mainFlow = flow {
        emit(Either.Right(heroList))
      }

      whenever(getHeroes.invoke(any())).thenReturn(mainFlow)
      whenever(uiMapper.convertHeroesToUIHeroes(any())).thenReturn(expected)

      viewModel.getHeroes()

      verify(observer).onChanged(refEq(HeroListState.ShowItems(expected)))
    }

  }

  @Test
  fun showErrorState() {
    mainCoroutineRule.testDispatcher.runBlockingTest {

      viewModel.heroesStateList.observeForever(observer)

      val error = RetrofitService.ParsingError

      val mainFlow = flow {
        emit(Either.Left(error))
      }

      whenever(getHeroes.invoke(any())).thenReturn(mainFlow)
      viewModel.getHeroes()
      verify(observer).onChanged(refEq(HeroListState.Error(error)))
    }
  }
}