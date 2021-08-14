package com.sample.android.goals

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sample.android.goals.ui.MainActivity
import com.sample.android.goals.ui.MainAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestMainActivity {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

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
