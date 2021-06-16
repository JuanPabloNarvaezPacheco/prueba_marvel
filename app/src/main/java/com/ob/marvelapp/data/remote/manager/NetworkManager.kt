package com.ob.marvelapp.data.remote.manager

import com.ob.domain.*
import retrofit2.Response

interface NetworkManager {

  class ServerResponseException : Failure.CustomFailure()
  class UnexpectedServerError : Failure.CustomFailure()
  class EmptyBody : Failure.CustomFailure()

  suspend fun <T, R> safeRequest(callRequest: Response<T>, functionCall: (Either.Right<T>) -> Either<Failure, R>): Either<Failure, R>

  class NetworkImplementation : NetworkManager {

    override suspend fun <T, R> safeRequest(callRequest: Response<T>, functionCall: (Either.Right<T>) -> Either<Failure, R>): Either<Failure, R> =
      ((if (callRequest.isSuccessful) {
        val body = callRequest.body()
        if (body != null) Either.Right(body)
        else Either.Left(EmptyBody())
      } else when (callRequest.code()) {
        in 300..600 -> Either.Left(ServerResponseException())
        else -> Either.Left(UnexpectedServerError())
      }).flatMapToRight { rightResult -> functionCall.invoke(Either.Right(rightResult)) })
  }
}