package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;
import java.util.regex.Pattern;

import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsNull.nullValue;

public class MatchStrings {
    public static final Matcher<String> IS_EMPTY_STRING = new TypeSafeMatcher<String>() {
        @Override public void describeTo(Description description) { description.appendText("an empty string"); }
        @Override protected boolean matchesSafely(String item) { return item.isEmpty(); }
    };
    public static final Matcher<String> IS_NULL_OR_EMPTY_STRING = anyOf(IS_EMPTY_STRING, nullValue());


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
    public static Matcher<String> equalToIgnoringCase(String expectedString) {
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
    public static Matcher<String> equalToIgnoringWhiteSpace(String expectedString) {
        return new IsEqualIgnoringWhiteSpace(expectedString);
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
     * has zero length.
     * For example:
     * <pre>assertThat(((String)null), is(emptyOrNullString()))</pre>
     */
    public static Matcher<String> emptyOrNullString() { return IS_NULL_OR_EMPTY_STRING; }

    /**
     * Creates a matcher of {@link String} that matches when the examined string has zero length.
     * For example:
     * <pre>assertThat("", is(emptyString()))</pre>
     */
    public static Matcher<String> emptyString() {
        return IS_EMPTY_STRING;
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string is <code>null</code>, or
     * contains zero or more whitespace characters and nothing else.
     * For example:
     * <pre>assertThat(((String)null), is(blankOrNullString()))</pre>
     */
    public static Matcher<String> blankOrNullString() { return IS_BLANK_OR_NULL_STRING; }

    /**
     * Creates a matcher of {@link String} that matches when the examined string contains
     * zero or more whitespace characters and nothing else.
     * For example:
     * <pre>assertThat("  ", is(blankString()))</pre>
     */
    public static Matcher<String> blankString() {
        return IS_BLANK_STRING;
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string
     * exactly matches the given {@link Pattern}.
     */
    public static Matcher<String> matchesPattern(Pattern pattern) {
        return new MatchesPattern(pattern);
    }

    /**
     * Creates a matcher of {@link String} that matches when the examined string
     * exactly matches the given regular expression, treated as a {@link Pattern}.
     */
    public static Matcher<String> matchesPattern(String regex) {
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
    public static Matcher<String> stringContainsInOrder(Iterable<String> substrings) {
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
    public static Matcher<String> stringContainsInOrder(String... substrings) {
        return new StringContainsInOrder(Arrays.asList(substrings));
    }
}
