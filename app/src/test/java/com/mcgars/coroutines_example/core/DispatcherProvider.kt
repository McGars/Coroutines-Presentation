package com.mcgars.coroutines_example.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DispatcherProvider {
    val IO: CoroutineDispatcher = Dispatchers.Unconfined
    val MAIN: CoroutineDispatcher = Dispatchers.Unconfined
    val DEFAULT: CoroutineDispatcher = Dispatchers.Unconfined
}