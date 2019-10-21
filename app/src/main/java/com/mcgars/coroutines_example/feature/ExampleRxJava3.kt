package com.mcgars.coroutines_example.feature

import com.mcgars.coroutines_example.core.PresenterRxJava
import com.mcgars.coroutines_example.core.Progress
import com.mcgars.coroutines_example.interactor.DataInteractor
import com.mcgars.coroutines_example.repository.DataRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class ExampleRxJava3(private val progress: Progress) : PresenterRxJava(progress) {

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