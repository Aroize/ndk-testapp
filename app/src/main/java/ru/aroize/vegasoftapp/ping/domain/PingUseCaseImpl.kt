package ru.aroize.vegasoftapp.ping.domain

import ru.aroize.vegasoftapp.bridge.impl.NativeBridge
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PingUseCaseImpl @Inject constructor() : PingUseCase {

    private val bridge by lazy { NativeBridge() }

    override suspend fun invoke(input: String, delay: Int): String = suspendCoroutine { continuation ->
        bridge.asyncPing(input, delay) { result ->
            continuation.resume(result)
        }
    }
}