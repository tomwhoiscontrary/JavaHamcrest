package org.hamcrest.collection;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.util.Map;

/**
 * Matches if map size satisfies a nested matcher.
 */
public final class IsMapWithSize<K, V> extends FeatureMatcher<Map<? extends K, ? extends V>, Integer> {
    public IsMapWithSize(Matcher<? super Integer> sizeMatcher) {
      super(sizeMatcher, "a map with size", "map size");
    }

    @Override
    protected Integer featureValueOf(Map<? extends K, ? extends V> actual) {
      return actual.size();
    }

}
