package com.mcgars.coroutines_example.feature

import com.mcgars.coroutines_example.core.IProgress
import com.mcgars.coroutines_example.core.PresenterRxJava
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit


class ExampleRxJava3(private val progress: IProgress) : PresenterRxJava(progress) {

    private val subject: BehaviorSubject<String> = BehaviorSubject.create()

    override fun name(): String = "Rx, events"

    override fun run() {
        registerRx()

        sendMessages()
    }

    private fun registerRx() {
        compositeDisposable.add(subject
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { println(it) }
        )
    }

    private fun sendMessages() {
        compositeDisposable.add(Observable.just("send: 1")
            .doOnNext { subject.onNext(it) }
            .delay(200, TimeUnit.MILLISECONDS)
            .doOnNext { subject.onNext("send: 2") }
            .delay(400, TimeUnit.MILLISECONDS)
            .doOnNext { subject.onNext("send: 3") }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progress.show() }
            .doOnComplete {  progress.hide()  }
            .subscribe()
        )
    }

}