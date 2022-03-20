package com.tiagoexemplo.dsmovie.common

abstract class BaseObservable<LISTENER> {
    private val _listeners: MutableSet<LISTENER> = mutableSetOf()
    val listeners: Set<LISTENER> = _listeners

    fun registerListener(listener: LISTENER) {
        _listeners.add(listener)
    }

    fun unregisterListener(listener: LISTENER) {
        _listeners.remove(listener)
    }
}