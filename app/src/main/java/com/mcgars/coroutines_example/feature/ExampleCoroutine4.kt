package com.mcgars.coroutines_example.feature

import com.mcgars.coroutines_example.core.IProgress
import com.mcgars.coroutines_example.core.PresenterCoroutine
import com.mcgars.coroutines_example.interactor.DataInteractor
import com.mcgars.coroutines_example.model.Payment
import com.mcgars.coroutines_example.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime

class ExampleCoroutine4(private val progress: IProgress) : PresenterCoroutine(progress) {

    private val dataInteractor = DataInteractor(DataRepository())

    override fun name(): String = "Coroutine, rx to coroutine"

    @ExperimentalTime
    override fun run() = launch {
        progress.show()

        val payments: List<Payment> = withContext(Dispatchers.IO) {
            dataInteractor.getPaymentsRx().awaitSingle()
        }

        progress.showToast(payments)
        progress.hide()
    }

}