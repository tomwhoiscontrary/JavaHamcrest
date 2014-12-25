package org.junit.matchers;

import org.hamcrest.Matcher;

/**
 * Matchers ported from JUnit 4
 * @since 3.0
 */
public class MatchThrowables {
  public static <T extends Throwable> Matcher<T> isThrowable(Matcher<T> throwableMatcher) {
      return new StacktracePrintingMatcher<>(throwableMatcher);
  }

  public static <T extends Exception> Matcher<T> isException(Matcher<T> exceptionMatcher) {
      return new StacktracePrintingMatcher<>(exceptionMatcher);
  }

  /**
   * Returns a matcher that verifies that the outer exception has a cause for which the supplied matcher
   * evaluates to true.
   *
   * @param matcher to apply to the cause of the outer exception
   * @param <T> type of the outer exception
   */
  public static <T extends Throwable> Matcher<T> hasCause(final Matcher<? extends Throwable> matcher) {
    return new ThrowableCauseMatcher<>(matcher);
  }

  /**
   * Returns a matcher that verifies that the outer exception has a message for which the supplied matcher
   * evaluates to true.
   *
   * @param matcher to apply to the cause of the outer exception
   * @param <T> type of the outer exception
   */
  public static <T extends Throwable> Matcher<T> hasMessage(final Matcher<String> matcher) {
      return new ThrowableMessageMatcher<>(matcher);
  }
}
