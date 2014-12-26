package org.hamcrest.collection;

import org.hamcrest.Matcher;
import org.hamcrest.internal.Wrapping;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.object.MatchObjects.equalTo;

/**
 * @since 2.0
 */
public class MatchCollections {
  /**
   * Creates a matcher for {@link java.util.Collection}s that matches when the <code>size()</code> method returns
   * a value that satisfies the specified matcher.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar"), hasSize(equalTo(2)))</pre>
   *
   * @param sizeMatcher a matcher for the size of an examined {@link java.util.Collection}
   */
  public static <E> Matcher<Collection<? extends E>> hasSize(Matcher<? super Integer> sizeMatcher) {
      return new IsCollectionWithSize<>(sizeMatcher);
  }

  /**
   * Creates a matcher for {@link java.util.Collection}s that matches when the <code>size()</code> method returns
   * a value equal to the specified <code>size</code>.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar"), hasSize(2))</pre>
   *
   * @param size the expected size of an examined {@link java.util.Collection}
   */
  public static <E> Matcher<Collection<? extends E>> hasSize(int size) {
      return hasSize(equalTo(size));
  }

  /**
   * Creates a matcher for {@link java.util.Collection}s matching examined collections whose <code>isEmpty</code>
   * method returns <code>true</code>.
   * For example:
   * <pre>assertThat(new ArrayList&lt;String&gt;(), is(empty()))</pre>
   */
  public static <E> Matcher<Collection<? extends E>> empty() {
      return new IsEmptyCollection<>();
  }

  /**
   * Creates a matcher for {@link java.util.Collection}s matching examined collections whose <code>isEmpty</code>
   * method returns <code>true</code>.
   * For example:
   * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyCollectionOf(String.class)))</pre>
   *
   * @param unusedToForceReturnType the type of the collection's content
   */
  @SuppressWarnings({"unchecked", "UnusedParameters"})
  public static <E> Matcher<Collection<E>> emptyCollectionOf(Class<E> unusedToForceReturnType) {
      return (Matcher)empty();
  }

  /**
   * Creates a matcher for {@link Iterable}s that matches when a single pass over the
   * examined {@link Iterable} yields a series of items, each logically equal to the
   * corresponding item in the specified items.  For a positive match, the examined iterable
   * must be of the same length as the number of specified items.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar"), contains("foo", "bar"))</pre>
   *
   * @param items
   *     the items that must equal the items provided by an examined {@link Iterable}
   */
  @SafeVarargs
  public static <E> Matcher<Iterable<? extends E>> contains(E... items) {
      return contains(Wrapping.asEqualToMatchers(items));
  }

  /**
   * Creates a matcher for {@link Iterable}s that matches when a single pass over the
   * examined {@link Iterable} yields a series of items, each satisfying the corresponding
   * matcher in the specified matchers.  For a positive match, the examined iterable
   * must be of the same length as the number of specified matchers.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar"), contains(equalTo("foo"), equalTo("bar")))</pre>
   *
   * @param itemMatchers
   *     the matchers that must be satisfied by the items provided by an examined {@link Iterable}
   */
  @SafeVarargs
  public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E>... itemMatchers) {
      return contains(Wrapping.nullSafe(itemMatchers));
  }

  /**
   * Creates a matcher for {@link Iterable}s that matches when a single pass over the
   * examined {@link Iterable} yields a series of items, each satisfying the corresponding
   * matcher in the specified list of matchers.  For a positive match, the examined iterable
   * must be of the same length as the specified list of matchers.
   * For example:
   * <pre>assertThat(Arrays.asList("foo", "bar"), contains(Arrays.asList(equalTo("foo"), equalTo("bar"))))</pre>
   *
   * @param itemMatchers
   *     a list of matchers, each of which must be satisfied by the corresponding item provided by
   *     an examined {@link Iterable}
   */
  public static <E> Matcher<Iterable<? extends E>> contains(List<Matcher<? super E>> itemMatchers) {
      return new IsIterableContainingInOrder<>(itemMatchers);
  }
}
