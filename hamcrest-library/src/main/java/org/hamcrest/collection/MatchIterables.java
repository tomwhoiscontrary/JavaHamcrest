package org.hamcrest.collection;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.Every;
import org.hamcrest.core.IsCollectionContaining;
import org.hamcrest.object.MatchObjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.internal.Wrapping.asEqualToMatchers;
import static org.hamcrest.internal.Wrapping.nullSafe;
import static org.hamcrest.object.MatchObjects.equalTo;

public class MatchIterables {
    /**
     * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
     * examined {@link Iterable} yields items that are all matched by the specified
     * <code>itemMatcher</code>.
     * For example:
     * <pre>assertThat(Arrays.asList("bar", "baz"), everyItem(startsWith("ba")))</pre>
     *
     * @param itemMatcher the matcher to apply to every item provided by the examined {@link Iterable}
     */
    public static <U> Matcher<Iterable<? extends U>> everyItem(Matcher<U> itemMatcher) {
        return new Every<>(itemMatcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
     * examined {@link Iterable} yields at least one item that is matched by the specified
     * <code>itemMatcher</code>.  Whilst matching, the traversal of the examined {@link Iterable}
     * will stop as soon as a matching item is found.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasItem(startsWith("ba")))</pre>
     *
     * @param itemMatcher the matcher to apply to items provided by the examined {@link Iterable}
     */
    public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> itemMatcher) {
        return new IsCollectionContaining<>(itemMatcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that only matches when a single pass over the
     * examined {@link Iterable} yields at least one item that is equal to the specified
     * <code>item</code>.  Whilst matching, the traversal of the examined {@link Iterable}
     * will stop as soon as a matching item is found.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), hasItem("bar"))</pre>
     *
     * @param item the item to compare against the items provided by the examined {@link Iterable}
     */
    public static <T> Matcher<Iterable<? super T>> hasItem(T item) {
        return new IsCollectionContaining<>(equalTo(item));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when consecutive passes over the
     * examined {@link Iterable} yield at least one item that is matched by the corresponding
     * matcher from the specified <code>itemMatchers</code>.  Whilst matching, each traversal of
     * the examined {@link Iterable} will stop as soon as a matching item is found.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems(endsWith("z"), endsWith("o")))</pre>
     *
     * @param itemMatchers the matchers to apply to items provided by the examined {@link Iterable}
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<T>> hasItems(Matcher<? super T>... itemMatchers) {
        List<Matcher<? super Iterable<T>>> all = new ArrayList<>(itemMatchers.length);

        for (Matcher<? super T> elementMatcher : itemMatchers) {
          all.add(new IsCollectionContaining<>(elementMatcher));
        }

        return Matchers.allOf(all);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when consecutive passes over the
     * examined {@link Iterable} yield at least one item that is equal to the corresponding
     * item from the specified <code>items</code>.  Whilst matching, each traversal of the
     * examined {@link Iterable} will stop as soon as a matching item is found.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems("baz", "foo"))</pre>
     *
     * @param items the items to compare against the items provided by the examined {@link Iterable}
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<T>> hasItems(T... items) {
        List<Matcher<? super Iterable<T>>> all = new ArrayList<>(items.length);
        for (T item : items) {
            all.add(new IsCollectionContaining<>(equalTo(item)));
        }

        return Matchers.allOf(all);
    }

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
    public static <E> Matcher<Collection<? extends E>> emptyCollectionOf(Class<E> unusedToForceReturnType) {
        return new IsEmptyCollection<>();
    }

    /**
     * Creates a matcher for {@link Iterable}s matching examined iterables that yield no items.
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyIterable()))</pre>
     */
    public static <E> Matcher<Iterable<? extends E>> emptyIterable() {
        return new IsEmptyIterable<>();
    }

    /**
     * Creates a matcher for {@link Iterable}s matching examined iterables that yield no items.
     * For example:
     * <pre>assertThat(new ArrayList&lt;String&gt;(), is(emptyIterableOf(String.class)))</pre>
     *
     * @param unusedToForceReturnType the type of the iterable's content
     */
    public static <E> Matcher<Iterable<? extends E>> emptyIterableOf(Class<E> unusedToForceReturnType) {
        return new IsEmptyIterable<>();
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each logically equal to the
     * corresponding item in the specified items.  For a positive match, the examined iterable
     * must be of the same length as the number of specified items.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains("foo", "bar"))</pre>
     *
     * @param items the items that must equal the items provided by an examined {@link Iterable}
     */
    @SafeVarargs
    public static <E> Matcher<Iterable<? extends E>> contains(E... items) {
        return new IsIterableContainingInOrder<>(asEqualToMatchers(items));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a single item that satisfies the specified matcher.
     * For a positive match, the examined iterable must only yield one item.
     * For example:
     * <pre>assertThat(Arrays.asList("foo"), contains(equalTo("foo")))</pre>
     *
     * @param itemMatcher the matcher that must be satisfied by the single item provided by an
     *                    examined {@link Iterable}
     */
    public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E> itemMatcher) {
        return new IsIterableContainingInOrder<>(Collections.<Matcher<? super E>>singletonList(itemMatcher));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each satisfying the corresponding
     * matcher in the specified matchers.  For a positive match, the examined iterable
     * must be of the same length as the number of specified matchers.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains(equalTo("foo"), equalTo("bar")))</pre>
     *
     * @param itemMatchers the matchers that must be satisfied by the items provided by an examined {@link Iterable}
     */
    @SafeVarargs
    public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E>... itemMatchers) {
        return new IsIterableContainingInOrder<>(nullSafe(itemMatchers));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, each satisfying the corresponding
     * matcher in the specified list of matchers.  For a positive match, the examined iterable
     * must be of the same length as the specified list of matchers.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), contains(Arrays.asList(equalTo("foo"), equalTo("bar"))))</pre>
     *
     * @param itemMatchers a list of matchers, each of which must be satisfied by the corresponding item provided by
     *                     an examined {@link Iterable}
     */
    public static <E> Matcher<Iterable<? extends E>> contains(List<Matcher<? super E>> itemMatchers) {
        return new IsIterableContainingInOrder<>(itemMatchers);
    }

    /**
     * <p>
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each satisfying one matcher anywhere
     * in the specified matchers.  For a positive match, the examined iterable must be of the same
     * length as the number of specified matchers.
     * </p>
     * <p>
     * N.B. each of the specified matchers will only be used once during a given examination, so be
     * careful when specifying matchers that may be satisfied by more than one entry in an examined
     * iterable.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder(equalTo("bar"), equalTo("foo")))</pre>
     *
     * @param itemMatchers a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T>... itemMatchers) {
        return new IsIterableContainingInAnyOrder<>(asList(itemMatchers));
    }

    /**
     * <p>
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each logically equal to one item
     * anywhere in the specified items. For a positive match, the examined iterable
     * must be of the same length as the number of specified items.
     * </p>
     * <p>
     * N.B. each of the specified items will only be used once during a given examination, so be
     * careful when specifying items that may be equal to more than one entry in an examined
     * iterable.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder("bar", "foo"))</pre>
     *
     * @param items the items that must equal the items provided by an examined {@link Iterable} in any order
     */
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T... items) {
        return new IsIterableContainingInAnyOrder<>(asEqualToMatchers(items));
    }

    /**
     * <p>
     * Creates an order agnostic matcher for {@link Iterable}s that matches when a single pass over
     * the examined {@link Iterable} yields a series of items, each satisfying one matcher anywhere
     * in the specified collection of matchers.  For a positive match, the examined iterable
     * must be of the same length as the specified collection of matchers.
     * </p>
     * <p>
     * N.B. each matcher in the specified collection will only be used once during a given
     * examination, so be careful when specifying matchers that may be satisfied by more than
     * one entry in an examined iterable.
     * </p>
     * <p>For example:</p>
     * <pre>assertThat(Arrays.asList("foo", "bar"), containsInAnyOrder(Arrays.asList(equalTo("bar"), equalTo("foo"))))</pre>
     *
     * @param itemMatchers a list of matchers, each of which must be satisfied by an item provided by an examined {@link Iterable}
     */
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> itemMatchers) {
        return new IsIterableContainingInAnyOrder<>(itemMatchers);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, that contains items logically equal to the
     * corresponding item in the specified items, in the same relative order
     * For example:
     * <pre>assertThat(Arrays.asList("a", "b", "c", "d", "e"), containsInRelativeOrder("b", "d"))</pre>
     *
     * @param items the items that must be contained within items provided by an examined {@link Iterable} in the same relative order
     */
    @SafeVarargs
    public static <E> Matcher<Iterable<? extends E>> containsInRelativeOrder(E... items) {
        return new IsIterableContainingInRelativeOrder<>(asEqualToMatchers(items));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, that each satisfying the corresponding
     * matcher in the specified matchers, in the same relative order.
     * For example:
     * <pre>assertThat(Arrays.asList("a", "b", "c", "d", "e"), containsInRelativeOrder(equalTo("b"), equalTo("d")))</pre>
     *
     * @param itemMatchers the matchers that must be satisfied by the items provided by an examined {@link Iterable} in the same relative order
     */
    @SafeVarargs
    public static <E> Matcher<Iterable<? extends E>> containsInRelativeOrder(Matcher<? super E>... itemMatchers) {
        return new IsIterableContainingInRelativeOrder<>(asList(itemMatchers));
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields a series of items, that contains items satisfying the corresponding
     * matcher in the specified list of matchers, in the same relative order.
     * For example:
     * <pre>assertThat(Arrays.asList("a", "b", "c", "d", "e"), contains(Arrays.asList(equalTo("b"), equalTo("d"))))</pre>
     *
     * @param itemMatchers a list of matchers, each of which must be satisfied by the items provided by
     *                     an examined {@link Iterable} in the same relative order
     */
    public static <E> Matcher<Iterable<? extends E>> containsInRelativeOrder(List<Matcher<? super E>> itemMatchers) {
        return new IsIterableContainingInRelativeOrder<>(itemMatchers);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields an item count that satisfies the specified
     * matcher.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(equalTo(2)))</pre>
     *
     * @param sizeMatcher a matcher for the number of items that should be yielded by an examined {@link Iterable}
     */
    public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<? super Integer> sizeMatcher) {
        return new IsIterableWithSize<>(sizeMatcher);
    }

    /**
     * Creates a matcher for {@link Iterable}s that matches when a single pass over the
     * examined {@link Iterable} yields an item count that is equal to the specified
     * <code>size</code> argument.
     * For example:
     * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(2))</pre>
     *
     * @param size the number of items that should be yielded by an examined {@link Iterable}
     */
    public static <E> Matcher<Iterable<E>> iterableWithSize(int size) {
        return IsIterableWithSize.iterableWithSize(equalTo(size));
    }

    public static <T> Matcher<Iterable<? extends T>> hasSubsequence(List<Matcher<? super T>> matchers) {
        return new HasSubsequence<>(matchers);
    }

    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> hasSubsequence(Matcher<? super T>... matchers) {
        return hasSubsequence(asList(matchers));
    }

    public static <T> Matcher<Iterable<? extends T>> hasSubsequence(Iterable<? extends T> items) {
        List<Matcher<? super T>> matchers = new ArrayList<>();
        for (Object item : items) {
            matchers.add(MatchObjects.equalTo(item));
        }
        return new HasSubsequence<>(matchers);
    }

    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> hasSubsequence(T... elements) {
        return hasSubsequence(asList(elements));
    }
}
