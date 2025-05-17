package ru.aroize.vegasoftapp.arch.api

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MviFeature<S, A, E, P> where S : MviState, A : MviAction, E : MviEffect, P : MviPatch {
    val uiState: StateFlow<S>
    val effects: SharedFlow<E>
    fun process(action: A)
}