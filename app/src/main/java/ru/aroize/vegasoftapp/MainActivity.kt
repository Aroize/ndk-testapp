package ru.aroize.vegasoftapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import dagger.hilt.android.AndroidEntryPoint
import ru.aroize.vegasoftapp.ping.presentation.feature.PingFeature
import ru.aroize.vegasoftapp.ping.presentation.view.PingView

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val feature: PingFeature by viewModels()
    private val content by lazy { PingView(feature) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                content.Content()
            }
        }
    }
}