package com.ob.usecases

import com.nhaarman.mockitokotlin2.verify
import com.ob.data.MarvelRepository
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetHeroesTest {

  @Mock
  lateinit var repository: MarvelRepository

  lateinit var useCase: GetHeroes

  @Before
  fun setUp() {
    useCase = GetHeroes(repository)
  }

  @Test
  fun useCaseCallRepositoryWhenIsCalledWithTrueParam() {

    runBlocking {
      useCase(GetHeroes.Params(false))
      verify(repository).getHeroes(false)
    }
  }

  @Test
  fun useCaseCallRepositoryWhenIsCalledFalseParam() {

    runBlocking {
      useCase(GetHeroes.Params(true))
      verify(repository).getHeroes(true)
    }
  }
}