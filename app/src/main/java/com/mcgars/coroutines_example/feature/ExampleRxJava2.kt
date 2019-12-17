package com.mcgars.coroutines_example.feature

import com.mcgars.coroutines_example.core.IProgress
import com.mcgars.coroutines_example.core.PresenterRxJava
import com.mcgars.coroutines_example.interactor.DataInteractor
import com.mcgars.coroutines_example.repository.DataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ExampleRxJava2(private val progress: IProgress) : PresenterRxJava(progress) {

    private val dataInteractor = DataInteractor(DataRepository())

    override fun name(): String = "Rx, load payments with exception"

    override fun run() {
        compositeDisposable.add(
            dataInteractor.getPaymentsErrorRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progress.show() }
                .doFinally { progress.hide() }
                .subscribe(progress::showToast, ::errorHandler)
        )
    }

}