package org.hamcrest.collection;

import org.hamcrest.Matcher;

import java.util.Collection;

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
  @SuppressWarnings("unchecked")
  public static <E> Matcher<Collection<E>> emptyCollectionOf(Class<E> unusedToForceReturnType) {
      return (Matcher)empty();
  }
}
