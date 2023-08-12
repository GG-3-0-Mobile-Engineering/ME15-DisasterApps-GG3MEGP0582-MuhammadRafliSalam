package com.raflisalam.disastertracker

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.raflisalam.disastertracker.presentation.ui.HomeActivity
import com.raflisalam.disastertracker.presentation.ui.MapsActivity
import com.raflisalam.disastertracker.presentation.ui.SettingsActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(HomeActivity::class.java)


    @Test
    fun test_ActivityLayout() {
        //ensure layout is appears when HomeActivity is open
        onView(withId(R.id.homeLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun test_ShowMoreDisasterButton() {
        Intents.init()
        onView(withId(R.id.btnShowMore)).perform(click())
        intended(hasComponent(MapsActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun test_SettingsButton() {
        Intents.init()
        onView(withId(R.id.btnSettings)).perform(click())
        intended(hasComponent(SettingsActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun test_MapFragment() {
        //ensure map view appears correctly
        onView(withId(R.id.map)).check(matches(isDisplayed()))

        //check if the map can be swipe left, right, up, down
        onView(withId(R.id.map)).perform(swipeLeft())
        onView(withId(R.id.map)).perform(swipeRight())
        onView(withId(R.id.map)).perform(swipeUp())
        onView(withId(R.id.map)).perform(swipeDown())
    }
}