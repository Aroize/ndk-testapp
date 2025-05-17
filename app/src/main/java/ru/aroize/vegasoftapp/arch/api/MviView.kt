package ru.aroize.vegasoftapp.arch.api

import androidx.compose.runtime.Composable

interface MviView<F, S, A, E, P> where F : MviFeature<S, A, E, P>, S : MviState, A : MviAction, E : MviEffect, P : MviPatch {
    @Composable
    fun Content()
}