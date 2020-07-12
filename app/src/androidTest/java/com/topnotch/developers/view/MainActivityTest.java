package com.topnotch.developers.view;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.topnotch.developers.R;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

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
