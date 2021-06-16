package com.ob.usecases

import com.nhaarman.mockitokotlin2.verify
import com.ob.data.MarvelRepository
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetHeroByIdTest {

  @Mock
  lateinit var repository: MarvelRepository

  lateinit var useCase: GetHeroById

  @Before
  fun setUp() {
    useCase = GetHeroById(repository)
  }

  @Test
  fun useCaseCallRepositoryWhenIsCalled() {

    runBlocking {
      useCase(GetHeroById.Params(1))
      verify(repository).getHero(1)
    }
  }
}