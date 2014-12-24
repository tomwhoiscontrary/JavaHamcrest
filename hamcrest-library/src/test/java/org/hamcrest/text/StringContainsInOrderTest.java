package org.hamcrest.text;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.text.MatchStrings.stringContainsInOrder;


public class StringContainsInOrderTest extends AbstractMatcherTest {
    Matcher<String> m = stringContainsInOrder("a", "b", "c", "c");

    @Override
    protected Matcher<?> createMatcher() {
        return m;
    }
    
    public void testMatchesOnlyIfStringContainsGivenSubstringsInTheSameOrder() {
        assertMatches("all substrings adjacent in order", m, "abcc");
        assertMatches("all substrings separated in order", m, "1a2b3c4c5");
        
        assertDoesNotMatch("cumulative substring length longer than string", m, "abc");
        assertDoesNotMatch("substrings out of order", m, "cabc");
        assertDoesNotMatch("no substrings in string", m, "wxyz");
        assertDoesNotMatch("substring not occurring", m, "axcc");
        assertDoesNotMatch("substring missing", m, "acc");
        assertDoesNotMatch("empty string", m, "");
    }
    
    public void testHasAReadableDescription() {
        assertDescription("a string containing \"a\", \"b\", \"c\", \"c\" in order", m);
    }
}
