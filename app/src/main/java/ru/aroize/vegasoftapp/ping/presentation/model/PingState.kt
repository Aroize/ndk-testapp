package ru.aroize.vegasoftapp.ping.presentation.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import ru.aroize.vegasoftapp.arch.api.MviState

@Immutable
data class PingState(
    val result: Result = Result.Empty,
    val input: String = "",
    val delay: Delay = Delay()
) : MviState {

    @Immutable
    sealed interface Result {
        data class Ready(val text: String) : Result
        data object Loading : Result
        data object Empty : Result
    }

    @Immutable
    data class Delay(
        val expanded: Boolean = false,
        val picked: Int = 1,
        val values: PersistentList<Int> = persistentListOf()
    )
}