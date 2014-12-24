package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Matches if a collection contains another collection's items in
 * consecutive order anywhere inside it.
 */
public class HasSubsequence<T> extends TypeSafeDiagnosingMatcher<Iterable<? extends T>> {
    private final List<Matcher<? super T>> matchers;

    public HasSubsequence(List<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matchesSafely(Iterable<? extends T> actual,
                                 Description mismatchDescription) {
        final Cursor<T> cursor = new Cursor<>(actual.iterator(), matchers.iterator());

        final boolean matched = cursor.foundFirst() && cursor.foundRemainder();
        if (!matched) cursor.describeNotFound(mismatchDescription);
        return matched;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("iterable contains subsequence matching ")
                .appendList("[", ", ", "]", matchers);
    }

    static private class Cursor<T> {
        private final List<T> viewedItems = new ArrayList<>();
        private Matcher<? super T> currentMatcher;
        private final Iterator<? extends T> items;
        private final Iterator<Matcher<? super T>> remainingMatchers;

        public Cursor(Iterator<? extends T> items, Iterator<Matcher<? super T>> remainingMatchers) {
            this.items = items;
            this.remainingMatchers = remainingMatchers;
        }

        boolean foundFirst() {
            final Matcher<? super T> firstMatcher = nextMatcher();
            while (items.hasNext()) {
                if (firstMatcher.matches(nextItem())) return true;
            }
            return false;
        }


        boolean foundRemainder() {
            while (remainingMatchers.hasNext()) {
                if (! nextItemMatches(nextMatcher())) {
                    return false;
                }
            }
            return true;
        }

        private boolean nextItemMatches(Matcher<? super T> matcher) {
            return items.hasNext() && matcher.matches(nextItem());
        }

        private T nextItem() {
            final T next = items.next();
            viewedItems.add(next);
            return next;
        }

        private Matcher<? super T> nextMatcher() {
            currentMatcher = remainingMatchers.next();
            return currentMatcher;
        }

        private void describeNotFound(Description mismatch) {
            mismatch.appendText("subsequence not in ")
                .appendValueList("[", ", ", "]", viewedItems);

            if (null != currentMatcher) {
                mismatch.appendText(" because no match for ");
                mismatch.appendDescriptionOf(currentMatcher);
            }
        }
    }
}
