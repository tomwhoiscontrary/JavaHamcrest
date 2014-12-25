package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.Arrays;
import java.util.regex.Pattern;

import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.text.IsEmptyString.IS_EMPTY_STRING;

public class MatchStrings {
    private static final Pattern REGEX_WHITESPACE = Pattern.compile("\\s*");
    public static final Matcher<String> IS_BLANK_STRING = new MatchesPattern(REGEX_WHITESPACE) {
        @Override
        public void describeTo(Description description) {
            description.appendText("a blank string");
        }
    };
    public static final Matcher<String> IS_BLANK_OR_NULL_STRING = anyOf(IS_BLANK_STRING, nullValue());

    /**
     * Creates a matcher of {@link String} that matches when the examined string is equal to
     * the specified expectedString, ignoring case.
     * For example:
     * <pre>assertThat("Foo", equalToIgnoringCase("FOO"))</pre>
     *
     * @param expectedString the expected value of matched strings
     */
    public static Matcher<String> equalToIgnoringCase(java.lang.String expectedString) {
        return new IsEqualIgnoringCase(expectedString);
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is equal to
     * the specified expectedString, when whitespace differences are (mostly) ignored.  To be
     * exact, the following whitespace rules are applied:
     * <ul>
     * <li>all leading and trailing whitespace of both the expectedString and the examined string are ignored</li>
     * <li>any remaining whitespace, appearing within either string, is collapsed to a single space before comparison</li>
     * </ul>
     * For example:
     * <pre>assertThat("   my\tfoo  bar ", equalToIgnoringWhiteSpace(" my  foo bar"))</pre>
     *
     * @param expectedString the expected value of matched strings
     */
    public static Matcher<java.lang.String> equalToIgnoringWhiteSpace(java.lang.String expectedString) {
        return new IsEqualIgnoringWhiteSpace(expectedString);
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
     * has zero length.
     * For example:
     * <pre>assertThat(((String)null), is(emptyOrNullString()))</pre>
     */
    public static Matcher<java.lang.String> emptyOrNullString() {
        return Matchers.anyOf(nullValue(), IS_EMPTY_STRING);
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string has zero length.
     * For example:
     * <pre>assertThat("", is(emptyString()))</pre>
     */
    public static Matcher<java.lang.String> emptyString() {
        return IS_EMPTY_STRING;
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
     * contains zero or more whitespace characters and nothing else.
     * For example:
     * <pre>assertThat(((String)null), is(blankOrNullString()))</pre>
     */
    public static Matcher<java.lang.String> blankOrNullString() {
        return IS_BLANK_OR_NULL_STRING;
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string contains
     * zero or more whitespace characters and nothing else.
     * For example:
     * <pre>assertThat("  ", is(blankString()))</pre>
     */
    public static Matcher<java.lang.String> blankString() {
        return IS_BLANK_STRING;
    }

    /**
     * Creates a matcher of {@link java.lang.String} that matches when the examined string
     * exactly matches the given {@link java.util.regex.Pattern}.
     */
    public static Matcher<java.lang.String> matchesPattern(java.util.regex.Pattern pattern) {
        return new MatchesPattern(pattern);
    }

    /**
     * Creates a matcher of {@link java.lang.String} that matches when the examined string
     * exactly matches the given regular expression, treated as a {@link java.util.regex.Pattern}.
     */
    public static Matcher<java.lang.String> matchesPattern(java.lang.String regex) {
        return new MatchesPattern(Pattern.compile(regex));
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string contains all of
     * the specified substrings, considering the order of their appearance.
     * For example:
     * <pre>assertThat("myfoobarbaz", stringContainsInOrder(Arrays.asList("bar", "foo")))</pre>
     * fails as "foo" occurs before "bar" in the string "myfoobarbaz"
     *
     * @param substrings the substrings that must be contained within matching strings
     */
    public static Matcher<java.lang.String> stringContainsInOrder(java.lang.Iterable<java.lang.String> substrings) {
        return new StringContainsInOrder(substrings);
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string contains all of
     * the specified substrings, considering the order of their appearance.
     * For example:
     * <pre>assertThat("myfoobarbaz", stringContainsInOrder("bar", "foo"))</pre>
     * fails as "foo" occurs before "bar" in the string "myfoobarbaz"
     *
     * @param substrings the substrings that must be contained within matching strings
     */
    public static Matcher<java.lang.String> stringContainsInOrder(java.lang.String... substrings) {
        return new StringContainsInOrder(Arrays.asList(substrings));
    }
}
