package com.mcgars.coroutines_example.interactor

import com.mcgars.coroutines_example.model.Payment
import com.mcgars.coroutines_example.repository.DataRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import kotlinx.coroutines.*


class DataInteractor(
    private val dataRepository: DataRepository
) {

    /**
     * Load via coroutines, success load
     */
    suspend fun getPayments(): List<Payment> = coroutineScope {
        val itemsFromCache = getCache()
        if (itemsFromCache.isNotEmpty()) return@coroutineScope itemsFromCache

        val newPayment = dataRepository.getNewPayments()
        val oldPayment = dataRepository.getOldPayments()
        val additionList = listOf(Payment(1, "From Coroutine"))
        val items = mergeListOf(oldPayment, newPayment, additionList)
        saveToCache(items)
        items
    }

    /**
     * Load via rxJava, success load
     */
    fun getPaymentsRx(): Observable<List<Payment>> {
        return Observable.concat<List<Payment>>(
            Observable.fromCallable { getCache() }
                .flatMap {
                    if (it.isEmpty()) Observable.empty()
                    else Observable.just(it)
                },
            Observable.zip(
                Observable.fromCallable { dataRepository.getNewPayments() },
                Observable.fromCallable { dataRepository.getOldPayments() },
                Observable.fromCallable { listOf(Payment(1, "From Rx")) },
                Function3<List<Payment>, List<Payment>, List<Payment>, List<Payment>>
                { newPayment, oldPayment, additionList ->
                    mergeListOf(newPayment, oldPayment, additionList)
                }
            ).doOnNext { saveToCache(it) }
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
     * Load via rx, error load
     */
    fun getPaymentsErrorRx(): Single<List<Payment>> {
        return Single.zip(
            Single.fromCallable { dataRepository.getNewPaymentsWithException() },
            Single.fromCallable { dataRepository.getOldPayments() },
            BiFunction { newPayment, oldPayment ->
                mergeListOf(newPayment, oldPayment)
            }
        )
    }

    /**
     * Load via coroutines
     * @return deferred async data
     */
    fun getPaymentsAsync(): Deferred<List<Payment>> = GlobalScope.async {
        val newPayment = dataRepository.getNewPayments()
        val oldPayment = dataRepository.getOldPayments()
        val additionList = listOf(Payment(1, "From Coroutine"))
        mergeListOf(oldPayment, newPayment, additionList)
    }



    private fun <T> mergeListOf(vararg items: List<T>): List<T> {
        val result = mutableListOf<T>()
        items.forEach { result += it }
        return result
    }

    private fun saveToCache(items: List<Payment>) {
        println("items saved to cache")
    }

    private fun getCache(): List<Payment> {
        return emptyList()
    }

}