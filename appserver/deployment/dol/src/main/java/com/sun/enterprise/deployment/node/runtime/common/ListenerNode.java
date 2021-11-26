package com.sun.enterprise.deployment.node.runtime.common;

import com.sun.enterprise.deployment.node.XMLElement;
import com.sun.enterprise.deployment.node.runtime.RuntimeDescriptorNode;
import com.sun.enterprise.deployment.runtime.common.Listener;
import com.sun.enterprise.deployment.xml.RuntimeTagNames;
import org.w3c.dom.Node;

/**
 * This node handles all the listener
 */
public class ListenerNode extends RuntimeDescriptorNode {

    @Override
    protected Listener createDescriptor() {
        return new Listener();
    }

    public ListenerNode() {
    }

    /**
     * receives notiification of the value for a particular tag
     *
     * @param element the xml element
     * @param value   it's associated value
     */
    public void setElementValue(XMLElement element, String value) {
        Listener listener = (Listener) getDescriptor();
        if (RuntimeTagNames.LISTENER_CLASS.equals(element.getQName())) {
            listener.setListenerClass(value);
        } else if (RuntimeTagNames.LISTENER_URI.equals(element.getQName())) {
            listener.setListenerUri(value);
        } else if (RuntimeTagNames.RUN_AS_PRINCIPAL_NAME.equals(element.getQName())) {
            listener.setRunAsPrincipalName(value);
        } else super.setElementValue(element, value);
    }

    /**
     * write the descriptor class to a DOM tree and return it
     *
     * @param parent     node for the DOM tree
     * @param nodeName   name
     * @param descriptor descriptor to write
     * @return the DOM tree top node
     */
    public Node writeDescriptor(Node parent, String nodeName, Listener descriptor) {
        Node listener = appendChild(parent, nodeName);

        //listener-class
        appendTextChild(listener, RuntimeTagNames.LISTENER_CLASS, descriptor.getListenerClass());

        //listener-uri
        appendTextChild(listener, RuntimeTagNames.LISTENER_URI, descriptor.getListenerUri());

        //run-as-principal-name
        appendTextChild(listener, RuntimeTagNames.RUN_AS_PRINCIPAL_NAME, descriptor.getRunAsPrincipalName());
        return listener;
    }
}
