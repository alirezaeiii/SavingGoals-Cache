package com.sample.android.qapital

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.sample.android.qapital.ui.MainActivity
import com.sample.android.qapital.ui.MainAdapter
import com.sample.android.qapital.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class TestMainActivity {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    /**
     * Prepare your test fixture for this test. In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests
     * significantly more reliable.
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }

    @Test
    fun shouldBeAbleToLaunchMainScreen() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldBeAbleToLoadList() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldBeAbleToScrollViewAndDisplayRuleDetails() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions
            .actionOnItemAtPosition<MainAdapter.SavingsGoalViewHolder>(5, click()))
        onView(withText(R.string.your_rules)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldBeAbleToScrollViewAndDisplayWeekDetails() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions
            .actionOnItemAtPosition<MainAdapter.SavingsGoalViewHolder>(4, click()))
        onView(withText(R.string.this_week_savings)).check(matches(isDisplayed()))
    }

}
