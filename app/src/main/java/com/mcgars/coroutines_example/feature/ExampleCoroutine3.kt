package com.mcgars.coroutines_example.feature

import com.mcgars.coroutines_example.core.PresenterCoroutine
import com.mcgars.coroutines_example.core.Progress
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ExampleCoroutine3(private val progress: Progress) : PresenterCoroutine(progress) {

    private val channel = Channel<String>()

    override fun name(): String = "Coroutine, events"

    override fun run() = launch {
        registerChannel()

        progress.show()

        sendMessages().join()

        progress.hide()
    }

    private fun registerChannel() = launch {
        for (message in channel) println(message)
    }

    private fun sendMessages() = launch {

        channel.send("send: 1")
        delay(200)
        channel.send("send: 2")
        delay(400)
        channel.send("send: 3")

    }


}