package com.mcgars.coroutines_example.feature

import com.mcgars.coroutines_example.core.PresenterCoroutine
import com.mcgars.coroutines_example.core.Progress
import com.mcgars.coroutines_example.interactor.DataInteractor
import com.mcgars.coroutines_example.model.Payment
import com.mcgars.coroutines_example.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.await
import kotlinx.coroutines.withContext

class ExampleCoroutine4(private val progress: Progress) : PresenterCoroutine(progress) {

    private val dataInteractor = DataInteractor(DataRepository())

    override fun name(): String = "Coroutine, rx to coroutine"

    override fun run() = launch {
        progress.show()

        val payments: List<Payment> = withContext(Dispatchers.IO) {
            dataInteractor.getPaymentsRx().await()
        }

        progress.showToast(payments)
        progress.hide()
    }

}