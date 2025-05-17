package ru.aroize.vegasoftapp.ping.presentation.model

import ru.aroize.vegasoftapp.arch.api.MviPatch

sealed interface PingPatch : MviPatch {
    data class ChangeInput(val input: String) : PingPatch

    data class ChangeDelay(val delay: Int) : PingPatch
    data class PickDelay(val delays: List<Int>) : PingPatch

    data object Start : PingPatch
    data class End(val result: String) : PingPatch
}