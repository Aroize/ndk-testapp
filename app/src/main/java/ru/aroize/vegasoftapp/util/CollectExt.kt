package ru.aroize.vegasoftapp.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun <T> CollectEffect(
    flow: SharedFlow<T>,
    scope: CoroutineScope = rememberCoroutineScope(),
    block: suspend EffectScope.(T) -> Unit,
) {
    val ctx = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        val effectScope = EffectScopeImpl(ctx)
        flow.onEach { block(effectScope, it) }.launchIn(scope)
    }
}

interface EffectScope {
    val context: Context
}

internal class EffectScopeImpl(override val context: Context): EffectScope