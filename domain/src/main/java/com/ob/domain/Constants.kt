package com.ob.domain

object Constants {

  object Server {
    const val PRIVATE_KEY = "3e1e37a6f0bdbb15cec8dc4d369028fd3a633252"
    const val API_KEY = "ff1bbafd775a6d0209d677f348c22d6b"
    const val BASE_URL = "https://gateway.marvel.com:443/v1/public/"

    object Hero {
      const val GET_HEROES = "characters"
    }

    object OkHTTPConfig {
      const val timeOut = 15000L
    }
  }
}