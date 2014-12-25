
package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.core.CombinableMatcher.either;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Matches empty Strings (and null).
 */
public final class IsEmptyString extends TypeSafeMatcher<String> {
    public static final IsEmptyString IS_EMPTY_STRING = new IsEmptyString();
    public static final Matcher<String> IS_NULL_OR_EMPTY_STRING = either(IS_EMPTY_STRING).or(nullValue());

    private IsEmptyString() { }

    @Override
    public boolean matchesSafely(String item) {
        return item.equals("");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an empty string");
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string has zero length.
     * For example:
     * <pre>assertThat("", isEmptyString())</pre>
     * 
     * @deprecated see {@link org.hamcrest.text.MatchStrings.emptyString() }
     */
    @Deprecated
    public static Matcher<String> isEmptyString() {
        return emptyString();
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string has zero length.
     * For example:
     * <pre>assertThat("", is(emptyString()))</pre>
     * 
     */
    public static Matcher<String> emptyString() {
        return IS_EMPTY_STRING;
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
     * has zero length.
     * For example:
     * <pre>assertThat(((String)null), isEmptyOrNullString())</pre>
     * 
     * @deprecated use is(emptyOrNullString()) instead
     * 
     */
    @Deprecated
    public static Matcher<String> isEmptyOrNullString() {
        return emptyOrNullString();
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
     * has zero length.
     * For example:
     * <pre>assertThat(((String)null), is(emptyOrNullString()))</pre>
     * 
     */
    public static Matcher<String> emptyOrNullString() {
        return IS_NULL_OR_EMPTY_STRING;
    }
}
