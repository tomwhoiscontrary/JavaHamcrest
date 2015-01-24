package org.hamcrest.beans;

import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.beans.HasPropertyWithValue.Matchable;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.beans.PropertyUtil.NO_ARGUMENTS;

/**
 * <p>Matcher that asserts that a JavaBean property on an argument passed to the
 * mock object meets the provided matcher. This is useful for when objects
 * are created within code under test and passed to a mock object, and you wish
 * to assert that the created object has certain properties.
 * </p>
 *
 * <h2>Example Usage</h2>
 * Consider the situation where we have a class representing a person, which
 * follows the basic JavaBean convention of having get() and possibly set()
 * methods for it's properties:
 * <pre>
 * public class Person {
 *   private String name;
 *   public Person(String person) {
 *     this.person = person;
 *   }
 *   public String getName() {
 *     return name;
 *   }
 * }</pre>
 * 
 * And that these person objects are generated within a piece of code under test
 * (a class named PersonGenerator). This object is sent to one of our mock objects
 * which overrides the PersonGenerationListener interface:
 * <pre>
 * public interface PersonGenerationListener {
 *   public void personGenerated(Person person);
 * }</pre>
 * 
 * In order to check that the code under test generates a person with name
 * "Iain" we would do the following:
 * <pre>
 * Mock personGenListenerMock = mock(PersonGenerationListener.class);
 * personGenListenerMock.expects(once()).method("personGenerated").with(and(isA(Person.class), hasProperty("Name", eq("Iain")));
 * PersonGenerationListener listener = (PersonGenerationListener)personGenListenerMock.proxy();</pre>
 * 
 * <p>If an exception is thrown by the getter method for a property, the property
 * does not exist, is not readable, or a reflection related exception is thrown
 * when trying to invoke it then this is treated as an evaluation failure and
 * the matches method will return false.
 * </p>
 * <p>This matcher class will also work with JavaBean objects that have explicit
 * bean descriptions via an associated BeanInfo description class. See the
 * JavaBeans specification for more information:
 * http://java.sun.com/products/javabeans/docs/index.html
 * </p>
 *
 * @author Iain McGinniss
 * @author Nat Pryce
 * @author Steve Freeman
 */
public class HasPropertyWithValue<T> extends FeatureMatcher<T, Matchable> {
    private final String propertyName;

    public HasPropertyWithValue(String propertyName, Matcher<?> valueMatcher) {
        super(liftOverMatchable(valueMatcher), "has property \"" + propertyName + "\"", propertyName);
        this.propertyName = propertyName;
    }

    private static Matcher<Matchable> liftOverMatchable(final Matcher<?> matcher) {
        return new TypeSafeDiagnosingMatcher<Matchable>() {
            @Override
            protected boolean matchesSafely(Matchable item, Description mismatchDescription) {
                return item.matchWith(matcher, mismatchDescription);
            }

            @Override
            public void describeTo(Description description) {
                matcher.describeTo(description);
            }
        };
    }

    @Override
    protected Matchable featureValueOf(T actual) {
        PropertyDescriptor property = PropertyUtil.getPropertyDescriptor(propertyName, actual);
        if (null == property) return failure("is not a property");
        Method readMethod = property.getReadMethod();
        if (null == readMethod) return failure("is not readable");
        try {
            return value(readMethod.invoke(actual, NO_ARGUMENTS));
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            return failure(e.getMessage());
        }
    }

    interface Matchable {
        public boolean matchWith(Matcher<?> matcher, Description mismatchDescription);
    }

    private Matchable value(final Object value) {
        return new Matchable() {
            @Override
            public boolean matchWith(Matcher<?> matcher, Description mismatchDescription) {
                boolean matches = matcher.matches(value);
                if (!matches) matcher.describeMismatch(value, mismatchDescription);
                return matches;
            }
        };
    }

    private Matchable failure(final String reason) {
        return new Matchable() {
            @Override
            public boolean matchWith(Matcher<?> matcher, Description mismatchDescription) {
                mismatchDescription.appendText(reason);
                return false;
            }
        };
    }

    /**
     * Creates a matcher that matches when the examined object has a JavaBean property
     * with the specified name whose value satisfies the specified matcher.
     * For example:
     * <pre>assertThat(myBean, hasProperty("foo", equalTo("bar"))</pre>
     * 
     * @param propertyName
     *     the name of the JavaBean property that examined beans should possess
     * @param valueMatcher
     *     a matcher for the value of the specified property of the examined bean
     */
    public static <T> Matcher<T> hasProperty(String propertyName, Matcher<?> valueMatcher) {
        return new HasPropertyWithValue<T>(propertyName, valueMatcher);
    }
}
