package org.junit.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * A matcher that applies a delegate matcher to the message of the given Throwable
 *
 * @param <T> the type of the throwable being matched
 * @since 3.0
 */
public class ThrowableMessageMatcher<T extends Throwable> extends TypeSafeMatcher<T> {

    private final Matcher<String> matcher;

    public ThrowableMessageMatcher(Matcher<String> matcher) {
        this.matcher = matcher;
    }

    public void describeTo(Description description) {
        description.appendText("exception with message ");
        description.appendDescriptionOf(matcher);
    }

    @Override
    protected boolean matchesSafely(T item) {
        return matcher.matches(item.getMessage());
    }

    @Override
    protected void describeMismatchSafely(T item, Description description) {
        description.appendText("message ");
        matcher.describeMismatch(item.getMessage(), description);
    }
}
