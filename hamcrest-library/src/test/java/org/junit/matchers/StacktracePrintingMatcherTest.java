package org.junit.matchers;

import org.junit.Test;

import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.object.MatchingObjects.*;
import static org.junit.Assert.*;
import static org.junit.matchers.MatchThrowables.isException;
import static org.junit.matchers.MatchThrowables.isThrowable;

public class StacktracePrintingMatcherTest {

    @Test
    public void succeedsWhenInnerMatcherSucceeds() throws Exception {
        assertTrue(isThrowable(any(Throwable.class)).matches(new Exception()));
    }

    @Test
    public void failsWhenInnerMatcherFails() throws Exception {
        assertFalse(isException(notNullValue(Exception.class)).matches(null));
    }

    @Test
    public void assertThatIncludesStacktrace() {
        Exception actual = new IllegalArgumentException("my message");
        Exception expected = new NullPointerException();

        try {
            assertThat(actual, isThrowable(equalTo(expected)));
        } catch (AssertionError e) {
            assertThat(e.getMessage(), containsString("Stacktrace was: java.lang.IllegalArgumentException: my message"));
        }
    }
}
