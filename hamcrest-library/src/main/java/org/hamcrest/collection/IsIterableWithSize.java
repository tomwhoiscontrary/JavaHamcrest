package org.hamcrest.collection;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class IsIterableWithSize<E> extends FeatureMatcher<Iterable<E>, Integer> {
    public IsIterableWithSize(Matcher<? super Integer> sizeMatcher) {
        super(sizeMatcher, "an iterable with size", "iterable size");
    }


    @Override
    protected Integer featureValueOf(Iterable<E> actual) {
        int size = 0;
        for (E e : actual) {
            size++;
        }
        return size;
    }
}
