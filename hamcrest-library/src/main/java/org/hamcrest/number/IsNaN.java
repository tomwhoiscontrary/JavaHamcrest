/*  Copyright (c) 2000-2006 hamcrest.org
 */
package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;


/**
 * Is the value a number actually not a number (NaN)?
 */
public final class IsNaN extends TypeSafeMatcher<Double> {
    IsNaN() { }

    @Override
    public boolean matchesSafely(Double item) {
        return Double.isNaN(item);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a double value of NaN");
    }
}
