package com.mcgars.coroutines_example.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DispatcherProvider {
    val IO: CoroutineDispatcher = Dispatchers.IO
    val MAIN: CoroutineDispatcher = Dispatchers.Main
    val DEFAULT: CoroutineDispatcher = Dispatchers.Default
}

suspend fun <T> CoroutineScope.withIO(block: suspend CoroutineScope.() -> T): T {
    return withContext(DispatcherProvider.IO, block)
}

suspend fun <T> CoroutineScope.withDefault(block: suspend CoroutineScope.() -> T): T {
    return withContext(DispatcherProvider.DEFAULT, block)
}

suspend fun <T> CoroutineScope.withMain(block: suspend CoroutineScope.() -> T): T {
    return withContext(DispatcherProvider.MAIN, block)
}