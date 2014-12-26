package org.hamcrest.beans;

public class MatchingBeans {
    /**
     * Creates a matcher that matches when the examined object has a JavaBean property
     * with the specified name.
     * For example:
     * <pre>assertThat(myBean, hasProperty("foo"))</pre>
     *
     * @param propertyName the name of the JavaBean property that examined beans should possess
     */
    public static <T> org.hamcrest.Matcher<T> hasProperty(java.lang.String propertyName) {
        return org.hamcrest.beans.HasProperty.hasProperty(propertyName);
    }

    /**
     * Creates a matcher that matches when the examined object has a JavaBean property
     * with the specified name whose value satisfies the specified matcher.
     * For example:
     * <pre>assertThat(myBean, hasProperty("foo", equalTo("bar"))</pre>
     *
     * @param propertyName the name of the JavaBean property that examined beans should possess
     * @param valueMatcher a matcher for the value of the specified property of the examined bean
     */
    public static <T> org.hamcrest.Matcher<T> hasProperty(java.lang.String propertyName, org.hamcrest.Matcher<?> valueMatcher) {
        return org.hamcrest.beans.HasPropertyWithValue.hasProperty(propertyName, valueMatcher);
    }

    /**
     * Creates a matcher that matches when the examined object has values for all of
     * its JavaBean properties that are equal to the corresponding values of the
     * specified bean.
     * For example:
     * <pre>assertThat(myBean, samePropertyValuesAs(myExpectedBean))</pre>
     *
     * @param expectedBean the bean against which examined beans are compared
     */
    public static <T> org.hamcrest.Matcher<T> samePropertyValuesAs(T expectedBean) {
        return org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs(expectedBean);
    }

}
