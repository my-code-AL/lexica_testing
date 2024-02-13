package com.serwylo.lexica;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class FiniteStateTest {

    @Rule
    public IntentsTestRule<MainMenuActivity> activityRule = new IntentsTestRule<>(MainMenuActivity.class);

    @Test
    public void testMainMenuToGame() {
        ActivityScenario<MainMenuActivity> scenario = ActivityScenario.launch(MainMenuActivity.class);
        onView(allOf(withId(R.id.game_mode), isDisplayed()));
        // Verify that the activity is displayed on the screen
        Espresso.onView(allOf(withId(R.id.game_mode_button))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

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

        // Check Restore Screen Path
        onView(allOf(withId(R.id.restore_game), isDisplayed()));
        Espresso.onView(allOf(withId(R.id.restore_game))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.restore_game)).perform(ViewActions.click());

        Intents.intended(IntentMatchers.hasAction("com.serwylo.lexica.action.RESTORE_GAME"));
        Espresso.onView(ViewMatchers.withId(R.id.game_wrapper)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Check Rotate Screen Path
        onView(withId(R.id.rotate)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.game_wrapper)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Check End Game Path
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());

        Espresso.onView(withText("End Game")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withText("End Game")).perform(click());

        Espresso.onView(withText("END GAME")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withText("END GAME")).perform(click());

        // Check Score Screen related path
        Intents.intended(IntentMatchers.hasAction("com.serwylo.lexica.action.SCORE"));

        onView(withId(R.id.found_words_button)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.missed_words_button)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.found_words_button)).perform(click());
        onView(withId(R.id.missed_words_button)).perform(click());

        onView(withId(R.id.toolbar)).check(ViewAssertions.matches(isDisplayed()));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(MainMenuActivity.class.getName()));
    }

    @Test
    public void testMainMenuToLexiconToMainMenu() {
        ActivityScenario<MainMenuActivity> scenario = ActivityScenario.launch(MainMenuActivity.class);

        onView(allOf(withId(R.id.language_button), isDisplayed()));

        // Verify that the activity is displayed on the screen
        Espresso.onView(allOf(withId(R.id.language_button))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Click a button (assuming the button has an ID, change it accordingly)
        Espresso.onView(ViewMatchers.withId(R.id.language_button)).perform(ViewActions.click());

        // Wait for the next activity to be launched
        Intents.intended(IntentMatchers.hasComponent("com.serwylo.lexica.ChooseLexiconActivity"));

        // Get the resulting activity
        Activity resultActivity = getActivityInstance();
        ChooseLexiconActivity lexiconActivity = (ChooseLexiconActivity) resultActivity;

        // Perform assertions or verifications on the ResultActivity if needed
        // For example, you can check if certain views are displayed in the ResultActivity
        Espresso.onView(ViewMatchers.withId(R.id.toolbar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(MainMenuActivity.class.getName()));
    }

    @Test
    public void testMainMenuToMutliplayer() {
        ActivityScenario<MainMenuActivity> scenario = ActivityScenario.launch(MainMenuActivity.class);

        onView(allOf(withId(R.id.new_multiplayer_game), isDisplayed()));

        // Verify that the activity is displayed on the screen
        Espresso.onView(allOf(withId(R.id.new_multiplayer_game))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Click a button (assuming the button has an ID, change it accordingly)
        Espresso.onView(ViewMatchers.withId(R.id.new_multiplayer_game)).perform(ViewActions.click());

        // Wait for the next activity to be launched
        Intents.intended(IntentMatchers.hasComponent("com.serwylo.lexica.activities.NewMultiplayerActivity"));

        // Get the resulting activity
        Activity resultActivity = getActivityInstance();

        // Perform assertions or verifications on the ResultActivity if needed
        // For example, you can check if certain views are displayed in the ResultActivity
        Espresso.onView(ViewMatchers.withId(R.id.toolbar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(MainMenuActivity.class.getName()));
    }
    @Test
    public void testMainMenuToGameModeToMainMenu() {
        ActivityScenario<MainMenuActivity> scenario = ActivityScenario.launch(MainMenuActivity.class);

        onView(allOf(withId(R.id.game_mode_button), isDisplayed()));

        // Verify that the activity is displayed on the screen
        Espresso.onView(allOf(withId(R.id.game_mode_button))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Click a button (assuming the button has an ID, change it accordingly)
        Espresso.onView(ViewMatchers.withId(R.id.game_mode_button)).perform(ViewActions.click());

        // Wait for the next activity to be launched
        Intents.intended(IntentMatchers.hasComponent("com.serwylo.lexica.ChooseGameModeActivity"));

        // Get the resulting activity
        Activity resultActivity = getActivityInstance();

        // Perform assertions or verifications on the ResultActivity if needed
        // For example, you can check if certain views are displayed in the ResultActivity
        Espresso.onView(ViewMatchers.withId(R.id.toolbar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(MainMenuActivity.class.getName()));
    }
    @Test
    public void testMainMenuToHighScoreToMainMneu() {
        ActivityScenario<MainMenuActivity> scenario = ActivityScenario.launch(MainMenuActivity.class);

        onView(allOf(withId(R.id.high_score), isDisplayed()));

        // Verify that the activity is displayed on the screen
        Espresso.onView(allOf(withId(R.id.high_score))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Click a button (assuming the button has an ID, change it accordingly)
        Espresso.onView(ViewMatchers.withId(R.id.high_score)).perform(ViewActions.click());

        // Wait for the next activity to be launched
        Intents.intended(IntentMatchers.hasComponent("com.serwylo.lexica.activities.HighScoresActivity"));

        // Get the resulting activity
        Activity resultActivity = getActivityInstance();

        // Perform assertions or verifications on the ResultActivity if needed
        // For example, you can check if certain views are displayed in the ResultActivity
        Espresso.onView(ViewMatchers.withId(R.id.toolbar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(MainMenuActivity.class.getName()));
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
