package com.mcgars.coroutines_example.feature

import com.mcgars.coroutines_example.core.PresenterCoroutine
import com.mcgars.coroutines_example.core.Progress
import com.mcgars.coroutines_example.interactor.DataInteractor
import com.mcgars.coroutines_example.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ExampleCoroutine1(private val progress: Progress) : PresenterCoroutine(progress) {

    private val dataInteractor = DataInteractor(DataRepository())

    override fun name(): String = "Coroutine, load payments"

    override fun run() = launch {
        progress.show()
        val payments = withContext(Dispatchers.IO) { dataInteractor.getPayments() }
        progress.showToast(payments)
        progress.hide()
    }

}