package org.hamcrest.collection;

import org.hamcrest.Matcher;

import java.util.Map;

import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;

public class MatchingMaps {
    /**
     * Creates a matcher for {@link java.util.Map}s that matches when the <code>size()</code> method returns
     * a value that satisfies the specified matcher.
     * For example:
     * <pre>assertThat(myMap, is(aMapWithSize(equalTo(2))))</pre>
     *
     * @param sizeMatcher a matcher for the size of an examined {@link java.util.Map}
     */
    public static <K, V> Matcher<Map<? extends K, ? extends V>> aMapWithSize(Matcher<? super Integer> sizeMatcher) {
        return new IsMapWithSize<>(sizeMatcher);
    }

    /**
     * Creates a matcher for {@link java.util.Map}s that matches when the <code>size()</code> method returns
     * a value equal to the specified <code>size</code>.
     * For example:
     * <pre>assertThat(myMap, is(aMapWithSize(2)))</pre>
     *
     * @param size the expected size of an examined {@link java.util.Map}
     */
    public static <K, V> Matcher<Map<? extends K, ? extends V>> aMapWithSize(int size) {
        return aMapWithSize(equalTo(size));
    }

    /**
     * Creates a matcher for {@link java.util.Map}s that matches when the <code>size()</code> method returns
     * zero.
     * For example:
     * <pre>assertThat(myMap, is(anEmptyMap()))</pre>
     */
    public static <K, V> Matcher<Map<? extends K, ? extends V>> anEmptyMap() {
        return aMapWithSize(equalTo(0));
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one entry whose key satisfies the specified <code>keyMatcher</code> <b>and</b> whose
     * value satisfies the specified <code>valueMatcher</code>.
     * For example:
     * <pre>assertThat(myMap, hasEntry(equalTo("bar"), equalTo("foo")))</pre>
     *
     * @param keyMatcher   the key matcher that, in combination with the valueMatcher, must be satisfied by at least one entry
     * @param valueMatcher the value matcher that, in combination with the keyMatcher, must be satisfied by at least one entry
     */
    public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(Matcher<? super K> keyMatcher, Matcher<? super V> valueMatcher) {
        return new IsMapContaining<>(keyMatcher, valueMatcher);
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one entry whose key equals the specified <code>key</code> <b>and</b> whose value equals the
     * specified <code>value</code>.
     * For example:
     * <pre>assertThat(myMap, hasEntry("bar", "foo"))</pre>
     *
     * @param key   the key that, in combination with the value, must be describe at least one entry
     * @param value the value that, in combination with the key, must be describe at least one entry
     */
    public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(K key, V value) {
        return hasEntry(equalTo(key), equalTo(value));
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one key that satisfies the specified matcher.
     * For example:
     * <pre>assertThat(myMap, hasKey(equalTo("bar")))</pre>
     *
     * @param keyMatcher the matcher that must be satisfied by at least one key
     */
    public static <K> org.hamcrest.Matcher<Map<? extends K, ?>> hasKey(Matcher<? super K> keyMatcher) {
        return new IsMapContaining<>(keyMatcher, anything());
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one key that is equal to the specified key.
     * For example:
     * <pre>assertThat(myMap, hasKey("bar"))</pre>
     *
     * @param key the key that satisfying maps must contain
     */
    public static <K> org.hamcrest.Matcher<Map<? extends K, ?>> hasKey(K key) { return hasKey(equalTo(key)); }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one value that satisfies the specified valueMatcher.
     * For example:
     * <pre>assertThat(myMap, hasValue(equalTo("foo")))</pre>
     *
     * @param valueMatcher the matcher that must be satisfied by at least one value
     */
    public static <V> org.hamcrest.Matcher<Map<?, ? extends V>> hasValue(Matcher<? super V> valueMatcher) {
        return new IsMapContaining<>(anything(), valueMatcher);
    }

    /**
     * Creates a matcher for {@link java.util.Map}s matching when the examined {@link java.util.Map} contains
     * at least one value that is equal to the specified value.
     * For example:
     * <pre>assertThat(myMap, hasValue("foo"))</pre>
     *
     * @param value the value that satisfying maps must contain
     */
    public static <V> org.hamcrest.Matcher<Map<?, ? extends V>> hasValue(V value) {
        return hasValue(equalTo(value));
    }

}
