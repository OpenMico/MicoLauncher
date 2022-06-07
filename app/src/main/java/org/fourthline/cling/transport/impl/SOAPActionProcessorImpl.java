package org.fourthline.cling.transport.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import org.apache.commons.lang3.StringUtils;
import org.fourthline.cling.model.Constants;
import org.fourthline.cling.model.UnsupportedDataException;
import org.fourthline.cling.model.XMLUtil;
import org.fourthline.cling.model.action.ActionArgumentValue;
import org.fourthline.cling.model.action.ActionException;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.control.ActionMessage;
import org.fourthline.cling.model.message.control.ActionRequestMessage;
import org.fourthline.cling.model.message.control.ActionResponseMessage;
import org.fourthline.cling.model.meta.ActionArgument;
import org.fourthline.cling.model.types.ErrorCode;
import org.fourthline.cling.model.types.InvalidValueException;
import org.fourthline.cling.transport.spi.SOAPActionProcessor;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/* loaded from: classes5.dex */
public class SOAPActionProcessorImpl implements SOAPActionProcessor, ErrorHandler {
    private static Logger log = Logger.getLogger(SOAPActionProcessor.class.getName());

    protected DocumentBuilderFactory createDocumentBuilderFactory() throws FactoryConfigurationError {
        return DocumentBuilderFactory.newInstance();
    }

    @Override // org.fourthline.cling.transport.spi.SOAPActionProcessor
    public void writeBody(ActionRequestMessage actionRequestMessage, ActionInvocation actionInvocation) throws UnsupportedDataException {
        Logger logger = log;
        logger.fine("Writing body of " + actionRequestMessage + " for: " + actionInvocation);
        try {
            DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
            newInstance.setNamespaceAware(true);
            Document newDocument = newInstance.newDocumentBuilder().newDocument();
            writeBodyRequest(newDocument, writeBodyElement(newDocument), actionRequestMessage, actionInvocation);
            if (log.isLoggable(Level.FINER)) {
                log.finer("===================================== SOAP BODY BEGIN ============================================");
                log.finer(actionRequestMessage.getBodyString());
                log.finer("-===================================== SOAP BODY END ============================================");
            }
        } catch (Exception e) {
            throw new UnsupportedDataException("Can't transform message payload: " + e, e);
        }
    }

    @Override // org.fourthline.cling.transport.spi.SOAPActionProcessor
    public void writeBody(ActionResponseMessage actionResponseMessage, ActionInvocation actionInvocation) throws UnsupportedDataException {
        Logger logger = log;
        logger.fine("Writing body of " + actionResponseMessage + " for: " + actionInvocation);
        try {
            DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
            newInstance.setNamespaceAware(true);
            Document newDocument = newInstance.newDocumentBuilder().newDocument();
            Element writeBodyElement = writeBodyElement(newDocument);
            if (actionInvocation.getFailure() != null) {
                writeBodyFailure(newDocument, writeBodyElement, actionResponseMessage, actionInvocation);
            } else {
                writeBodyResponse(newDocument, writeBodyElement, actionResponseMessage, actionInvocation);
            }
            if (log.isLoggable(Level.FINER)) {
                log.finer("===================================== SOAP BODY BEGIN ============================================");
                log.finer(actionResponseMessage.getBodyString());
                log.finer("-===================================== SOAP BODY END ============================================");
            }
        } catch (Exception e) {
            throw new UnsupportedDataException("Can't transform message payload: " + e, e);
        }
    }

    @Override // org.fourthline.cling.transport.spi.SOAPActionProcessor
    public void readBody(ActionRequestMessage actionRequestMessage, ActionInvocation actionInvocation) throws UnsupportedDataException {
        Logger logger = log;
        logger.fine("Reading body of " + actionRequestMessage + " for: " + actionInvocation);
        if (log.isLoggable(Level.FINER)) {
            log.finer("===================================== SOAP BODY BEGIN ============================================");
            log.finer(actionRequestMessage.getBodyString());
            log.finer("-===================================== SOAP BODY END ============================================");
        }
        String messageBody = getMessageBody(actionRequestMessage);
        try {
            DocumentBuilderFactory createDocumentBuilderFactory = createDocumentBuilderFactory();
            createDocumentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder newDocumentBuilder = createDocumentBuilderFactory.newDocumentBuilder();
            newDocumentBuilder.setErrorHandler(this);
            Document parse = newDocumentBuilder.parse(new InputSource(new StringReader(messageBody)));
            readBodyRequest(parse, readBodyElement(parse), actionRequestMessage, actionInvocation);
        } catch (Exception e) {
            throw new UnsupportedDataException("Can't transform message payload: " + e, e, messageBody);
        }
    }

    @Override // org.fourthline.cling.transport.spi.SOAPActionProcessor
    public void readBody(ActionResponseMessage actionResponseMessage, ActionInvocation actionInvocation) throws UnsupportedDataException {
        Logger logger = log;
        logger.fine("Reading body of " + actionResponseMessage + " for: " + actionInvocation);
        if (log.isLoggable(Level.FINER)) {
            log.finer("===================================== SOAP BODY BEGIN ============================================");
            log.finer(actionResponseMessage.getBodyString());
            log.finer("-===================================== SOAP BODY END ============================================");
        }
        String messageBody = getMessageBody(actionResponseMessage);
        try {
            DocumentBuilderFactory createDocumentBuilderFactory = createDocumentBuilderFactory();
            createDocumentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder newDocumentBuilder = createDocumentBuilderFactory.newDocumentBuilder();
            newDocumentBuilder.setErrorHandler(this);
            Document parse = newDocumentBuilder.parse(new InputSource(new StringReader(messageBody)));
            Element readBodyElement = readBodyElement(parse);
            ActionException readBodyFailure = readBodyFailure(parse, readBodyElement);
            if (readBodyFailure == null) {
                readBodyResponse(parse, readBodyElement, actionResponseMessage, actionInvocation);
            } else {
                actionInvocation.setFailure(readBodyFailure);
            }
        } catch (Exception e) {
            throw new UnsupportedDataException("Can't transform message payload: " + e, e, messageBody);
        }
    }

    protected void writeBodyFailure(Document document, Element element, ActionResponseMessage actionResponseMessage, ActionInvocation actionInvocation) throws Exception {
        writeFaultElement(document, element, actionInvocation);
        actionResponseMessage.setBody(toString(document));
    }

    protected void writeBodyRequest(Document document, Element element, ActionRequestMessage actionRequestMessage, ActionInvocation actionInvocation) throws Exception {
        writeActionInputArguments(document, writeActionRequestElement(document, element, actionRequestMessage, actionInvocation), actionInvocation);
        actionRequestMessage.setBody(toString(document));
    }

    protected void writeBodyResponse(Document document, Element element, ActionResponseMessage actionResponseMessage, ActionInvocation actionInvocation) throws Exception {
        writeActionOutputArguments(document, writeActionResponseElement(document, element, actionResponseMessage, actionInvocation), actionInvocation);
        actionResponseMessage.setBody(toString(document));
    }

    protected ActionException readBodyFailure(Document document, Element element) throws Exception {
        return readFaultElement(element);
    }

    protected void readBodyRequest(Document document, Element element, ActionRequestMessage actionRequestMessage, ActionInvocation actionInvocation) throws Exception {
        readActionInputArguments(readActionRequestElement(element, actionRequestMessage, actionInvocation), actionInvocation);
    }

    protected void readBodyResponse(Document document, Element element, ActionResponseMessage actionResponseMessage, ActionInvocation actionInvocation) throws Exception {
        readActionOutputArguments(readActionResponseElement(element, actionInvocation), actionInvocation);
    }

    protected Element writeBodyElement(Document document) {
        Element createElementNS = document.createElementNS(Constants.SOAP_NS_ENVELOPE, "s:Envelope");
        Attr createAttributeNS = document.createAttributeNS(Constants.SOAP_NS_ENVELOPE, "s:encodingStyle");
        createAttributeNS.setValue(Constants.SOAP_URI_ENCODING_STYLE);
        createElementNS.setAttributeNode(createAttributeNS);
        document.appendChild(createElementNS);
        Element createElementNS2 = document.createElementNS(Constants.SOAP_NS_ENVELOPE, "s:Body");
        createElementNS.appendChild(createElementNS2);
        return createElementNS2;
    }

    protected Element readBodyElement(Document document) {
        Element documentElement = document.getDocumentElement();
        if (documentElement == null || !getUnprefixedNodeName(documentElement).equals("Envelope")) {
            throw new RuntimeException("Response root element was not 'Envelope'");
        }
        NodeList childNodes = documentElement.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() == 1 && getUnprefixedNodeName(item).equals("Body")) {
                return (Element) item;
            }
        }
        throw new RuntimeException("Response envelope did not contain 'Body' child element");
    }

    protected Element writeActionRequestElement(Document document, Element element, ActionRequestMessage actionRequestMessage, ActionInvocation actionInvocation) {
        Logger logger = log;
        logger.fine("Writing action request element: " + actionInvocation.getAction().getName());
        String actionNamespace = actionRequestMessage.getActionNamespace();
        Element createElementNS = document.createElementNS(actionNamespace, "u:" + actionInvocation.getAction().getName());
        element.appendChild(createElementNS);
        return createElementNS;
    }

    protected Element readActionRequestElement(Element element, ActionRequestMessage actionRequestMessage, ActionInvocation actionInvocation) {
        NodeList childNodes = element.getChildNodes();
        Logger logger = log;
        logger.fine("Looking for action request element matching namespace:" + actionRequestMessage.getActionNamespace());
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                String unprefixedNodeName = getUnprefixedNodeName(item);
                if (unprefixedNodeName.equals(actionInvocation.getAction().getName())) {
                    if (item.getNamespaceURI() == null || !item.getNamespaceURI().equals(actionRequestMessage.getActionNamespace())) {
                        throw new UnsupportedDataException("Illegal or missing namespace on action request element: " + item);
                    }
                    Logger logger2 = log;
                    logger2.fine("Reading action request element: " + unprefixedNodeName);
                    return (Element) item;
                }
            }
        }
        throw new UnsupportedDataException("Could not read action request element matching namespace: " + actionRequestMessage.getActionNamespace());
    }

    protected Element writeActionResponseElement(Document document, Element element, ActionResponseMessage actionResponseMessage, ActionInvocation actionInvocation) {
        Logger logger = log;
        logger.fine("Writing action response element: " + actionInvocation.getAction().getName());
        String actionNamespace = actionResponseMessage.getActionNamespace();
        Element createElementNS = document.createElementNS(actionNamespace, "u:" + actionInvocation.getAction().getName() + "Response");
        element.appendChild(createElementNS);
        return createElementNS;
    }

    protected Element readActionResponseElement(Element element, ActionInvocation actionInvocation) {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                String unprefixedNodeName = getUnprefixedNodeName(item);
                if (unprefixedNodeName.equals(actionInvocation.getAction().getName() + "Response")) {
                    Logger logger = log;
                    logger.fine("Reading action response element: " + getUnprefixedNodeName(item));
                    return (Element) item;
                }
            }
        }
        log.fine("Could not read action response element");
        return null;
    }

    protected void writeActionInputArguments(Document document, Element element, ActionInvocation actionInvocation) {
        ActionArgument[] inputArguments = actionInvocation.getAction().getInputArguments();
        for (ActionArgument actionArgument : inputArguments) {
            log.fine("Writing action input argument: " + actionArgument.getName());
            XMLUtil.appendNewElement(document, element, actionArgument.getName(), actionInvocation.getInput(actionArgument) != null ? actionInvocation.getInput(actionArgument).toString() : "");
        }
    }

    public void readActionInputArguments(Element element, ActionInvocation actionInvocation) throws ActionException {
        actionInvocation.setInput(readArgumentValues(element.getChildNodes(), actionInvocation.getAction().getInputArguments()));
    }

    protected void writeActionOutputArguments(Document document, Element element, ActionInvocation actionInvocation) {
        ActionArgument[] outputArguments = actionInvocation.getAction().getOutputArguments();
        for (ActionArgument actionArgument : outputArguments) {
            log.fine("Writing action output argument: " + actionArgument.getName());
            XMLUtil.appendNewElement(document, element, actionArgument.getName(), actionInvocation.getOutput(actionArgument) != null ? actionInvocation.getOutput(actionArgument).toString() : "");
        }
    }

    protected void readActionOutputArguments(Element element, ActionInvocation actionInvocation) throws ActionException {
        actionInvocation.setOutput(readArgumentValues(element.getChildNodes(), actionInvocation.getAction().getOutputArguments()));
    }

    protected void writeFaultElement(Document document, Element element, ActionInvocation actionInvocation) {
        Element createElementNS = document.createElementNS(Constants.SOAP_NS_ENVELOPE, "s:Fault");
        element.appendChild(createElementNS);
        XMLUtil.appendNewElement(document, createElementNS, "faultcode", "s:Client");
        XMLUtil.appendNewElement(document, createElementNS, "faultstring", "UPnPError");
        Element createElement = document.createElement("detail");
        createElementNS.appendChild(createElement);
        Element createElementNS2 = document.createElementNS(Constants.NS_UPNP_CONTROL_10, "UPnPError");
        createElement.appendChild(createElementNS2);
        int errorCode = actionInvocation.getFailure().getErrorCode();
        String message = actionInvocation.getFailure().getMessage();
        Logger logger = log;
        logger.fine("Writing fault element: " + errorCode + " - " + message);
        XMLUtil.appendNewElement(document, createElementNS2, "errorCode", Integer.toString(errorCode));
        XMLUtil.appendNewElement(document, createElementNS2, "errorDescription", message);
    }

    protected ActionException readFaultElement(Element element) {
        NodeList childNodes = element.getChildNodes();
        String str = null;
        String str2 = null;
        boolean z = false;
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            short s = 1;
            if (item.getNodeType() == 1 && getUnprefixedNodeName(item).equals("Fault")) {
                NodeList childNodes2 = item.getChildNodes();
                String str3 = str2;
                String str4 = str;
                int i2 = 0;
                while (i2 < childNodes2.getLength()) {
                    Node item2 = childNodes2.item(i2);
                    if (item2.getNodeType() == s && getUnprefixedNodeName(item2).equals("detail")) {
                        NodeList childNodes3 = item2.getChildNodes();
                        String str5 = str3;
                        String str6 = str4;
                        int i3 = 0;
                        while (i3 < childNodes3.getLength()) {
                            Node item3 = childNodes3.item(i3);
                            if (item3.getNodeType() == s && getUnprefixedNodeName(item3).equals("UPnPError")) {
                                NodeList childNodes4 = item3.getChildNodes();
                                String str7 = str5;
                                String str8 = str6;
                                int i4 = 0;
                                while (i4 < childNodes4.getLength()) {
                                    Node item4 = childNodes4.item(i4);
                                    if (item4.getNodeType() == s) {
                                        if (getUnprefixedNodeName(item4).equals("errorCode")) {
                                            str8 = XMLUtil.getTextContent(item4);
                                        }
                                        if (getUnprefixedNodeName(item4).equals("errorDescription")) {
                                            str7 = XMLUtil.getTextContent(item4);
                                        }
                                    }
                                    i4++;
                                    s = 1;
                                }
                                str6 = str8;
                                str5 = str7;
                            }
                            i3++;
                            s = 1;
                        }
                        str4 = str6;
                        str3 = str5;
                    }
                    i2++;
                    s = 1;
                }
                str = str4;
                str2 = str3;
                z = true;
            }
        }
        if (str != null) {
            try {
                int intValue = Integer.valueOf(str).intValue();
                ErrorCode byCode = ErrorCode.getByCode(intValue);
                if (byCode != null) {
                    log.fine("Reading fault element: " + byCode.getCode() + " - " + str2);
                    return new ActionException(byCode, str2, false);
                }
                log.fine("Reading fault element: " + intValue + " - " + str2);
                return new ActionException(intValue, str2);
            } catch (NumberFormatException unused) {
                throw new RuntimeException("Error code was not a number");
            }
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException("Received fault element but no error code");
        }
    }

    protected String getMessageBody(ActionMessage actionMessage) throws UnsupportedDataException {
        if (actionMessage.isBodyNonEmptyString()) {
            return actionMessage.getBodyString().trim();
        }
        throw new UnsupportedDataException("Can't transform null or non-string/zero-length body of: " + actionMessage);
    }

    protected String toString(Document document) throws Exception {
        String documentToString = XMLUtil.documentToString(document);
        while (true) {
            if (!documentToString.endsWith("\n") && !documentToString.endsWith(StringUtils.CR)) {
                return documentToString;
            }
            documentToString = documentToString.substring(0, documentToString.length() - 1);
        }
    }

    protected String getUnprefixedNodeName(Node node) {
        if (node.getPrefix() != null) {
            return node.getNodeName().substring(node.getPrefix().length() + 1);
        }
        return node.getNodeName();
    }

    protected ActionArgumentValue[] readArgumentValues(NodeList nodeList, ActionArgument[] actionArgumentArr) throws ActionException {
        List<Node> matchingNodes = getMatchingNodes(nodeList, actionArgumentArr);
        ActionArgumentValue[] actionArgumentValueArr = new ActionArgumentValue[actionArgumentArr.length];
        for (int i = 0; i < actionArgumentArr.length; i++) {
            ActionArgument actionArgument = actionArgumentArr[i];
            Node findActionArgumentNode = findActionArgumentNode(matchingNodes, actionArgument);
            if (findActionArgumentNode != null) {
                Logger logger = log;
                logger.fine("Reading action argument: " + actionArgument.getName());
                actionArgumentValueArr[i] = createValue(actionArgument, XMLUtil.getTextContent(findActionArgumentNode));
            } else {
                ErrorCode errorCode = ErrorCode.ARGUMENT_VALUE_INVALID;
                throw new ActionException(errorCode, "Could not find argument '" + actionArgument.getName() + "' node");
            }
        }
        return actionArgumentValueArr;
    }

    protected List<Node> getMatchingNodes(NodeList nodeList, ActionArgument[] actionArgumentArr) throws ActionException {
        ArrayList arrayList = new ArrayList();
        for (ActionArgument actionArgument : actionArgumentArr) {
            arrayList.add(actionArgument.getName());
            arrayList.addAll(Arrays.asList(actionArgument.getAliases()));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            if (item.getNodeType() == 1 && arrayList.contains(getUnprefixedNodeName(item))) {
                arrayList2.add(item);
            }
        }
        if (arrayList2.size() >= actionArgumentArr.length) {
            return arrayList2;
        }
        throw new ActionException(ErrorCode.ARGUMENT_VALUE_INVALID, "Invalid number of input or output arguments in XML message, expected " + actionArgumentArr.length + " but found " + arrayList2.size());
    }

    protected ActionArgumentValue createValue(ActionArgument actionArgument, String str) throws ActionException {
        try {
            return new ActionArgumentValue(actionArgument, str);
        } catch (InvalidValueException e) {
            ErrorCode errorCode = ErrorCode.ARGUMENT_VALUE_INVALID;
            throw new ActionException(errorCode, "Wrong type or invalid value for '" + actionArgument.getName() + "': " + e.getMessage(), e);
        }
    }

    protected Node findActionArgumentNode(List<Node> list, ActionArgument actionArgument) {
        for (Node node : list) {
            if (actionArgument.isNameOrAlias(getUnprefixedNodeName(node))) {
                return node;
            }
        }
        return null;
    }

    @Override // org.xml.sax.ErrorHandler
    public void warning(SAXParseException sAXParseException) throws SAXException {
        log.warning(sAXParseException.toString());
    }

    @Override // org.xml.sax.ErrorHandler
    public void error(SAXParseException sAXParseException) throws SAXException {
        throw sAXParseException;
    }

    @Override // org.xml.sax.ErrorHandler
    public void fatalError(SAXParseException sAXParseException) throws SAXException {
        throw sAXParseException;
    }
}
