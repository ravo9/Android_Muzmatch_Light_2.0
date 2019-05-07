package development.dreamcatcher.muzmatchlight;

import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.filters.LargeTest;
import androidx.test.internal.util.Checks;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import development.dreamcatcher.muzmatchlight.activities.ChatActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChatViewTest {

    @Rule
    public ActivityTestRule<ChatActivity> chatActivityTestRule =
            new ActivityTestRule<>(ChatActivity.class);

    // Custom matcher provided by Google (Google Codelab code)
    private Matcher<View> withItemText(final String itemText) {
        Checks.checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return allOf(
                        isDescendantOfA(isAssignableFrom(RecyclerView.class)),
                        withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA RV with text " + itemText);
            }
        };
    }

    @Test
    public void addMessageToChat() throws Exception {

        String newMessage = "My testing message!";

        // Type new message
        onView(withId(R.id.editText_messageInput)).perform(typeText(newMessage));

        // Send the message
        onView(withId(R.id.editText_messageInput)).perform(pressImeActionButton(), closeSoftKeyboard());

        // Verify note is displayed on screen
        onView(withItemText(newMessage)).check(matches(isDisplayed()));
    }
}