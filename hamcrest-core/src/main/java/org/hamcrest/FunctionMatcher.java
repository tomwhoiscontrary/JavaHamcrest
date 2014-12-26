package org.hamcrest;

import org.hamcrest.internal.ReflectiveTypeFinder;

/**
 * Supporting class for matching a feature of an object. Uses a given function to extract
 * the feature to be matched against.
 * In preparation for Java 8.
 *
 * @param <T> The type of the object to be matched
 * @param <U> The type of the feature to be matched
 */
public class FunctionMatcher<T, U> extends TypeSafeDiagnosingMatcher<T> {
  public static interface Feature<T, U> {
    U from(T actual);
  }


  private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("featureValueOf", 1, 0);
  private final Matcher<? super U> subMatcher;
  private final String featureDescription;
  private final String featureName;
  private final Feature<T, U> feature;

  /**
   * Constructor
   * @param featureDescription Descriptive text to use in describeTo
   * @param featureName Identifying text for mismatch message
   * @param subMatcher The matcher to apply to the feature
   * @param feature A function to extra the value to be matched against.
   */
  public FunctionMatcher(String featureDescription, String featureName,
      Matcher<? super U> subMatcher,
      Feature<T, U> feature) {
    super(TYPE_FINDER);
    this.subMatcher = subMatcher;
    this.featureDescription = featureDescription;
    this.featureName = featureName;
    this.feature = feature;
  }
  

  @Override
  protected boolean matchesSafely(T actual, Description mismatch) {
    final U featureValue = feature.from(actual);
    if (subMatcher.matches(featureValue)) {
      return true;
    }

    mismatch.appendText(featureName).appendText(" ");
    subMatcher.describeMismatch(featureValue, mismatch);
    return false;
  }
      
  @Override
  public final void describeTo(Description description) {
    description.appendText(featureDescription).appendText(" ")
               .appendDescriptionOf(subMatcher);
  }
}
