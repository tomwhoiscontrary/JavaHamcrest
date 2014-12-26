package org.hamcrest.xml;

import javax.xml.xpath.XPathConstants;

import static javax.xml.xpath.XPathConstants.STRING;
import static org.hamcrest.xml.HasXPath.NO_NAMESPACE_CONTEXT;

public class MatchingXml {
    /**
     * Creates a matcher of {@link org.w3c.dom.Node}s that matches when the examined node has a value at the
     * specified <code>xPath</code> that satisfies the specified <code>valueMatcher</code>.
     * For example:
     * <pre>assertThat(xml, hasXPath("/root/something[2]/cheese", equalTo("Cheddar")))</pre>
     *
     * @param xPath        the target xpath
     * @param valueMatcher matcher for the value at the specified xpath
     */
    public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String xPath, org.hamcrest.Matcher<java.lang.String> valueMatcher) {
        return new HasXPath(xPath, NO_NAMESPACE_CONTEXT, valueMatcher, STRING);
    }

    /**
     * Creates a matcher of {@link org.w3c.dom.Node}s that matches when the examined node has a value at the
     * specified <code>xPath</code>, within the specified <code>namespaceContext</code>, that satisfies
     * the specified <code>valueMatcher</code>.
     * For example:
     * <pre>assertThat(xml, hasXPath("/root/something[2]/cheese", myNs, equalTo("Cheddar")))</pre>
     *
     * @param xPath            the target xpath
     * @param namespaceContext the namespace for matching nodes
     * @param valueMatcher     matcher for the value at the specified xpath
     */
    public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String xPath, javax.xml.namespace.NamespaceContext namespaceContext, org.hamcrest.Matcher<java.lang.String> valueMatcher) {
        return new HasXPath(xPath, namespaceContext, valueMatcher, STRING);
    }

    /**
     * Creates a matcher of {@link org.w3c.dom.Node}s that matches when the examined node contains a node
     * at the specified <code>xPath</code>, with any content.
     * For example:
     * <pre>assertThat(xml, hasXPath("/root/something[2]/cheese"))</pre>
     *
     * @param xPath the target xpath
     */
    public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String xPath) {
        return new HasXPath(xPath, NO_NAMESPACE_CONTEXT, HasXPath.WITH_ANY_CONTENT, XPathConstants.NODE);
    }

    /**
     * Creates a matcher of {@link org.w3c.dom.Node}s that matches when the examined node contains a node
     * at the specified <code>xPath</code> within the specified namespace context, with any content.
     * For example:
     * <pre>assertThat(xml, hasXPath("/root/something[2]/cheese", myNs))</pre>
     *
     * @param xPath            the target xpath
     * @param namespaceContext the namespace for matching nodes
     */
    public static org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String xPath, javax.xml.namespace.NamespaceContext namespaceContext) {
        return new HasXPath(xPath, namespaceContext, HasXPath.WITH_ANY_CONTENT, XPathConstants.NODE);
    }

}
