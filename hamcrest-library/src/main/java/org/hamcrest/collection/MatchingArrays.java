package org.hamcrest.collection;

import org.hamcrest.FunctionMatcher;
import org.hamcrest.FunctionMatcher.Feature;
import org.hamcrest.Matcher;
import org.hamcrest.internal.Wrapping;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.core.DescribedAs.describedAs;
import static org.hamcrest.core.IsEqual.equalTo;

public class MatchingArrays {
    /**
     * Creates a matcher that matches arrays whose elements are satisfied by the specified matchers.  Matches
     * positively only if the number of matchers specified is equal to the length of the examined array and
     * each matcher[i] is satisfied by array[i].
     * For example:
     * <pre>assertThat(new Integer[]{1,2,3}, is(array(equalTo(1), equalTo(2), equalTo(3))))</pre>
     *
     * @param elementMatchers the matchers that the elements of examined arrays should satisfy
     */
    @SafeVarargs
    public static <T> IsArray<T> array(Matcher<? super T>... elementMatchers) {
        return new IsArray<>(elementMatchers);
    }

    /**
     * Creates a matcher for arrays that matches when the examined array contains at least one item
     * that is matched by the specified <code>elementMatcher</code>.  Whilst matching, the traversal
     * of the examined array will stop as soon as a matching element is found.
     * For example:
     * <pre>assertThat(new String[] {"foo", "bar"}, hasItemInArray(startsWith("ba")))</pre>
     *
     * @param elementMatcher the matcher to apply to elements in examined arrays
     */
    public static <T> Matcher<T[]> hasItemInArray(Matcher<? super T> elementMatcher) {
        return new IsArrayContaining<>(elementMatcher);
    }

    /**
     * A shortcut to the frequently used <code>hasItemInArray(equalTo(x))</code>.
     * For example:
     * <pre>assertThat(hasItemInArray(x))</pre>
     * instead of:
     * <pre>assertThat(hasItemInArray(equalTo(x)))</pre>
     *
     * @param element the element that should be present in examined arrays
     */
    public static <T> Matcher<T[]> hasItemInArray(T element) {
        return hasItemInArray(equalTo(element));
    }

    /**
     * Creates a matcher for arrays that matches when each item in the examined array is
     * logically equal to the corresponding item in the specified items.  For a positive match,
     * the examined array must be of the same length as the number of specified items.
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, contains("foo", "bar"))</pre>
     *
     * @param items the items that must equal the items within an examined array
     */
    @SafeVarargs
    public static <E> Matcher<E[]> arrayContaining(E... items) {
        return arrayContaining(Wrapping.asEqualToMatchers(items));
    }

    /**
     * Creates a matcher for arrays that matches when each item in the examined array satisfies the
     * corresponding matcher in the specified matchers.  For a positive match, the examined array
     * must be of the same length as the number of specified matchers.
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, contains(equalTo("foo"), equalTo("bar")))</pre>
     *
     * @param itemMatchers the matchers that must be satisfied by the items in the examined array
     */
    @SafeVarargs
    public static <E> Matcher<E[]> arrayContaining(Matcher<? super E>... itemMatchers) {
        return arrayContaining(Wrapping.nullSafe(itemMatchers));
    }

    /**
     * Creates a matcher for arrays that matches when each item in the examined array satisfies the
     * corresponding matcher in the specified list of matchers.  For a positive match, the examined array
     * must be of the same length as the specified list of matchers.
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, contains(Arrays.asList(equalTo("foo"), equalTo("bar"))))</pre>
     *
     * @param itemMatchers a list of matchers, each of which must be satisfied by the corresponding item in an examined array
     */
    public static <E> Matcher<E[]> arrayContaining(List<Matcher<? super E>> itemMatchers) {
        return new IsArrayContainingInOrder<>(itemMatchers);
    }

    /**
     * <p>
     * Creates an order agnostic matcher for arrays that matches when each item in the
     * examined array satisfies one matcher anywhere in the specified matchers.
     * For a positive match, the examined array must be of the same length as the number of
     * specified matchers.
     * </p>
     * <p>
     * N.B. each of the specified matchers will only be used once during a given examination, so be
     * careful when specifying matchers that may be satisfied by more than one entry in an examined
     * array.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayContainingInAnyOrder(equalTo("bar"), equalTo("foo")))</pre>
     *
     * @param itemMatchers a list of matchers, each of which must be satisfied by an entry in an examined array
     */
    @SafeVarargs
    public static <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<? super E>... itemMatchers) {
        return arrayContainingInAnyOrder(Wrapping.nullSafe(itemMatchers));
    }

    /**
     * <p>
     * Creates an order agnostic matcher for arrays that matches when each item in the
     * examined array satisfies one matcher anywhere in the specified collection of matchers.
     * For a positive match, the examined array must be of the same length as the specified collection
     * of matchers.
     * </p>
     * <p>
     * N.B. each matcher in the specified collection will only be used once during a given
     * examination, so be careful when specifying matchers that may be satisfied by more than
     * one entry in an examined array.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayContainingInAnyOrder(Arrays.asList(equalTo("bar"), equalTo("foo"))))</pre>
     *
     * @param itemMatchers a list of matchers, each of which must be satisfied by an item provided by an examined array
     */
    public static <E> Matcher<E[]> arrayContainingInAnyOrder(Collection<Matcher<? super E>> itemMatchers) {
        return new IsArrayContainingInAnyOrder<>(itemMatchers);
    }

    /**
     * <p>Creates an order agnostic matcher for arrays that matches when each item in the
     * examined array is logically equal to one item anywhere in the specified items.
     * For a positive match, the examined array must be of the same length as the number of
     * specified items.
     * </p>
     * <p>N.B. each of the specified items will only be used once during a given examination, so be
     * careful when specifying items that may be equal to more than one entry in an examined
     * array.
     * </p>
     * <p>
     * For example:
     * </p>
     * <pre>assertThat(new String[]{"foo", "bar"}, containsInAnyOrder("bar", "foo"))</pre>
     *
     * @param items the items that must equal the entries of an examined array, in any order
     */
    @SafeVarargs
    public static <E> Matcher<E[]> arrayContainingInAnyOrder(E... items) {
        return arrayContainingInAnyOrder(Wrapping.asEqualToMatchers(items));
    }

    /**
     * Creates a matcher for arrays that matches when the <code>length</code> of the array
     * satisfies the specified matcher.
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayWithSize(equalTo(2)))</pre>
     *
     * @param sizeMatcher a matcher for the length of an examined array
     */
    public static <E> Matcher<E[]> arrayWithSize(Matcher<? super Integer> sizeMatcher) {
        return new FunctionMatcher<>(
            "an array with size", "array size",
            sizeMatcher,
            new Feature<E[], Integer>() { @Override public Integer from(E[] actual) { return actual.length; } });
    }

    /**
     * Creates a matcher for arrays that matches when the <code>length</code> of the array
     * equals the specified <code>size</code>.
     * For example:
     * <pre>assertThat(new String[]{"foo", "bar"}, arrayWithSize(2))</pre>
     *
     * @param size the length that an examined array must have for a positive match
     */
    public static <E> Matcher<E[]> arrayWithSize(int size) {
        return arrayWithSize(equalTo(size));
    }

    /**
     * Creates a matcher for arrays that matches when the <code>length</code> of the array
     * is zero.
     * For example:
     * <pre>assertThat(new String[0], emptyArray())</pre>
     */
    public static <E> Matcher<E[]> emptyArray() {
        return describedAs("an empty array", MatchingArrays.<E>arrayWithSize(0));
    }

}
