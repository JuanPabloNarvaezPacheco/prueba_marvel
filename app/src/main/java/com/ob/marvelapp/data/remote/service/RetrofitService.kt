package com.ob.marvelapp.data.remote.service

import com.ob.data.datasources.RemoteDataSource
import com.ob.domain.*
import com.ob.marvelapp.data.remote.*
import com.ob.marvelapp.data.remote.manager.*
import org.json.*

class RetrofitService(private val retrofit: ApiService,
                      private val serverMapper: RemoteMapper,
                      private val jsonMapper: JsonMapper,
                      private val connectivityManager: ConnectionManager) : RemoteDataSource,
  NetworkManager by NetworkManager.NetworkImplementation() {

  object ConnectionError : Failure.CustomFailure()
  object ParsingError : Failure.CustomFailure()

  override suspend fun getHeroes(timeStamp: String, apiKey: String, hash: String): Either<Failure, List<Hero>> =
    when (connectivityManager.isConnected()) {
      true -> safeRequest(retrofit.getHeroes(timeStamp, apiKey, hash)) { (b) ->
        try {
          jsonMapper.getArray(JSONObject(b).getJSONObject("data").getJSONArray("results").toString()) { jsonArray ->
            convertJsonToHeroes(jsonArray) { entity ->
              serverMapper.convertEntityHeroToDomain(entity)
            }
          }
        } catch (e: JSONException) {
          Either.Left(ParsingError)
        }
      }
      false -> Either.Left(ConnectionError)
    }
}