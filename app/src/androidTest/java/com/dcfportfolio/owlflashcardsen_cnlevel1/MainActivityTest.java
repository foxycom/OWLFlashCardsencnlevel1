package com.dcfportfolio.owlflashcardsen_cnlevel1;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Instrumented tests for MainActivity
 * covers
 * entering name in alert dialog,
 * pressing 'enter' button to change to card menu screen.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class){
        @Override
        protected void beforeActivityLaunched() {
            clearSharedPrefs(InstrumentationRegistry.getTargetContext());
            super.beforeActivityLaunched();
        }
    };

    /**
     * Clears everything in the SharedPreferences
     */
    private void clearSharedPrefs(Context context) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    @Test
    public void mainActivityNameDialogTest() {

        ViewInteraction editText = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.custom),
                                childAtPosition(
                                        withId(R.id.customPanel),
                                        0)),
                        0),
                        isDisplayed()));
        editText.perform(replaceText("davi"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    /*@Test
    public void OpenNextActivityTest() {
        onView(withId(R.id.title_enter_button)).perform(click());
        onView(withId(R.id.pronounceGuideButton)).check(matches(isDisplayed()));
    }*/
}
