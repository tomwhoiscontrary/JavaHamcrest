package org.junit.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * A matcher that delegates to throwableMatcher and in addition appends the
 * stacktrace of the actual Throwable in case of a mismatch.
 * @since 3.0
 */
public class StacktracePrintingMatcher<T extends Throwable> extends TypeSafeMatcher<T> {

    private final Matcher<T> throwableMatcher;

    public StacktracePrintingMatcher(Matcher<T> throwableMatcher) {
        this.throwableMatcher = throwableMatcher;
    }

    public void describeTo(Description description) {
        throwableMatcher.describeTo(description);
    }

    @Override
    protected boolean matchesSafely(T item) {
        return throwableMatcher.matches(item);
    }

    @Override
    protected void describeMismatchSafely(T item, Description description) {
        throwableMatcher.describeMismatch(item, description);
        description.appendText("\nStacktrace was: ");
        description.appendText(readStacktrace(item));
    }

    private String readStacktrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

}
