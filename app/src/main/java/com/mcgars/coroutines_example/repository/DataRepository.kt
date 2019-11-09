package com.mcgars.coroutines_example.repository

import androidx.annotation.WorkerThread
import com.mcgars.coroutines_example.model.Payment
import java.io.IOException


class DataRepository {

    @WorkerThread
    fun getNewPayments(): List<Payment> {
        Thread.sleep(1000)
        println("DataRepository: getNewPayments, thread: " + Thread.currentThread())
        return listOf()
    }

    @WorkerThread
    fun getNewPaymentsWithException(): List<Payment> {
        Thread.sleep(1000)
        throw IOException()
    }

    @WorkerThread
    fun getOldPayments(): List<Payment> {
        Thread.sleep(500)
        println("DataRepository: getOldPayments, thread: " + Thread.currentThread())
        return listOf()
    }

}