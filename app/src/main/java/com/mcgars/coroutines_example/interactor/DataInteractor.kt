package com.mcgars.coroutines_example.interactor

import com.mcgars.coroutines_example.model.Payment
import com.mcgars.coroutines_example.repository.DataRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext


class DataInteractor(
    private val dataRepository: DataRepository
) {

    /**
     * Load via coroutines, success load
     */
    fun getPayments(): List<Payment> {
        val newPayment = dataRepository.getNewPayments()
        val oldPayment = dataRepository.getOldPayments()
        val additionList = listOf(Payment(1, "From Coroutine"))
        return mergeListOf(oldPayment, newPayment, additionList)
    }

    /**
     * Load via rxJava, success load
     */
    fun getPaymentsRx(): Single<List<Payment>> {
        return Single.zip(
            Single.fromCallable { dataRepository.getNewPayments() },
            Single.fromCallable { dataRepository.getOldPayments() },
            Single.fromCallable { listOf(Payment(1, "From Rx")) },
            Function3 <List<Payment>, List<Payment>, List<Payment>, List<Payment>> {
                    newPayment,
                    oldPayment,
                    additionList ->
                mergeListOf(newPayment, oldPayment, additionList)
            }
        )
    }

    /**
     * Load via coroutines, error load
     */
    fun getPaymentsWithError(): List<Payment> {
        val oldPayment = dataRepository.getNewPaymentsWithException()
        val newPayment = dataRepository.getOldPayments()
        return mergeListOf(oldPayment, newPayment)
    }

    /**
     * Load via rxJava, error load
     */
    fun getPaymentsErrorRx(): Single<List<Payment>> {
        return Single.zip(
            Single.fromCallable { dataRepository.getNewPaymentsWithException() },
            Single.fromCallable { dataRepository.getOldPayments() },
            BiFunction<List<Payment>, List<Payment>, List<Payment>> { newPayment, oldPayment ->
                mergeListOf(newPayment, oldPayment)
            }
        )
    }

    /**
     * Load via coroutines, success load
     */
    suspend fun getPaymentsAsync(): Deferred<List<Payment>> = GlobalScope.async(coroutineContext) {
        listOf(Payment(1, "From Coroutine"))
    }

    private fun <T> mergeListOf(vararg items: List<T>): List<T> {
        val result = mutableListOf<T>()
        items.forEach { result += it }
        return result
    }

}