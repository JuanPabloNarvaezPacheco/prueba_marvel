package com.ob.marvelapp.ui.events

open class Event<out T>(private val content: T) {

  private var hasBeenHandled = false

  /**
   * Returns the content and prevents its use again.
   */
  fun getContentIfNotHandled(): T? = if (hasBeenHandled) null
  else {
    hasBeenHandled = true
    content
  }
}