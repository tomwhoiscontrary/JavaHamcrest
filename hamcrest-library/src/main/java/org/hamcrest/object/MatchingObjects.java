package org.hamcrest.object;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIn;
import org.hamcrest.comparator.ComparatorMatcherBuilder;
import org.hamcrest.core.*;

import java.util.EventObject;

import static org.hamcrest.core.IsNot.not;

public class MatchingObjects {
    /**
     * Creates a matcher that always matches, regardless of the examined object.
     */
    public static Matcher<Object> anything() {
        return new IsAnything<>();
    }

    /**
     * Creates a matcher that always matches, regardless of the examined object, but describes
     * itself with the specified {@link String}.
     *
     * @param description a meaningful {@link String} used when describing itself
     */
    public static Matcher<Object> anything(String description) {
        return new IsAnything<>(description);
    }

    /**
     * <p>Creates a matcher that matches when the examined object is logically equal to the specified
     * <code>operand</code>, as determined by calling the {@link Object#equals} method on
     * the <b>examined</b> object.</p>
     *
     * <p>If the specified operand is <code>null</code> then the created matcher will only match if
     * the examined object's <code>equals</code> method returns <code>true</code> when passed a
     * <code>null</code> (which would be a violation of the <code>equals</code> contract), unless the
     * examined object itself is <code>null</code>, in which case the matcher will return a positive
     * match.</p>
     *
     * <p>The created matcher provides a special behaviour when examining <code>Array</code>s, whereby
     * it will match if both the operand and the examined object are arrays of the same length and
     * contain items that are equal to each other (according to the above rules) <b>in the same
     * indexes</b>.</p>
     * For example:
     * <pre>
     * assertThat("foo", equalTo("foo"));
     * assertThat(new String[] {"foo", "bar"}, equalTo(new String[] {"foo", "bar"}));
     * </pre>
     */
    public static <T> Matcher<T> equalTo(T operand) {
        return new IsEqual<>(operand);
    }

    /**
     * Creates an {@link org.hamcrest.core.IsEqual} matcher that does not enforce the values being
     * compared to be of the same static type.
     */
    public static Matcher<Object> equalToObject(Object operand) {
        return new IsEqual<>(operand);
    }

    /**
     * <p>Creates a matcher that matches when the examined object is an instance of the specified <code>type</code>,
     * as determined by calling the {@link java.lang.Class#isInstance(Object)} method on that type, passing the
     * the examined object.</p>
     *
     * <p>The created matcher forces a relationship between specified type and the examined object, and should be
     * used when it is necessary to make generics conform, for example in the JMock clause
     * <code>with(any(Thing.class))</code></p>
     * For example:
     * <pre>assertThat(new Canoe(), instanceOf(Canoe.class));</pre>
     */
    public static <T> Matcher<T> any(Class<T> type) {
        //noinspection unchecked
        return (Matcher<T>) new IsInstanceOf(type);
    }

    /**
     * <p>Creates a matcher that matches when the examined object is an instance of the specified <code>type</code>,
     * as determined by calling the {@link java.lang.Class#isInstance(Object)} method on that type, passing the
     * the examined object.</p>
     *
     * <p>The created matcher assumes no relationship between specified type and the examined object.</p>
     * For example:
     * <pre>assertThat(new Canoe(), instanceOf(Paddlable.class));</pre>
     */
    public static <T> Matcher<T> instanceOf(java.lang.Class<?> type) {
        //noinspection unchecked
        return (Matcher<T>) new IsInstanceOf(type);
    }


    /**
     * A shortcut to the frequently used <code>not(nullValue())</code>.
     * For example:
     * <pre>assertThat(cheese, is(notNullValue()))</pre>
     * instead of:
     * <pre>assertThat(cheese, is(not(nullValue())))</pre>
     */
    public static Matcher<Object> notNullValue() {
        return not(nullValue());
    }

    /**
     * A shortcut to the frequently used <code>not(nullValue(X.class)). Accepts a
     * single dummy argument to facilitate type inference.</code>.
     * For example:
     * <pre>assertThat(cheese, is(notNullValue(X.class)))</pre>
     * instead of:
     * <pre>assertThat(cheese, is(not(nullValue(X.class))))</pre>
     *
     * @param type dummy parameter used to infer the generic type of the returned matcher
     */
    public static <T> Matcher<T> notNullValue(Class<T> type) {
        return not(nullValue(type));
    }

    /**
     * Creates a matcher that matches if examined object is <code>null</code>.
     * For example:
     * <pre>assertThat(cheese, is(nullValue())</pre>
     */
    public static Matcher<Object> nullValue() {
        return new IsNull<>();
    }

    /**
     * Creates a matcher that matches if examined object is <code>null</code>. Accepts a
     * single dummy argument to facilitate type inference.
     * For example:
     * <pre>assertThat(cheese, is(nullValue(Cheese.class))</pre>
     *
     * @param type dummy parameter used to infer the generic type of the returned matcher
     */
    @SuppressWarnings("UnusedParameters")
    public static <T> Matcher<T> nullValue(Class<T> type) {
        return new IsNull<>();
    }

    /**
     * Creates a matcher that matches only when the examined object is the same instance as
     * the specified target object.
     *
     * @param target the target instance against which others should be assessed
     */
    public static <T> Matcher<T> sameInstance(T target) {
        return new IsSame<>(target);
    }

    /**
     * Creates a matcher that matches when the examined object is found within the
     * specified collection.
     * For example:
     * <pre>assertThat("foo", in(Arrays.asList("bar", "foo")))</pre>
     *
     * @param collection the collection in which matching items must be found
     */
    public static <T> Matcher<T> in(java.util.Collection<T> collection) {
        return new IsIn<>(collection);
    }

    /**
     * Creates a matcher that matches when the examined object is found within the
     * specified array.
     * For example:
     * <pre>assertThat("foo", is(in(new String[]{"bar", "foo"})))</pre>
     *
     * @param elements the array in which matching items must be found
     */
    public static <T> Matcher<T> in(T[] elements) {
        return new IsIn<>(elements);
    }


    /**
     * Creates a matcher that matches when the examined object is equal to one of the
     * specified elements.
     * For example:
     * <pre>assertThat("foo", is(oneOf("bar", "foo")))</pre>
     *
     * @param elements the elements amongst which matching items will be found
     */
    @SafeVarargs
    public static <T> Matcher<T> oneOf(T... elements) {
        return new IsIn<>(elements);
    }

    /**
     * Creates a matcher of {@link Comparable} object that matches when the examined object is
     * equal to the specified value, as reported by the <code>compareTo</code> method of the
     * <b>examined</b> object.
     * For example:
     * <pre>assertThat(1, comparesEqualTo(1))</pre>
     *
     * @param value the value which, when passed to the compareTo method of the examined object, should return zero
     */
    public static <T extends Comparable<? super T>> Matcher<T> comparesEqualTo(T value) {
        return ComparatorMatcherBuilder.<T>usingNaturalOrdering().comparesEqualTo(value);
    }

    /**
     * Creates a matcher of {@link Comparable} object that matches when the examined object is
     * greater than the specified value, as reported by the <code>compareTo</code> method of the
     * <b>examined</b> object.
     * For example:
     * <pre>assertThat(2, greaterThan(1))</pre>
     *
     * @param value the value which, when passed to the compareTo method of the examined object, should return greater
     *              than zero
     */
    public static <T extends Comparable<? super T>> Matcher<T> greaterThan(T value) {
        return ComparatorMatcherBuilder.<T>usingNaturalOrdering().greaterThan(value);
    }

    /**
     * Creates a matcher of {@link Comparable} object that matches when the examined object is
     * greater than or equal to the specified value, as reported by the <code>compareTo</code> method
     * of the <b>examined</b> object.
     * For example:
     * <pre>assertThat(1, greaterThanOrEqualTo(1))</pre>
     *
     * @param value the value which, when passed to the compareTo method of the examined object, should return greater
     *              than or equal to zero
     */
    public static <T extends Comparable<? super T>> Matcher<T> greaterThanOrEqualTo(T value) {
        return ComparatorMatcherBuilder.<T>usingNaturalOrdering().greaterThanOrEqualTo(value);
    }

    /**
     * Creates a matcher of {@link Comparable} object that matches when the examined object is
     * less than the specified value, as reported by the <code>compareTo</code> method of the
     * <b>examined</b> object.
     * For example:
     * <pre>assertThat(1, lessThan(2))</pre>
     *
     * @param value the value which, when passed to the compareTo method of the examined object, should return less
     *              than zero
     */
    public static <T extends Comparable<? super T>> Matcher<T> lessThan(T value) {
        return ComparatorMatcherBuilder.<T>usingNaturalOrdering().lessThan(value);
    }

    /**
     * Creates a matcher of {@link Comparable} object that matches when the examined object is
     * less than or equal to the specified value, as reported by the <code>compareTo</code> method
     * of the <b>examined</b> object.
     * For example:
     * <pre>assertThat(1, lessThanOrEqualTo(1))</pre>
     *
     * @param value the value which, when passed to the compareTo method of the examined object, should return less
     *              than or equal to zero
     */
    public static <T extends Comparable<? super T>> Matcher<T> lessThanOrEqualTo(T value) {
        return ComparatorMatcherBuilder.<T>usingNaturalOrdering().lessThanOrEqualTo(value);
    }

    /**
     * Creates a matcher that matches any examined object whose <code>toString</code> method
     * returns a value that satisfies the specified matcher.
     * For example:
     * <pre>assertThat(true, hasToString(equalTo("TRUE")))</pre>
     *
     * @param toStringMatcher the matcher used to verify the toString result
     */
    public static <T> Matcher<T> hasToString(org.hamcrest.Matcher<? super String> toStringMatcher) {
        return new FeatureMatcher<T, String>(toStringMatcher, "with toString()", "toString()") {
            @Override protected String featureValueOf(T actual) { return String.valueOf(actual); }
        };
    }
    /**
     * Creates a matcher that matches any examined object whose <code>toString</code> method
     * returns a value equalTo the specified string.
     * For example:
     * <pre>assertThat(true, hasToString("TRUE"))</pre>
     *
     * @param expectedToString the expected toString result
     */
    public static <T> Matcher<T> hasToString(String expectedToString) {
        return hasToString(equalTo(expectedToString));
    }

    /**
     * Creates a matcher of {@link Class} that matches when the specified baseType is
     * assignable from the examined class.
     * For example:
     * <pre>assertThat(Integer.class, typeCompatibleWith(Number.class))</pre>
     *
     * @param baseType the base class to examine classes against
     */
    public static <T> org.hamcrest.Matcher<java.lang.Class<?>> typeCompatibleWith(Class<T> baseType) {
        return org.hamcrest.object.IsCompatibleType.typeCompatibleWith(baseType);
    }

    /**
     * Creates a matcher of {@link EventObject} that matches any object
     * derived from <var>eventClass</var> announced by <var>source</var>.
     * For example:
     * <pre>assertThat(myEvent, is(eventFrom(PropertyChangeEvent.class, myBean)))</pre>
     *
     * @param eventClass the class of the event to match on
     * @param source     the source of the event
     */
    public static Matcher<EventObject> eventFrom(java.lang.Class<? extends EventObject> eventClass, Object source) {
        return new IsEventFrom(eventClass, source);
    }

    /**
     * Creates a matcher of {@link EventObject} that matches any EventObject
     * announced by <var>source</var>.
     * For example:
     * <pre>assertThat(myEvent, is(eventFrom(myBean)))</pre>
     *
     * @param source the source of the event
     */
    public static Matcher<EventObject> eventFrom(Object source) {
        return new IsEventFrom(EventObject.class, source);
    }
}
