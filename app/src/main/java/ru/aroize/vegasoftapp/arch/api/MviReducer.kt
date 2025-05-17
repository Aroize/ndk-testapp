package ru.aroize.vegasoftapp.arch.api

interface MviReducer<S, P> where S : MviState, P : MviPatch {
    fun applyPatch(state: S, patch: P): S
}