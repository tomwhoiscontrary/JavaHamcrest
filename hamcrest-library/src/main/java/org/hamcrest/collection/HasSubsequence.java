package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
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
    public boolean matchesSafely(Iterable<? extends T> subsequenceToMatch,
                                 Description mismatchDescription)
    {
        List<T> subsequenceToMatchList = new ArrayList<>();
        for (T e : subsequenceToMatch) {
            subsequenceToMatchList.add(e);
        }

        for (int i = 0; i <= subsequenceToMatchList.size() - matchers.size(); i++) {
            boolean allMatchersMatched = true;
            for (int j = 0; j < matchers.size(); j++) {
                Matcher<? super T> matcher = matchers.get(j);
                if (!matcher.matches(subsequenceToMatchList.get(i + j))) {
                    allMatchersMatched = false;
                    break;
                }
            }
            if (allMatchersMatched) {
                return true;
            }
        }

        mismatchDescription
                .appendText("could not find subsequence inside collection ")
                .appendValueList("[", ", ", "]", subsequenceToMatch);
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("collection contains subsequence matching ")
                .appendList("[", ", ", "]", matchers);
    }
}
