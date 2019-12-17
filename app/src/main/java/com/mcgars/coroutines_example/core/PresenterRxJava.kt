package com.mcgars.coroutines_example.core

import com.mcgars.coroutines_example.feature.EXAMPLE
import com.mcgars.coroutines_example.feature.ExampleRunnuble
import io.reactivex.disposables.CompositeDisposable


abstract class PresenterRxJava(private val progress: IProgress) : ExampleRunnuble {

    protected val compositeDisposable = CompositeDisposable()

    override fun type(): EXAMPLE = EXAMPLE.RX

    open fun errorHandler(throwable: Throwable) {
        progress.hide()
        progress.showToast("Error happened")
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}