package org.junit.matchers;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.object.MatchObjects.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.MatchThrowables.hasCause;
import static org.junit.matchers.MatchThrowables.hasMessage;

public class ThrowableMatchersTest {

    @Test
    public void shouldAllowCauseOfDifferentClassFromRoot() throws Exception {
        NullPointerException expectedCause = new NullPointerException("expected");
        Exception actual = new Exception(expectedCause) {
            @Override public String getMessage() { return "the exception message"; }
        };

        assertThat(actual, hasCause(sameInstance(expectedCause)));
        assertThat(actual, hasMessage(equalTo("the exception message")));

    }
}
