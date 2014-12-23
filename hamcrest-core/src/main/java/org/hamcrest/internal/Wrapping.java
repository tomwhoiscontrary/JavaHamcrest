package org.hamcrest.internal;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;

public class Wrapping {
    @SafeVarargs
    public static <E> List<Matcher<? super E>> asEqualToMatchers(E... items) {
        List<Matcher<? super E>> matchers = new ArrayList<>();
        for (E item : items) {
            matchers.add(equalTo(item));
        }
        return matchers;
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <E> List<Matcher<? super E>> nullSafe(Matcher<? super E>... itemMatchers) {
        final List<Matcher<? super E>> matchers = new ArrayList<>(itemMatchers.length);
        for (final Matcher<? super E> itemMatcher : itemMatchers) {
            matchers.add((Matcher<? super E>) (itemMatcher == null ? IsNull.nullValue() : itemMatcher));
        }
        return matchers;
    }

}
