package com.mcgars.coroutines_example.rule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


class TestCoroutineRule : TestRule {

    private val testCoroutineDisptcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDisptcher)

    override fun apply(base: Statement, description: Description): Statement =
        object : Statement() {
            override fun evaluate() {
                Dispatchers.setMain(testCoroutineDisptcher)

                var error: Throwable? = null

                try {
                    base.evaluate()
                } catch (e: Exception) {
                    error = e
                } finally {
                    Dispatchers.resetMain()
                    testCoroutineDisptcher.cleanupTestCoroutines()
                }

                if (error != null) throw error

            }
        }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineScope.runBlockingTest(block)

}