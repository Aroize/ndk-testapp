package ru.aroize.vegasoftapp.ping.presentation.model

import ru.aroize.vegasoftapp.arch.api.MviEffect

sealed interface PingEffect : MviEffect {
    data object AlreadyInProgress : PingEffect
}