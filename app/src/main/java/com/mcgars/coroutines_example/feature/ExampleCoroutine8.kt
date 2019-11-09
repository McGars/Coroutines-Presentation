package com.mcgars.coroutines_example.feature

import com.mcgars.coroutines_example.core.PresenterCoroutine
import com.mcgars.coroutines_example.core.Progress
import com.mcgars.coroutines_example.interactor.DataInteractor
import com.mcgars.coroutines_example.repository.DataRepository
import kotlinx.coroutines.*

class ExampleCoroutine8(private val progress: Progress) : PresenterCoroutine(progress) {

    private val dataInteractor = DataInteractor(DataRepository())

    override fun name(): String = "Coroutine, parallel load"

    override fun run() = launch {
        progress.show()

        val deferredPaymentsLoad = arrayOf(
            async(Dispatchers.Unconfined) { dataInteractor.getPayments() },
            async(Dispatchers.Unconfined) { dataInteractor.getPayments() }
        )

        val items = awaitAll(*deferredPaymentsLoad)

        progress.showToast(items)
        progress.hide()
    }

}