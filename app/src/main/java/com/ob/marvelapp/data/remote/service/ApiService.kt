package com.ob.marvelapp.data.remote.service

import com.ob.domain.Constants
import retrofit2.Response
import retrofit2.http.Query

interface ApiService {

  @retrofit2.http.GET(Constants.Server.Hero.GET_HEROES)
  suspend fun getHeroes(@Query("ts") timeStamp: String, @Query("apikey") apiKey: String, @Query("hash") hash: String): Response<String>
}