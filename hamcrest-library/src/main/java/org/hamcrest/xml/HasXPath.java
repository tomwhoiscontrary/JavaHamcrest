package org.hamcrest.xml;

import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsAnything;
import org.w3c.dom.Node;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import static javax.xml.xpath.XPathConstants.STRING;
import static org.hamcrest.Condition.matched;
import static org.hamcrest.Condition.notMatched;

/**
 * Applies a Matcher to a given XML Node in an existing XML Node tree, specified by an XPath expression.
 *
 * @author Joe Walnes
 * @author Steve Freeman
 */
public class HasXPath extends TypeSafeDiagnosingMatcher<Node> {
    public static final NamespaceContext NO_NAMESPACE_CONTEXT = null;
    public static final IsAnything<String> WITH_ANY_CONTENT = new IsAnything<>("");
    private static final Condition.Step<Object,String> NODE_EXISTS = nodeExists();
    private final Matcher<String> valueMatcher;
    private final XPathExpression compiledXPath;
    private final String xpathString;
    private final QName evaluationMode;

    /**
     * @param xPathExpression XPath expression.
     * @param valueMatcher Matcher to use at given XPath.
     *                     May be null to specify that the XPath must exist but the value is irrelevant.
     */
    public HasXPath(String xPathExpression, Matcher<String> valueMatcher) {
        this(xPathExpression, NO_NAMESPACE_CONTEXT, valueMatcher);
    }

    /**
     * @param xPathExpression XPath expression.
     * @param namespaceContext Resolves XML namespace prefixes in the XPath expression
     * @param valueMatcher Matcher to use at given XPath.
     *                     May be null to specify that the XPath must exist but the value is irrelevant.
     */
    public HasXPath(String xPathExpression, NamespaceContext namespaceContext, Matcher<String> valueMatcher) {
        this(xPathExpression, namespaceContext, valueMatcher, STRING);
    }

    public HasXPath(String xPathExpression, NamespaceContext namespaceContext, Matcher<String> valueMatcher, QName mode) {
        this.compiledXPath = compiledXPath(xPathExpression, namespaceContext);
        this.xpathString = xPathExpression;
        this.valueMatcher = valueMatcher;
        this.evaluationMode = mode;
    }

    @Override
    public boolean matchesSafely(Node item, Description mismatch) {
        return evaluated(item, mismatch)
               .and(NODE_EXISTS)
               .matching(valueMatcher);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an XML document with XPath ").appendText(xpathString);
        if (valueMatcher != null) {
            description.appendText(" ").appendDescriptionOf(valueMatcher);
        }
    }

    private Condition<Object> evaluated(Node item, Description mismatch) {
        try {
            return matched(compiledXPath.evaluate(item, evaluationMode), mismatch);
        } catch (XPathExpressionException e) {
            mismatch.appendText(e.getMessage());
        }
        return notMatched();
    }

    private static Condition.Step<Object, String> nodeExists() {
        return new Condition.Step<Object, String>() {
            @Override
            public Condition<String> apply(Object value, Description mismatch) {
                if (value == null) {
                    mismatch.appendText("xpath returned no results.");
                    return notMatched();
                }
                return matched(String.valueOf(value), mismatch);
            }
        };
    }

    private static XPathExpression compiledXPath(String xPathExpression, NamespaceContext namespaceContext) {
        try {
            final XPath xPath = XPathFactory.newInstance().newXPath();
            if (namespaceContext != null) {
                xPath.setNamespaceContext(namespaceContext);
            }
            return xPath.compile(xPathExpression);
        } catch (XPathExpressionException e) {
            throw new IllegalArgumentException("Invalid XPath : " + xPathExpression, e);
        }
    }
}
