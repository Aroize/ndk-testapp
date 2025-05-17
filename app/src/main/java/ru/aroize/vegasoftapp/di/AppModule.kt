package ru.aroize.vegasoftapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.aroize.vegasoftapp.bridge.api.AsyncPingBridge
import ru.aroize.vegasoftapp.bridge.impl.NativeBridge
import ru.aroize.vegasoftapp.ping.domain.PingUseCase
import ru.aroize.vegasoftapp.ping.domain.PingUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindPingBridge(bridge: NativeBridge): AsyncPingBridge

    @Binds
    abstract fun bindPingUseCase(useCaseImpl: PingUseCaseImpl): PingUseCase
}