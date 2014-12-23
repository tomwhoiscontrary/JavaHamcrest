package org.hamcrest.text;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;

import static org.hamcrest.text.MatchStrings.stringContainsInOrder;


public class StringContainsInOrderTest extends AbstractMatcherTest {
    Matcher<String> m = stringContainsInOrder("a", "b", "c");

    @Override
    protected Matcher<?> createMatcher() {
        return m;
    }
    
    public void testMatchesOnlyIfStringContainsGivenSubstringsInTheSameOrder() {
        assertMatches("substrings in order", m, "abc");
        assertMatches("substrings separated", m, "1a2b3c4");
        
        assertDoesNotMatch("substrings out of order", m, "cab");
        assertDoesNotMatch("no substrings in string", m, "xyz");
        assertDoesNotMatch("substring missing", m, "ac");
        assertDoesNotMatch("empty string", m, "");
    }
    
    public void testHasAReadableDescription() {
        assertDescription("a string containing \"a\", \"b\", \"c\" in order", m);
    }
}
