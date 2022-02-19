package ru.yoequilibrium.testdatabindingrange

import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.slider.RangeSlider
import org.hamcrest.CoreMatchers

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ru.yoequilibrium.testdatabindingrange", appContext.packageName)

        val activityScenario: ActivityScenario<MainActivity> =
            ActivityScenario.launch(MainActivity::class.java)
        activityScenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.seekBar)).perform(setValues(listOf<Float>(20f,100f)))

        onView(withId(R.id.edit_min_price)).check(matches(withText("20")))
        onView(withId(R.id.edit_max_price)).check(matches(withText("100")))

        onView(withId(R.id.edit_min_price)).perform(setTextInTextView("50"))
        onView(withId(R.id.seekBar)).check(matches(withValue(listOf(50f,100f))))
    }

    @Test
    fun useAppContexttestWrong() {
        val activityScenario: ActivityScenario<MainActivity> =
            ActivityScenario.launch(MainActivity::class.java)
        activityScenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.seekBar)).perform(setValues(listOf<Float>(2f,10f)))

        onView(withId(R.id.edit_min_price)).check(matches(withText("2")))
        //must be greater of 20
    }

    fun setTextInTextView(value: String): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return CoreMatchers.allOf(ViewMatchers.isDisplayed(), ViewMatchers.isAssignableFrom(TextView::class.java))
            }

            override fun perform(uiController: UiController, view: View) {
                (view as TextView).text = value
            }

            override fun getDescription(): String {
                return "replace text"
            }
        }
    }

    fun withValue(expectedValue: List<Float>): Matcher<android.view.View?> {
        return object : BoundedMatcher<android.view.View?, RangeSlider>(RangeSlider::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("expected: $expectedValue")
            }

            override fun matchesSafely(slider: RangeSlider?): Boolean {
                return slider?.values == expectedValue
            }
        }
    }

    fun setValues(values: List<Float>): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Set Slider value to $values"
            }

            override fun getConstraints(): Matcher<android.view.View> {
                return ViewMatchers.isAssignableFrom(RangeSlider::class.java)
            }

            override fun perform(uiController: UiController?, view: android.view.View) {
                val seekBar = view as RangeSlider
                seekBar.values = values
            }
        }
    }
}