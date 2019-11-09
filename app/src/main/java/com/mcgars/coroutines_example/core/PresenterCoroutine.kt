package com.mcgars.coroutines_example.core

import com.mcgars.coroutines_example.feature.EXAMPLE
import com.mcgars.coroutines_example.feature.ExampleRunnuble
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


abstract class PresenterCoroutine(
    private val progress: Progress
) : CoroutineScope, ExampleRunnuble {

    private val job: Job = SupervisorJob()

    override val coroutineContext: CoroutineContext = job + Dispatchers.Main + CoroutineExceptionHandler { _, throwable ->
        if (throwable !is CancellationException) errorHandler(throwable)
    }

    override fun type(): EXAMPLE = EXAMPLE.COROUTINE

    open fun errorHandler(throwable: Throwable) {
        throwable.printStackTrace()
        progress.hide()
        progress.showToast("Error happened")
    }

    fun cancelChildJob() {
        job.cancelChildren()
    }

    override fun onDestroy() {
        job.cancel()
    }

}