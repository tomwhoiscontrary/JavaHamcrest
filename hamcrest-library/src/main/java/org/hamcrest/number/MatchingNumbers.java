package org.hamcrest.number;

import org.hamcrest.Matcher;

import java.math.BigDecimal;

public class MatchingNumbers {
    /**
     * Creates a matcher of {@link Double}s that matches when an examined double is equal
     * to the specified <code>operand</code>, within a range of +/- <code>error</code>.
     * For example:
     * <pre>assertThat(1.03, is(closeTo(1.0, 0.03)))</pre>
     *
     * @param operand the expected value of matching doubles
     * @param error   the delta (+/-) within which matches will be allowed
     */
    public static Matcher<Double> closeTo(double operand, double error) {
        return new IsCloseTo(operand, error);
    }

    /**
     * Creates a matcher of {@link Double}s that matches when an examined double is not a number.
     * For example:
     * <pre>assertThat(Double.NaN, is(notANumber()))</pre>
     */
    public static Matcher<Double> notANumber() {
        return new IsNaN();
    }

    /**
     * Creates a matcher of {@link BigDecimal}s that matches when an examined BigDecimal is equal
     * to the specified <code>operand</code>, within a range of +/- <code>error</code>. The comparison for equality
     * is done by BigDecimals {@link BigDecimal#compareTo(BigDecimal)} method.
     * For example:
     * <pre>assertThat(new BigDecimal("1.03"), is(closeTo(new BigDecimal("1.0"), new BigDecimal("0.03"))))</pre>
     *
     * @param operand the expected value of matching BigDecimals
     * @param error   the delta (+/-) within which matches will be allowed
     */
    public static Matcher<BigDecimal> closeTo(BigDecimal operand, BigDecimal error) {
        return new BigDecimalCloseTo(operand, error);
    }
}
