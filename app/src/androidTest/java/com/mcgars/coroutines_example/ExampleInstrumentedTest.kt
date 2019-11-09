package com.mcgars.coroutines_example

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.mcgars.coroutines_example.utils.waiter.waitAndDo
import org.hamcrest.Matchers.*
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleInstrumentedTest : BaseTest() {

    @Test
    fun clickOnFirstElementInList() {
        onView(withText(startsWith("1."))).perform(click())
        // <-- here showing the loader,
        //     and waiting automatically while loader will be hidden before going further
        onView(withText(containsString("Payment")))
            .inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun clickOnFirstElementInListAndWait() {
        onView(withText(startsWith("1."))).perform(click())

        waitAndDo {
            onView(withText(containsString("Payment")))
                .inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
                .check(matches(isDisplayed()))
        }
    }

}
