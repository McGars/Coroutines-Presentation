package com.mcgars.coroutines_example

import com.mcgars.coroutines_example.core.IProgress
import com.mcgars.coroutines_example.feature.ExampleCoroutine1
import com.mcgars.coroutines_example.rule.TestCoroutineRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class PayUnitTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var presenter: ExampleCoroutine1
    private lateinit var progress: IProgress

    @Before
    fun setUp(){
        progress = mock()
        presenter = ExampleCoroutine1(progress)
    }

    @Test
    fun checkLoaderHideCalled() = testCoroutineRule.runBlockingTest {
        presenter.run()
        verify(progress).show()
        verify(progress).hide()
    }

}