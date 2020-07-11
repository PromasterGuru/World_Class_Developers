package com.topnotch.developers.view;

import android.content.Intent;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.topnotch.developers.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {
    @Rule
    public IntentsTestRule<DetailActivity> detailActivityActivityTestRule =
            new IntentsTestRule<>(DetailActivity.class, true, false);

    @Before
    public void setUp(){
        Intent intent = new Intent();
        intent.putExtra("username", "TheDancerCodes");
        intent.putExtra("imageUrl", "https://avatars0.githubusercontent.com/u/6739804?v=4");
        detailActivityActivityTestRule.launchActivity(intent);
    }

    @Test
    public void detailActivityLayout_isRedered(){
        registerIdlingResource();
        onView(withId(R.id.user_details)).check(matches(isDisplayed()));
    }

    @Test
    public void userProfileInformation_isDisplayed(){
        registerIdlingResource();
        onView(withId(R.id.userName)).check(matches(withText("TheDancerCodes")));
        onView(withId(R.id.profile_name)).check(matches(withText("Profile")));
        onView(withId(R.id.userProfile)).check(matches(isDisplayed()));
        onView(withId(R.id.txtBio)).check(matches(withText("Bio")));
        onView(withId(R.id.imgOrganization)).check(matches(isDisplayed()));
        onView(withId(R.id.txtFollowers)).check(matches(withText("Followers")));
        onView(withId(R.id.txtFollowing)).check(matches(withText("Following")));
        onView(withId(R.id.txtRepositories)).check(matches(withText("Repositories")));
        onView(withId(R.id.txtGists)).check(matches(withText("Gists")));
    }

    public void registerIdlingResource(){
        IdlingRegistry.getInstance().register(detailActivityActivityTestRule.getActivity().getCountingIdlingResource());
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(detailActivityActivityTestRule.getActivity().getCountingIdlingResource());
    }
}
