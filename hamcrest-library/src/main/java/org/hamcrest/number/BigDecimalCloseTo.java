package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalCloseTo extends TypeSafeMatcher<BigDecimal> {

  private final BigDecimal delta;
  private final BigDecimal value;

  public BigDecimalCloseTo(BigDecimal value, BigDecimal error) {
      this.delta = error;
      this.value = value;
  }

  @Override
  public boolean matchesSafely(BigDecimal item) {
      return actualDelta(item).compareTo(BigDecimal.ZERO) <= 0;
  }

  @Override
  public void describeMismatchSafely(BigDecimal item, Description mismatchDescription) {
      mismatchDescription.appendValue(item)
              .appendText(" differed by ")
              .appendValue(actualDelta(item))
              .appendText(" more than delta ")
              .appendValue(delta);
  }

  @Override
  public void describeTo(Description description) {
      description.appendText("a numeric value within ")
              .appendValue(delta)
              .appendText(" of ")
              .appendValue(value);
  }

  private BigDecimal actualDelta(BigDecimal item) {
      return item.subtract(value, MathContext.DECIMAL128).abs().subtract(delta, MathContext.DECIMAL128).stripTrailingZeros();
  }
}

