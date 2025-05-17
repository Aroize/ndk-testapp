@file:Suppress("unused")

package ru.aroize.vegasoftapp.bridge.api

fun interface PingCallback {
    fun onResult(result: String)
}