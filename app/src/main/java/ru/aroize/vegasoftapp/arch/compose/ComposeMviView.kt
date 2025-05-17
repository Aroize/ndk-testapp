package ru.aroize.vegasoftapp.arch.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ru.aroize.vegasoftapp.arch.api.MviAction
import ru.aroize.vegasoftapp.arch.api.MviEffect
import ru.aroize.vegasoftapp.arch.api.MviFeature
import ru.aroize.vegasoftapp.arch.api.MviPatch
import ru.aroize.vegasoftapp.arch.api.MviState
import ru.aroize.vegasoftapp.arch.api.MviView

abstract class ComposeMviView<F, S, A, E, P>(
    protected val feature: MviFeature<S, A, E, P>
) :
    MviView<F, S, A, E, P> where F : MviFeature<S, A, E, P>, S : MviState, A : MviAction, E : MviEffect, P : MviPatch {

    @Composable
    override fun Content() {
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) {
            Box(modifier = Modifier.padding(it)) {
                val state by feature.uiState.collectAsState()
                onBindState(state = state)
                observeEffects(snackbarHostState)
            }
        }
    }

    @Composable
    abstract fun onBindState(state: S)

    @Composable
    abstract fun observeEffects(snackbarHostState: SnackbarHostState)
}