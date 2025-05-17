package ru.aroize.vegasoftapp.ping.domain

import ru.aroize.vegasoftapp.bridge.impl.NativeBridge
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface PingUseCase {
    suspend operator fun invoke(
        input: String,
        delay: Int
    ): String
}

