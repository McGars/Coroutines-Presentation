package com.mcgars.coroutines_example.feature

import com.mcgars.coroutines_example.core.PresenterCoroutine
import com.mcgars.coroutines_example.core.Progress
import kotlinx.coroutines.*

class ExampleCoroutine6(private val progress: Progress) : PresenterCoroutine(progress) {

    override fun name(): String = "Coroutine, fill list, 1 element throw exception"

    override fun run() = launch {
        progress.show()

        val newItems = mutableListOf<String>()
        (0..3).forEach {
            addToList(newItems, it).join()
        }

        progress.showToast(newItems)
        progress.hide()
    }

    private fun addToList(items: MutableList<String>, position: Int) = async {
        if (position == 2) throw RuntimeException()
        items.add("$position")
    }

}