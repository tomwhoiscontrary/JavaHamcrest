package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.Collection;

public class IsIterableContainingInAnyOrder<T> extends TypeSafeDiagnosingMatcher<Iterable<? extends T>> {
    private final Collection<Matcher<? super T>> matchers;

    public IsIterableContainingInAnyOrder(Collection<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }
    
    @Override
    protected boolean matchesSafely(Iterable<? extends T> items, Description mismatchDescription) {
      final Containing<T> containing = new Containing<>(matchers, mismatchDescription);
      for (T item : items) {
        if (! containing.matches(item)) {
          return false;
        }
      }
      
      return containing.isFinished(items);
    }
    
    @Override
    public void describeTo(Description description) {
      description.appendText("iterable with items ")
          .appendList("[", ", ", "]", matchers)
          .appendText(" in any order");
    }

    private static class Containing<S> {
      private final Collection<Matcher<? super S>> matchers;
      private final Description mismatchDescription;

      public Containing(Collection<Matcher<? super S>> matchers, Description mismatchDescription) {
        this.matchers = new ArrayList<>(matchers);
        this.mismatchDescription = mismatchDescription;
      }
      
      public boolean matches(S item) {
        if (matchers.isEmpty()) {
          mismatchDescription.appendText("no match for: ").appendValue(item);
          return false;
        }
        return isMatched(item);
      }

      public boolean isFinished(Iterable<? extends S> items) {
        if (matchers.isEmpty()) {
          return true;
        }
        mismatchDescription
          .appendText("no item matches: ").appendList("", ", ", "", matchers)
          .appendText(" in ").appendValueList("[", ", ", "]", items);
        return false;
      }

      private boolean isMatched(S item) {
        for (Matcher<? super S>  matcher : matchers) {
          if (matcher.matches(item)) {
            matchers.remove(matcher);
            return true;
          }
        }
        mismatchDescription.appendText("not matched: ").appendValue(item);
        return false;
      }
    }
}

