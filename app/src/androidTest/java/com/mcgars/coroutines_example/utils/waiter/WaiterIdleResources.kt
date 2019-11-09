package com.mcgars.coroutines_example.utils.waiter

import java.util.concurrent.TimeoutException


private const val TIME_LIMIT = 5000L

fun waitAndDo(limit: Long = TIME_LIMIT, action: ()->Unit) {
    var time = 0L
    val tick = 500L
    var error: Exception? = null
    while (time <= limit) {
        try {
            action.invoke()
            return
        } catch (e: Exception) {
            error = e
            Thread.sleep(tick)
            time += tick
        }
    }

    throw error ?: TimeoutException()
}