package com.mcgars.coroutines_example

import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
open class BaseTest {

    @Rule
    @JvmField
    val activityRule = IntentsTestRule(MainActivity::class.java, false, true)

    @Before
    open fun registerIntentServiceIdlingResource() {
    }

    @After
    open fun unregisterIntentServiceIdlingResource() {
    }


}