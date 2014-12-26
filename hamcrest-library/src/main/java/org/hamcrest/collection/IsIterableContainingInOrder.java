package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.List;

public class IsIterableContainingInOrder<E> extends TypeSafeDiagnosingMatcher<Iterable<? extends E>> {
    private final List<Matcher<? super E>> matchers;

    public IsIterableContainingInOrder(List<Matcher<? super E>> matchers) {
        this.matchers = matchers;
    }

    @Override
    protected boolean matchesSafely(Iterable<? extends E> iterable, Description mismatchDescription) {
        final MatchInOrder<E> inOrder = new MatchInOrder<>(matchers, mismatchDescription);
        for (E item : iterable) {
            if (!inOrder.matches(item)) {
                return false;
            }
        }

        return inOrder.isFinished();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("iterable containing ").appendList("[", ", ", "]", matchers);
    }

    private static class MatchInOrder<F> {
        private final List<Matcher<? super F>> matchers;
        private final Description mismatchDescription;
        private int nextMatchIx = 0;

        public MatchInOrder(List<Matcher<? super F>> matchers, Description mismatchDescription) {
            this.mismatchDescription = mismatchDescription;
            if (matchers.isEmpty()) {
                throw new IllegalArgumentException("Should specify at least one expected element");
            }
            this.matchers = matchers;
        }

        public boolean matches(F item) {
          if (matchers.size() <= nextMatchIx) {
            mismatchDescription.appendText("not matched: ").appendValue(item);
            return false;
          }

          return isMatched(item);
        }

        public boolean isFinished() {
            if (nextMatchIx < matchers.size()) {
                mismatchDescription.appendText("no item was ").appendDescriptionOf(matchers.get(nextMatchIx));
                return false;
            }
            return true;
        }

        private boolean isMatched(F item) {
            final Matcher<? super F> matcher = matchers.get(nextMatchIx);
            if (!matcher.matches(item)) {
                describeMismatch(matcher, item);
                return false;
            }
            nextMatchIx++;
            return true;
        }

      private void describeMismatch(Matcher<? super F> matcher, F item) {
            mismatchDescription.appendText("item " + nextMatchIx + ": ");
            matcher.describeMismatch(item, mismatchDescription);
        }
    }
}
