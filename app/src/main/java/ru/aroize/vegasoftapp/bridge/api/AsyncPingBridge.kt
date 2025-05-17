package ru.aroize.vegasoftapp.bridge.api

interface AsyncPingBridge {
    fun asyncPing(
        input: String,
        delay: Int,
        callback: PingCallback
    )
}