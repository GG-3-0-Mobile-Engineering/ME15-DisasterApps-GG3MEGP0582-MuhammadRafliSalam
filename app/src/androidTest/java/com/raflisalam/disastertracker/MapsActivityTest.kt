package com.raflisalam.disastertracker

import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.raflisalam.disastertracker.presentation.ui.HomeActivity
import com.raflisalam.disastertracker.presentation.ui.MapsActivity
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.concurrent.thread

@RunWith(AndroidJUnit4::class)
class MapsActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MapsActivity::class.java)

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

    @Test
    fun test_ButtonShowDisaster() {
        onView(withId(R.id.btnShowDisasters)).check(matches(isDisplayed()))
        onView(withId(R.id.btnShowDisasters)).perform(click())
    }


}