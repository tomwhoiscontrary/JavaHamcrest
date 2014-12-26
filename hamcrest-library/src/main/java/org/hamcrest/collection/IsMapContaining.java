package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Map;
import java.util.Map.Entry;

public class IsMapContaining<K,V> extends TypeSafeMatcher<Map<? extends K, ? extends V>> {
    private final Matcher<? super K> keyMatcher;
    private final Matcher<? super V> valueMatcher;

    public IsMapContaining(Matcher<? super K> keyMatcher, Matcher<? super V> valueMatcher) {
        this.keyMatcher = keyMatcher;
        this.valueMatcher = valueMatcher;
    }

    @Override
    public boolean matchesSafely(Map<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
            if (keyMatcher.matches(entry.getKey()) && valueMatcher.matches(entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void describeMismatchSafely(Map<? extends K, ? extends V> map, Description mismatchDescription) {
      mismatchDescription.appendText("map was ").appendValueList("[", ", ", "]", map.entrySet());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("map containing [")
                   .appendDescriptionOf(keyMatcher)
                   .appendText("->")
                   .appendDescriptionOf(valueMatcher)
                   .appendText("]");
    }

}
