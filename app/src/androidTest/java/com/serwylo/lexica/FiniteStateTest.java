package com.serwylo.lexica;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertNotNull;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import com.serwylo.lexica.game.Game;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

@RunWith(AndroidJUnit4.class)
public class FiniteStateTest {

    @Rule
    public IntentsTestRule<MainMenuActivity> activityRule = new IntentsTestRule<>(MainMenuActivity.class);


    @Test
    public void testGameStartToGameRunning() {
        ActivityScenario<MainMenuActivity> scenario = ActivityScenario.launch(MainMenuActivity.class);
        onView(allOf(withId(R.id.new_game), isDisplayed()));
        // Verify that the activity is displayed on the screen
        Espresso.onView(allOf(withId(R.id.new_game))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Click a button (assuming the button has an ID, change it accordingly)
        Espresso.onView(ViewMatchers.withId(R.id.new_game)).perform(ViewActions.click());

        // Wait for the next activity to be launched
        Intents.intended(IntentMatchers.hasAction("com.serwylo.lexica.action.NEW_GAME"));

        // Get the resulting activity
        Activity resultActivity = getActivityInstance();
        GameActivity gameActivity = (GameActivity) resultActivity;
        Game game = gameActivity.getGame();
        assertEquals(game.getStatus(), Game.GameStatus.GAME_RUNNING);

        // Perform assertions or verifications on the ResultActivity if needed
        // For example, you can check if certain views are displayed in the ResultActivity
        Espresso.onView(ViewMatchers.withId(R.id.game_wrapper)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testGameStartedAndRunningToPaused() {
        ActivityScenario<MainMenuActivity> scenario = ActivityScenario.launch(MainMenuActivity.class);
        onView(allOf(withId(R.id.new_game), isDisplayed()));
        // Verify that the activity is displayed on the screen
        Espresso.onView(allOf(withId(R.id.new_game))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Click a button (assuming the button has an ID, change it accordingly)
        Espresso.onView(ViewMatchers.withId(R.id.new_game)).perform(ViewActions.click());

        // Wait for the next activity to be launched
        Intents.intended(IntentMatchers.hasAction("com.serwylo.lexica.action.NEW_GAME"));

        // Get the resulting activity
        Activity resultActivity = getActivityInstance();
        GameActivity gameActivity = (GameActivity) resultActivity;
        Game game = gameActivity.getGame();
        assertEquals(game.getStatus(), Game.GameStatus.GAME_RUNNING);

        // Perform assertions or verifications on the ResultActivity if needed
        // For example, you can check if certain views are displayed in the ResultActivity
        Espresso.onView(ViewMatchers.withId(R.id.game_wrapper)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(MainMenuActivity.class.getName()));

        assertEquals(game.getStatus(), Game.GameStatus.GAME_PAUSED);
    }

    private Activity getActivityInstance() {
        // This method helps to get the current activity instance
        final Activity[] currentActivity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Collection<Activity> activities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                for (Activity activity : activities) {
                    currentActivity[0] = activity;
                    break;
                }
            }
        });
        return currentActivity[0];
    }
}
