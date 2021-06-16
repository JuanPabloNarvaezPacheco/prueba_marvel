package com.ob.marvelapp.data.remote.manager

import android.app.Application
import android.content.Context
import android.net.*
import android.net.ConnectivityManager

class ConnectivityManager(val app: Application) : ConnectionManager {

  override fun isConnected(): Boolean = (app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
    getNetworkCapabilities(activeNetwork)?.run {
      hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || hasTransport(
        NetworkCapabilities.TRANSPORT_VPN)
    } ?: false
  }
}