package ru.aroize.vegasoftapp.arch.impl

import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.aroize.vegasoftapp.arch.api.MviAction
import ru.aroize.vegasoftapp.arch.api.MviEffect
import ru.aroize.vegasoftapp.arch.api.MviFeature
import ru.aroize.vegasoftapp.arch.api.MviPatch
import ru.aroize.vegasoftapp.arch.api.MviReducer
import ru.aroize.vegasoftapp.arch.api.MviState
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseMviFeature<S, A, E, P>(
    initialState: S,
    private val reducer: MviReducer<S, P>
) : ViewModel(), MviFeature<S, A, E, P>
        where S : MviState, A : MviAction, E : MviEffect, P : MviPatch
{

    private val tag: String by lazy { this::class.java.canonicalName }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }

    private val mutableUiState = MutableStateFlow(initialState)
    private val mutableEffects = MutableSharedFlow<E>()
    protected val state: S
        get() = mutableUiState.value

    override val uiState: StateFlow<S>
        get() = mutableUiState.asStateFlow()

    override val effects: SharedFlow<E>
        get() = mutableEffects.asSharedFlow()

    @CallSuper
    protected open fun onError(e: Throwable) {
        Log.w(tag, "Feature handled exception", e)
    }

    protected fun updateState(patch: P) {
        viewModelScope.launchSafe {
            val newState = reducer.applyPatch(mutableUiState.value, patch)
            mutableUiState.update { newState }
        }
    }

    protected fun applyEffect(effect: E) {
        viewModelScope.launchSafe {
            mutableEffects.emit(effect)
        }
    }

    protected fun CoroutineScope.launchSafe(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit,
    ): Job = launch(context + exceptionHandler, block = block)
}