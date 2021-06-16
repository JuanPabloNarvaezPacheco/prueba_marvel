package com.ob.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ob.data.datasources.*
import com.ob.domain.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.ArgumentMatchers.anyString
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MarvelRepositoryTest {

  @Mock
  lateinit var localDataSource: LocalDataSource

  @Mock
  lateinit var remoteDataSource: RemoteDataSource

  @InjectMocks
  lateinit var repository: MarvelRepositoryImplementation

  private var hero1 =
    Hero(id = 1234, name = "Thor", description = "Son of odin", thumbnail = "http://simplethumbnailurl.jpg", comics = listOf(), stories = listOf())
  private var hero2 =
    Hero(id = 4321, name = "Thor", description = "Son of odin", thumbnail = "http://simplethumbnailurl.jpg", comics = listOf(), stories = listOf())
  private var hero3 =
    Hero(id = 5678, name = "Thor", description = "Son of odin", thumbnail = "http://simplethumbnailurl.jpg", comics = listOf(), stories = listOf())

  private var heroList = listOf(hero1, hero2, hero3)

  @ExperimentalCoroutinesApi
  @Test
  fun testCallsToBothDataSourcesWithForceTrue() {
    runBlocking {
      val forceParam = true
      val remoteData = Either.Right(heroList)
      val localData = flow { emit(remoteData) }

      whenever(localDataSource.getHeroes()).thenReturn(localData)
      whenever(remoteDataSource.getHeroes(anyString(), anyString(), anyString())).thenReturn(remoteData)

      repository.getHeroes(forceParam).collect {
        assertEquals(Either.Right(heroList), it)
      }
      verify(localDataSource).getHeroes()
      verify(remoteDataSource).getHeroes(anyString(), anyString(), anyString())
    }
  }

  @ExperimentalCoroutinesApi
  @Test
  fun testCallsToBothDataSourcesWithForceFalse() {
    runBlocking {
      val forceParam = false
      val remoteData = Either.Right(heroList)
      val localData = flow { emit(remoteData) }

      whenever(localDataSource.getHeroes()).thenReturn(localData)
      whenever(remoteDataSource.getHeroes(anyString(), anyString(), anyString())).thenReturn(remoteData)

      repository.getHeroes(forceParam).collect {
        assertEquals(Either.Right(heroList), it)
      }
      verify(localDataSource, times(2)).getHeroes()
      verify(remoteDataSource).getHeroes(anyString(), anyString(), anyString())

    }
  }

  @ExperimentalCoroutinesApi
  @Test
  fun testDataBaseInsertions() {
    runBlocking {

      val remoteData = Either.Right(heroList)
      val localData = flow { emit(remoteData) }

      whenever(localDataSource.getHeroes()).thenReturn(localData)
      whenever(remoteDataSource.getHeroes(anyString(), anyString(), anyString())).thenReturn(remoteData)

      repository.getHeroes(false).collect {
        verify(localDataSource).insertHeroes(any())
      }
    }
  }

  @Test
  fun testGetHeroById() {
    runBlocking {

      val localData = flow { emit(Either.Right(heroList[0])) }
      val id = 1234
      whenever(localDataSource.getHero(id)).thenReturn(localData)

      repository.getHero(id).collect {
        assertEquals(Either.Right(heroList[0]), it)
      }
    }
  }

  @Test
  fun testGetHeroByIdNotFound() {
    runBlocking {

      val localData = flow { emit(Either.Left(Failure.NullResult)) }
      val id = -1
      whenever(localDataSource.getHero(id)).thenReturn(localData)

      repository.getHero(id).collect {
        assertEquals(Either.Left(Failure.NullResult), it)
      }
    }
  }
}