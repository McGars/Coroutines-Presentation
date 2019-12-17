package com.mcgars.coroutines_example.feature

import com.mcgars.coroutines_example.core.*
import com.mcgars.coroutines_example.interactor.DataInteractor
import com.mcgars.coroutines_example.repository.DataRepository
import kotlinx.coroutines.launch


class ExampleCoroutine1(
    private val progress: IProgress
) : PresenterCoroutine(progress) {

    private val dataInteractor = DataInteractor(DataRepository())

    override fun name(): String = "Coroutine, load payments"

    override fun run() = launch {
        progress.show()
        val payments = withIO { dataInteractor.getPayments() }
        progress.showToast(payments)
        progress.hide()
    }

}