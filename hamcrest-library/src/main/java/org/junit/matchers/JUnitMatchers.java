package org.junit.matchers;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.collection.MatchIterables;
import org.hamcrest.core.CombinableMatcher.CombinableBothMatcher;
import org.hamcrest.core.CombinableMatcher.CombinableEitherMatcher;

/**
 * Transient import class. Copied here from JUnit 4.4 in preparation for removing Hamcrest from
 * JUnit dependencies.
 *
 * @deprecated
 * @since 3.0.0
 */
public class JUnitMatchers {
  /**
   * @return A matcher matching any collection containing element
   * @deprecated Please use {@link MatchIterables#hasItem(Object)} instead.
   */
  @Deprecated
  public static <T> Matcher<Iterable<? super T>> hasItem(T element) {
    return MatchIterables.hasItem(element);
  }

  /**
   * @return A matcher matching any collection containing an element matching elementMatcher
   * @deprecated Please use {@link MatchIterables#hasItem(Matcher)} instead.
   */
  @Deprecated
  public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> elementMatcher) {
    return MatchIterables.hasItem(elementMatcher);
  }

  /**
   * @return A matcher matching any collection containing every element in elements
   * @deprecated Please use {@link MatchIterables#hasItems(Object...)} instead.
   */
  @Deprecated
  public static <T> Matcher<Iterable<T>> hasItems(T... elements) {
    return MatchIterables.hasItems(elements);
  }

  /**
   * @return A matcher matching any collection containing at least one element that matches
   *         each matcher in elementMatcher (this may be one element matching all matchers,
   *         or different elements matching each matcher)
   * @deprecated Please use {@link MatchIterables#hasItems(Matcher...)} instead.
   */
  @Deprecated
  public static <T> Matcher<Iterable<T>> hasItems(Matcher<? super T>... elementMatchers) {
    return MatchIterables.hasItems(elementMatchers);
  }

  /**
   * @return A matcher matching any collection in which every element matches elementMatcher
   * @deprecated Please use {@link MatchIterables#everyItem(Matcher)} instead.
   */
  @Deprecated
  public static <T> Matcher<Iterable<? extends T>> everyItem(final Matcher<T> elementMatcher) {
    return MatchIterables.everyItem(elementMatcher);
  }

  /**
   * @return a matcher matching any string that contains substring
   * @deprecated Please use {@link CoreMatchers#containsString(String)} instead.
   */
  @Deprecated
  public static Matcher<String> containsString(String substring) {
    return CoreMatchers.containsString(substring);
  }

  /**
   * This is useful for fluently combining matchers that must both pass.  For example:
   * <pre>
   *   assertThat(string, both(containsString("a")).and(containsString("b")));
   * </pre>
   *
   * @deprecated Please use {@link CoreMatchers#both(Matcher)} instead.
   */
  @Deprecated
  public static <T> CombinableBothMatcher<T> both(Matcher<? super T> matcher) {
    return CoreMatchers.both(matcher);
  }

  /**
   * This is useful for fluently combining matchers where either may pass, for example:
   * <pre>
   *   assertThat(string, either(containsString("a")).or(containsString("b")));
   * </pre>
   *
   * @deprecated Please use {@link CoreMatchers#either(Matcher)} instead.
   */
  @Deprecated
  public static <T> CombinableEitherMatcher<T> either(Matcher<? super T> matcher) {
    return CoreMatchers.either(matcher);
  }

  /**
   * @return A matcher that delegates to throwableMatcher and in addition
   *         appends the stacktrace of the actual Throwable in case of a mismatch.
   */
  public static <T extends Throwable> Matcher<T> isThrowable(Matcher<T> throwableMatcher) {
    return MatchThrowables.isThrowable(throwableMatcher);
  }

  /**
   * @return A matcher that delegates to exceptionMatcher and in addition
   *         appends the stacktrace of the actual Exception in case of a mismatch.
   */
  public static <T extends Exception> Matcher<T> isException(Matcher<T> exceptionMatcher) {
    return MatchThrowables.isException(exceptionMatcher);
  }
}
