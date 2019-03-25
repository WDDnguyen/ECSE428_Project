package mcgill.shredit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.action.ViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@LargeTest
public class CustomGymWorkoutTest {

    private String equipment;
    private String muscle;
    private String rep;
    private String workoutGenerated;
    private String workoutReplacement;

    @Before
    public void initTestStrings() {
        equipment = "none";
        muscle = "Abs";
        rep = "2";
        workoutGenerated = "test1";
        workoutReplacement = "test2";
    }

    @Rule
    public ActivityTestRule<CustomizeGymActivity> activityRule
            = new ActivityTestRule<>(CustomizeGymActivity.class);

    @Test
    public void normalScenario() {
        // select equipment
        onView(withText(equipment)).perform(click());

        // confirm equipment
        onView(withId(R.id.submitEquipmentBtn)).perform(click());

        // input wanted sets
        onView(allOf(isDescendantOfA(withId(R.id.muscle_group_table)), not(withText(muscle)), hasSibling(withText(muscle))))
                .perform(typeText(rep), ViewActions.closeSoftKeyboard());

        // select muscle group
        onView(allOf(isDescendantOfA(withId(R.id.muscle_group_table)), withText(muscle)))
                .check(matches(isNotChecked())).perform(click());

        // confirm muscle group
        onView(withId(R.id.muscle_group_confirm_button)).perform(click());
    }

    @Test
    public void alternateScenario() {
        // cannot delete exercises yet: switches to swap exercise

        // select equipment
        onView(withText(equipment)).perform(click());

        // confirm equipment
        onView(withId(R.id.submitEquipmentBtn)).perform(click());

        // input wanted sets
        onView(allOf(isDescendantOfA(withId(R.id.muscle_group_table)), not(withText(muscle)), hasSibling(withText(muscle))))
                .perform(typeText(rep), ViewActions.closeSoftKeyboard());

        // select muscle group
        onView(allOf(isDescendantOfA(withId(R.id.muscle_group_table)), withText(muscle)))
                .check(matches(isNotChecked())).perform(click());

        // confirm muscle group
        onView(withId(R.id.muscle_group_confirm_button)).perform(click());

        // select victim
        onView(withText(workoutGenerated)).perform(click());

        // swap with another
        onView(withText(workoutReplacement)).perform(click());
    }

    @Test
    public void errorScenario() {
        // Scenario change: Toast that prompts user error

        // elect equipment
        onView(withText(equipment)).perform(click());

        // confirm equipment
        onView(withId(R.id.submitEquipmentBtn)).perform(click());

        // forgets to select muscle group
        // confirm muscle group
        onView(withId(R.id.muscle_group_confirm_button)).perform(click());

        // confirm muscle group button should still be there
        onView(withId(R.id.muscle_group_confirm_button)).perform(click());
    }
}
