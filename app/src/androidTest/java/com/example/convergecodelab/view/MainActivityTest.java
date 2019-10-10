package com.example.convergecodelab.view;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.convergecodelab.R;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void recyclerView_isRedered(){
        registerIdlingResource();
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void swipeToRefreshLayout(){
        registerIdlingResource();
        onView(withId(R.id.swipeToReflesh)).perform(swipeDown());
    }

    @Test
    public void recyclerView_hasClickableItems(){
        registerIdlingResource();
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.user_details)).check(matches(isDisplayed()));
    }

    @Test
    public void mainActivityLayout_isRedered(){
        registerIdlingResource();
        onView(withId(R.id.main_layout)).check(matches(isDisplayed()));
    }

    public void registerIdlingResource(){
        IdlingRegistry.getInstance().register(activityTestRule.getActivity().getCountingIdlingResource());
    }


    @After
    public void unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(activityTestRule.getActivity().getCountingIdlingResource());
    }
}