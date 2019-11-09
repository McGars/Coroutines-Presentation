package com.mcgars.coroutines_example.feature

import com.mcgars.coroutines_example.core.PresenterCoroutine
import com.mcgars.coroutines_example.core.Progress
import com.mcgars.coroutines_example.interactor.DataInteractor
import com.mcgars.coroutines_example.repository.DataRepository
import kotlinx.coroutines.*

class ExampleCoroutine5(private val progress: Progress) : PresenterCoroutine(progress) {

    private val dataInteractor = DataInteractor(DataRepository())

    override fun name(): String = "Coroutine, parallel load"

    override fun run() = launch {
        progress.show()

        val deferredPaymentsLoad = arrayOf(
            async(Dispatchers.IO) { dataInteractor.getPayments() },
            async(Dispatchers.IO) { dataInteractor.getPayments() }
        )

        val items = awaitAll(*deferredPaymentsLoad)

        progress.showToast(items)
        progress.hide()
    }

}