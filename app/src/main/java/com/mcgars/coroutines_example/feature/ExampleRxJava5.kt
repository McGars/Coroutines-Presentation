package com.mcgars.coroutines_example.feature

import com.mcgars.coroutines_example.core.PresenterRxJava
import com.mcgars.coroutines_example.core.Progress
import com.mcgars.coroutines_example.interactor.DataInteractor
import com.mcgars.coroutines_example.model.Payment
import com.mcgars.coroutines_example.repository.DataRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime


class ExampleRxJava5(private val progress: Progress) : PresenterRxJava(progress) {

    private val dataInteractor = DataInteractor(DataRepository())

    override fun name(): String = "Rx, parallel load"

    override fun type(): EXAMPLE = EXAMPLE.RX

    override fun run() {
        compositeDisposable.add(
            Observable.zip<List<Payment>, List<Payment>, List<List<Payment>>>(
                dataInteractor.getPaymentsRx().subscribeOn(Schedulers.io()),
                dataInteractor.getPaymentsRx().subscribeOn(Schedulers.io()),
                BiFunction { t1, t2 -> listOf(t1, t2) }
            )
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progress.show() }
                .doFinally { progress.hide() }
                .subscribe(progress::showToast, ::errorHandler)
        )
    }

}