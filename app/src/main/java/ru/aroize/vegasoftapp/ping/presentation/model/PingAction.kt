package ru.aroize.vegasoftapp.ping.presentation.model

import ru.aroize.vegasoftapp.arch.api.MviAction

sealed interface PingAction : MviAction {
    data class InputChange(val input: String) : PingAction
    data class DelayChange(val delay: Int) : PingAction
    data object ClickDelay : PingAction
    data object CancelPicking : PingAction
    data object Run : PingAction
}