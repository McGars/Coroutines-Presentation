package com.mcgars.coroutines_example.feature


interface ExampleRunnuble {
    fun run(): Any
    fun name(): String
    fun type(): EXAMPLE
    fun onDestroy()
}

enum class EXAMPLE {
    COROUTINE,
    RX
}