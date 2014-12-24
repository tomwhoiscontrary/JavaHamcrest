package org.hamcrest.collection;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.MatchIterables.hasSubsequence;
import static org.hamcrest.core.IsEqual.equalTo;

public class HasSubsequenceTest extends AbstractMatcherTest {
    private final Matcher<Iterable<? extends WithValue>> contains123 = hasSubsequence(value(1), value(2), value(3));

    @Override
    protected Matcher<?> createMatcher() {
        return hasSubsequence(1, 2);
    }

    public void testMatchesIterableWithSameValues() throws Exception {
        assertMatches("Actual with single item",
            hasSubsequence(1), asList(1));
        assertMatches("Actual with multiple items",
                hasSubsequence(1, 2, 3), asList(1, 2, 3));
    }

    public void testMatchesIterableWithExcessElements() throws Exception {
        assertMatches("More elements at beginning",
                hasSubsequence(2, 3, 4), asList(1, 2, 3, 4));
        assertMatches("Sub section of actual", hasSubsequence(2, 3), asList(1, 2, 3, 4));
        assertMatches("More elements at end",
            hasSubsequence(1, 2, 3), asList(1, 2, 3, 4));
    }

    public void testMismatchesIfItemsMissing() throws Exception {
        assertMismatchDescription(
            "subsequence not in [<WithValue 3>] because no match for value with <4>",
            hasSubsequence(value(4)), asList(withValue(3)));
        assertMismatchDescription(
            "subsequence not in [<WithValue 1>, <WithValue 2>, <WithValue 4>] because no match for value with <3>",
            contains123, asList(withValue(1), withValue(2), withValue(4)));
    }


    public void testMismatchesForAdditionalIntermediateItems() throws Exception {
        assertMismatchDescription("subsequence not in [<1>, <2>] because no match for <3>",
                hasSubsequence(1, 3), asList(1, 2, 4));
    }


    public void testMismatchesWhenTooFewItems() throws Exception {
        assertMismatchDescription(
            "subsequence not in [<WithValue 1>, <WithValue 2>] because no match for value with <3>",
                contains123, asList(withValue(1), withValue(2)));
    }

    public void testDoesNotMatchEmptyCollection() throws Exception {
        assertMismatchDescription("subsequence not in [] because no match for value with <4>",
                hasSubsequence(value(4)), new ArrayList<WithValue>());
    }

    public void testHasAReadableDescription() {
        assertDescription("iterable contains subsequence matching [<1>, <2>]", hasSubsequence(1, 2));
    }

    public static class WithValue {
        private final int value;

        public WithValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "WithValue " + value;
        }
    }

    public static WithValue withValue(int value) {
        return new WithValue(value);
    }

    public static Matcher<WithValue> value(int value) {
        return new FeatureMatcher<WithValue, Integer>(equalTo(value), "value with", "value") {
            @Override
            protected Integer featureValueOf(WithValue actual) {
                return actual.getValue();
            }
        };
    }
}
