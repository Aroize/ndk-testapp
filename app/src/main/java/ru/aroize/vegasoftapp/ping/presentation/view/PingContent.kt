package ru.aroize.vegasoftapp.ping.presentation.view

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.aroize.vegasoftapp.R
import ru.aroize.vegasoftapp.arch.compose.ComposeMviView
import ru.aroize.vegasoftapp.ping.presentation.feature.PingFeature
import ru.aroize.vegasoftapp.ping.presentation.model.PingAction
import ru.aroize.vegasoftapp.ping.presentation.model.PingEffect
import ru.aroize.vegasoftapp.ping.presentation.model.PingPatch
import ru.aroize.vegasoftapp.ping.presentation.model.PingState
import ru.aroize.vegasoftapp.util.AppTheme
import ru.aroize.vegasoftapp.util.CollectEffect
import javax.inject.Inject


class PingView @Inject constructor(
    feature: PingFeature
) : ComposeMviView<PingFeature, PingState, PingAction, PingEffect, PingPatch>(feature) {

    @Composable
    override fun onBindState(state: PingState) {
        PingContent(
            state = state,
            onAction = { feature.process(it) }
        )
    }

    @Composable
    override fun observeEffects(snackbarHostState: SnackbarHostState) {
        CollectEffect(
            flow = feature.effects,
            block = { effect ->
                when (effect) {
                    PingEffect.AlreadyInProgress -> snackbarHostState.showSnackbar(
                        message = context.getString(R.string.ping_please_wait)
                    )
                }
            }
        )
    }
}

@Composable
private fun PingContent(
    modifier: Modifier = Modifier,
    state: PingState,
    onAction: (PingAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(AppTheme.Dimen.horizontalPadding),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(AppTheme.Dimen.cornersRadius),
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(AppTheme.Dimen.horizontalPadding)
                    .height(80.dp)
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.Center
            ) {
                when (val result = state.result) {
                    PingState.Result.Empty -> Text(
                        text = stringResource(id = R.string.ping_result_placeholder),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    PingState.Result.Loading -> CircularProgressIndicator()
                    is PingState.Result.Ready -> Text(
                        text = result.text,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        OutlinedTextField(
            value = state.input,
            onValueChange = { onAction(PingAction.InputChange(it)) },
            label = {
                Text(text = stringResource(id = R.string.ping_input_hint))
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.ping_delay_picker),
                style = MaterialTheme.typography.bodyLarge
            )
            DropdownMenuBox(
                delay = state.delay,
                onAction = onAction
            )
        }

        Button(
            onClick = { onAction(PingAction.Run) },
            enabled = state.input.isNotBlank(),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = stringResource(id = R.string.ping_run))
        }
    }
}

@Composable
fun DropdownMenuBox(
    delay: PingState.Delay,
    onAction: (PingAction) -> Unit,
) {
    Box {
        OutlinedButton(
            onClick = { onAction(PingAction.ClickDelay) }
        ) {
            Text(text = stringResource(id = R.string.ping_delay_picker_item, delay.picked))
        }
        DropdownMenu(
            expanded = delay.expanded,
            onDismissRequest = { onAction(PingAction.CancelPicking) }
        ) {
            delay.values.forEach { value ->
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(id = R.string.ping_delay_picker_item, value))
                    },
                    onClick = {
                        onAction(PingAction.DelayChange(value))
                    }
                )
            }
        }
    }
}

