package ru.aroize.vegasoftapp.bridge.impl

import ru.aroize.vegasoftapp.bridge.api.AsyncPingBridge
import ru.aroize.vegasoftapp.bridge.api.PingCallback
import javax.inject.Inject


class NativeBridge @Inject constructor(): AsyncPingBridge {

    override fun asyncPing(input: String, delay: Int, callback: PingCallback) = nativePing(input, delay, callback)

    private external fun nativePing(
        input: String,
        delay: Int,
        callback: PingCallback
    )
}