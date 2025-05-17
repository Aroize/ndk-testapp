package ru.aroize.vegasoftapp.ping.presentation.feature

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import ru.aroize.vegasoftapp.arch.impl.BaseMviFeature
import ru.aroize.vegasoftapp.ping.domain.PingUseCase
import ru.aroize.vegasoftapp.ping.presentation.model.PingAction
import ru.aroize.vegasoftapp.ping.presentation.model.PingEffect
import ru.aroize.vegasoftapp.ping.presentation.model.PingPatch
import ru.aroize.vegasoftapp.ping.presentation.model.PingState
import javax.inject.Inject

@HiltViewModel
class PingFeature @Inject constructor(
    private val useCase: PingUseCase
) : BaseMviFeature<PingState, PingAction, PingEffect, PingPatch>(
    initialState = PingState(),
    reducer = PingReducer()
) {

    private var job: Job? = null

    override fun process(action: PingAction) {
        when (action) {
            is PingAction.DelayChange -> updateState(PingPatch.ChangeDelay(action.delay))
            is PingAction.InputChange -> updateState(PingPatch.ChangeInput(action.input))
            PingAction.ClickDelay -> updateState(PingPatch.PickDelay(DELAYS_SEC))
            PingAction.CancelPicking -> updateState(PingPatch.ChangeDelay(state.delay.picked))
            PingAction.Run -> runImpl()
        }
    }

    private fun runImpl() {
        if (job?.isActive == true) {
            applyEffect(PingEffect.AlreadyInProgress)
            return
        }
        job = viewModelScope.launchSafe {
            updateState(PingPatch.Start)
            val result = useCase(input = state.input, delay = state.delay.picked)
            updateState(PingPatch.End(result = result))
        }
    }

    private companion object {
        val DELAYS_SEC = buildList {
            repeat(10) { add(it + 1) }
        }
    }
}