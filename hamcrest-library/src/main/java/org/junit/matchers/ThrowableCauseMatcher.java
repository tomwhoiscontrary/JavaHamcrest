package org.junit.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * A matcher that applies a delegate matcher to the cause of the given Throwable
 *
 * @param <T> the type of the throwable being matched
 * @since 3.0
 */
public class ThrowableCauseMatcher<T extends Throwable> extends TypeSafeMatcher<T> {

    private final Matcher<? extends Throwable> causeMatcher;

    public ThrowableCauseMatcher(Matcher<? extends Throwable> causeMatcher) {
        this.causeMatcher = causeMatcher;
    }

    public void describeTo(Description description) {
        description.appendText("throwable with cause ");
        description.appendDescriptionOf(causeMatcher);
    }

    @Override
    protected boolean matchesSafely(T item) {
        return causeMatcher.matches(item.getCause());
    }

    @Override
    protected void describeMismatchSafely(T item, Description description) {
        description.appendText("cause ");
        causeMatcher.describeMismatch(item.getCause(), description);
    }
}
