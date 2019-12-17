package com.mcgars.coroutines_example.feature

import com.mcgars.coroutines_example.core.IProgress
import com.mcgars.coroutines_example.core.PresenterCoroutine
import com.mcgars.coroutines_example.interactor.DataInteractor
import com.mcgars.coroutines_example.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ExampleCoroutine2(private val progress: IProgress) : PresenterCoroutine(progress) {

    private val dataInteractor = DataInteractor(DataRepository())

    override fun name(): String = "Coroutine, load payments with exception"

    override fun run() = launch {
        progress.show()
        val list = withContext(Dispatchers.IO) {
            dataInteractor.getPaymentsWithError()
        }
        progress.showToast(list)
        progress.hide()

    }

}