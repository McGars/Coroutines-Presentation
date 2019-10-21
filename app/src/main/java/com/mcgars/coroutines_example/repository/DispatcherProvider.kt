package com.mcgars.coroutines_example.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


interface IDispatcherProvider {
    val Main: CoroutineDispatcher get() = Dispatchers.Main
    val IO: CoroutineDispatcher get() = Dispatchers.IO
    val Default: CoroutineDispatcher get() = Dispatchers.Default
}

class DispatcherProvider : IDispatcherProvider

class DispatcherProviderTest(
    override val Main: CoroutineDispatcher = Dispatchers.Unconfined,
    override val IO: CoroutineDispatcher = Dispatchers.Unconfined,
    override val Default: CoroutineDispatcher = Dispatchers.Unconfined
) : IDispatcherProvider