package ru.aroize.vegasoftapp.ping.presentation.feature

import kotlinx.collections.immutable.toPersistentList
import ru.aroize.vegasoftapp.arch.api.MviReducer
import ru.aroize.vegasoftapp.ping.presentation.model.PingPatch
import ru.aroize.vegasoftapp.ping.presentation.model.PingState

class PingReducer : MviReducer<PingState, PingPatch> {
    override fun applyPatch(state: PingState, patch: PingPatch): PingState {
        return when (patch) {
            is PingPatch.ChangeDelay -> state.copy(delay = state.delay.copy(
                expanded = false,
                picked = patch.delay
            ))
            is PingPatch.PickDelay -> state.copy(delay = state.delay.copy(
                expanded = true,
                values = patch.delays.toPersistentList()
            ))
            is PingPatch.ChangeInput -> state.copy(input = patch.input)
            PingPatch.Start -> state.copy(result = PingState.Result.Loading)
            is PingPatch.End -> state.copy(result = PingState.Result.Ready(patch.result))
        }
    }
}