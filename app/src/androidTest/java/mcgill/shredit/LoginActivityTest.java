package mcgill.shredit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
public class LoginActivityTest {

    private String username;
    private String validPassword;
    private String invalidPassword;

    @Rule
    public ActivityTestRule<LoginActivity> activityRule
            = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void initValidStrings() {
        username = "test";
        validPassword = "test";
        invalidPassword = "invalid";
    }

    @Test
    public void testSignIn_validInput() {
        // type in the username
        onView(withId(R.id.username))
                .perform(typeText(username), ViewActions.closeSoftKeyboard());
        // type in the valid password
        onView(withId(R.id.password))
                .perform(typeText(validPassword), ViewActions.closeSoftKeyboard());
        // click on the sign in button
        onView(withId(R.id.login_button))
                .perform(click());
        // check that the user was logged in
        onView(withId(R.id.home_button_searchGyms))
                .check(matches(ViewMatchers.isClickable()));
    }

    @Test
    public void testSignIn_invalidInput() {
        // type in the username
        onView(withId(R.id.username))
                .perform(typeText(username), ViewActions.closeSoftKeyboard());
        // type in the invalid password
        onView(withId(R.id.password))
                .perform(typeText(invalidPassword), ViewActions.closeSoftKeyboard());
        // click on the sign in button
        onView(withId(R.id.login_button))
                .perform(click());
        // check that the user was not logged in
        // TODO: verify what are the actual actions when a sign in is rejected
        onView(withId(R.id.login_button))
                .check(matches(ViewMatchers.isClickable()));
    }
}