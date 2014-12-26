package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.List;

public class IsIterableContainingInRelativeOrder<E> extends TypeSafeDiagnosingMatcher<Iterable<? extends E>> {
    private final List<Matcher<? super E>> matchers;

    public IsIterableContainingInRelativeOrder(List<Matcher<? super E>> matchers) {
        this.matchers = matchers;
    }

    @Override
    protected boolean matchesSafely(Iterable<? extends E> iterable, Description mismatchDescription) {
        MatchSeriesInRelativeOrder<E> matchSeriesInRelativeOrder = new MatchSeriesInRelativeOrder<>(matchers, mismatchDescription);
        matchSeriesInRelativeOrder.processItems(iterable);
        return matchSeriesInRelativeOrder.isFinished();
    }

    public void describeTo(Description description) {
        description.appendText("iterable containing ").appendList("[", ", ", "]", matchers).appendText(" in relative order");
    }

    private static class MatchSeriesInRelativeOrder<F> {
        public final List<Matcher<? super F>> matchers;
        private final Description mismatchDescription;
        private int nextMatchIx = 0;
        private F lastMatchedItem = null;

        public MatchSeriesInRelativeOrder(List<Matcher<? super F>> matchers, Description mismatchDescription) {
            this.mismatchDescription = mismatchDescription;
            if (matchers.isEmpty()) {
                throw new IllegalArgumentException("Should specify at least one expected element");
            }
            this.matchers = matchers;
        }

        public void processItems(Iterable<? extends F> iterable) {
            for (F item : iterable) {
                if (nextMatchIx < matchers.size()) {
                    if (matchers.get(nextMatchIx).matches(item)) {
                        lastMatchedItem = item;
                        nextMatchIx++;
                    }
                }
            }
        }

        public boolean isFinished() {
            if (nextMatchIx < matchers.size()) {
                mismatchDescription.appendDescriptionOf(matchers.get(nextMatchIx)).appendText(" was not found");
                if (lastMatchedItem != null) {
                    mismatchDescription.appendText(" after ").appendValue(lastMatchedItem);
                }
                return false;
            }
            return true;
        }

    }
}
