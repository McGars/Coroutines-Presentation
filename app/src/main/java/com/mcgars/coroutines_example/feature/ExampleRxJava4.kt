package com.mcgars.coroutines_example.feature

import com.mcgars.coroutines_example.core.PresenterRxJava
import com.mcgars.coroutines_example.core.Progress
import com.mcgars.coroutines_example.interactor.DataInteractor
import com.mcgars.coroutines_example.repository.DataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.rx2.asSingle
import kotlinx.coroutines.rx2.rxSingle
import kotlin.coroutines.CoroutineContext


class ExampleRxJava4(private val progress: Progress) : PresenterRxJava(progress), CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    private val dataInteractor = DataInteractor(DataRepository())

    override fun name(): String = "Rx, coroutine to rx"

    override fun run() {
        example1()
        example2()
    }

    private fun example1() = launch {
        compositeDisposable.add(
            rxSingle { dataInteractor.getPaymentsWithError() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progress.show() }
                .doFinally { progress.hide() }
                .subscribe(progress::showToast, ::errorHandler)
        )
    }

    @ExperimentalCoroutinesApi
    private fun example2() = launch {
        dataInteractor.getPaymentsAsync().asSingle(Dispatchers.IO)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progress.show() }
            .doFinally { progress.hide() }
            .subscribe(progress::showToast, ::errorHandler)
    }

    override fun type(): EXAMPLE = EXAMPLE.RX

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}