package org.fourthline.cling.transport.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.inject.Alternative;
import org.fourthline.cling.model.UnsupportedDataException;
import org.fourthline.cling.model.action.ActionArgumentValue;
import org.fourthline.cling.model.action.ActionException;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.control.ActionRequestMessage;
import org.fourthline.cling.model.message.control.ActionResponseMessage;
import org.fourthline.cling.model.meta.ActionArgument;
import org.fourthline.cling.model.types.ErrorCode;
import org.fourthline.cling.transport.spi.SOAPActionProcessor;
import org.seamless.xml.XmlPullParserUtils;
import org.xmlpull.v1.XmlPullParser;

@Alternative
/* loaded from: classes5.dex */
public class PullSOAPActionProcessorImpl extends SOAPActionProcessorImpl {
    protected static Logger log = Logger.getLogger(SOAPActionProcessor.class.getName());

    @Override // org.fourthline.cling.transport.impl.SOAPActionProcessorImpl, org.fourthline.cling.transport.spi.SOAPActionProcessor
    public void readBody(ActionRequestMessage actionRequestMessage, ActionInvocation actionInvocation) throws UnsupportedDataException {
        String messageBody = getMessageBody(actionRequestMessage);
        try {
            readBodyRequest(XmlPullParserUtils.createParser(messageBody), actionRequestMessage, actionInvocation);
        } catch (Exception e) {
            throw new UnsupportedDataException("Can't transform message payload: " + e, e, messageBody);
        }
    }

    @Override // org.fourthline.cling.transport.impl.SOAPActionProcessorImpl, org.fourthline.cling.transport.spi.SOAPActionProcessor
    public void readBody(ActionResponseMessage actionResponseMessage, ActionInvocation actionInvocation) throws UnsupportedDataException {
        String messageBody = getMessageBody(actionResponseMessage);
        try {
            XmlPullParser createParser = XmlPullParserUtils.createParser(messageBody);
            readBodyElement(createParser);
            readBodyResponse(createParser, actionInvocation);
        } catch (Exception e) {
            throw new UnsupportedDataException("Can't transform message payload: " + e, e, messageBody);
        }
    }

    protected void readBodyElement(XmlPullParser xmlPullParser) throws Exception {
        XmlPullParserUtils.searchTag(xmlPullParser, "Body");
    }

    protected void readBodyRequest(XmlPullParser xmlPullParser, ActionRequestMessage actionRequestMessage, ActionInvocation actionInvocation) throws Exception {
        XmlPullParserUtils.searchTag(xmlPullParser, actionInvocation.getAction().getName());
        readActionInputArguments(xmlPullParser, actionInvocation);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0055, code lost:
        r0 = org.fourthline.cling.model.types.ErrorCode.ACTION_FAILED;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0080, code lost:
        throw new org.fourthline.cling.model.action.ActionException(r0, java.lang.String.format("Action SOAP response do not contain %s element", r6.getAction().getName() + "Response"));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void readBodyResponse(org.xmlpull.v1.XmlPullParser r5, org.fourthline.cling.model.action.ActionInvocation r6) throws java.lang.Exception {
        /*
            r4 = this;
        L_0x0000:
            int r0 = r5.next()
            r1 = 2
            if (r0 != r1) goto L_0x0042
            java.lang.String r1 = r5.getName()
            java.lang.String r2 = "Fault"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x001b
            org.fourthline.cling.model.action.ActionException r5 = r4.readFaultElement(r5)
            r6.setFailure(r5)
            return
        L_0x001b:
            java.lang.String r1 = r5.getName()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            org.fourthline.cling.model.meta.Action r3 = r6.getAction()
            java.lang.String r3 = r3.getName()
            r2.append(r3)
            java.lang.String r3 = "Response"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0042
            r4.readActionOutputArguments(r5, r6)
            return
        L_0x0042:
            r1 = 1
            if (r0 == r1) goto L_0x0055
            r2 = 3
            if (r0 != r2) goto L_0x0000
            java.lang.String r0 = r5.getName()
            java.lang.String r2 = "Body"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x0055
            goto L_0x0000
        L_0x0055:
            org.fourthline.cling.model.action.ActionException r5 = new org.fourthline.cling.model.action.ActionException
            org.fourthline.cling.model.types.ErrorCode r0 = org.fourthline.cling.model.types.ErrorCode.ACTION_FAILED
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r2 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            org.fourthline.cling.model.meta.Action r6 = r6.getAction()
            java.lang.String r6 = r6.getName()
            r3.append(r6)
            java.lang.String r6 = "Response"
            r3.append(r6)
            java.lang.String r6 = r3.toString()
            r1[r2] = r6
            java.lang.String r6 = "Action SOAP response do not contain %s element"
            java.lang.String r6 = java.lang.String.format(r6, r1)
            r5.<init>(r0, r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.fourthline.cling.transport.impl.PullSOAPActionProcessorImpl.readBodyResponse(org.xmlpull.v1.XmlPullParser, org.fourthline.cling.model.action.ActionInvocation):void");
    }

    protected void readActionInputArguments(XmlPullParser xmlPullParser, ActionInvocation actionInvocation) throws Exception {
        actionInvocation.setInput(readArgumentValues(xmlPullParser, actionInvocation.getAction().getInputArguments()));
    }

    protected void readActionOutputArguments(XmlPullParser xmlPullParser, ActionInvocation actionInvocation) throws Exception {
        actionInvocation.setOutput(readArgumentValues(xmlPullParser, actionInvocation.getAction().getOutputArguments()));
    }

    protected Map<String, String> getMatchingNodes(XmlPullParser xmlPullParser, ActionArgument[] actionArgumentArr) throws Exception {
        ArrayList arrayList = new ArrayList();
        for (ActionArgument actionArgument : actionArgumentArr) {
            arrayList.add(actionArgument.getName().toUpperCase(Locale.ROOT));
            for (String str : Arrays.asList(actionArgument.getAliases())) {
                arrayList.add(str.toUpperCase(Locale.ROOT));
            }
        }
        HashMap hashMap = new HashMap();
        String name = xmlPullParser.getName();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 2 && arrayList.contains(xmlPullParser.getName().toUpperCase(Locale.ROOT))) {
                hashMap.put(xmlPullParser.getName(), xmlPullParser.nextText());
            }
            if (next == 1 || (next == 3 && xmlPullParser.getName().equals(name))) {
                break;
            }
        }
        if (hashMap.size() >= actionArgumentArr.length) {
            return hashMap;
        }
        throw new ActionException(ErrorCode.ARGUMENT_VALUE_INVALID, "Invalid number of input or output arguments in XML message, expected " + actionArgumentArr.length + " but found " + hashMap.size());
    }

    protected ActionArgumentValue[] readArgumentValues(XmlPullParser xmlPullParser, ActionArgument[] actionArgumentArr) throws Exception {
        Map<String, String> matchingNodes = getMatchingNodes(xmlPullParser, actionArgumentArr);
        ActionArgumentValue[] actionArgumentValueArr = new ActionArgumentValue[actionArgumentArr.length];
        for (int i = 0; i < actionArgumentArr.length; i++) {
            ActionArgument actionArgument = actionArgumentArr[i];
            String findActionArgumentValue = findActionArgumentValue(matchingNodes, actionArgument);
            if (findActionArgumentValue != null) {
                Logger logger = log;
                logger.fine("Reading action argument: " + actionArgument.getName());
                actionArgumentValueArr[i] = createValue(actionArgument, findActionArgumentValue);
            } else {
                ErrorCode errorCode = ErrorCode.ARGUMENT_VALUE_INVALID;
                throw new ActionException(errorCode, "Could not find argument '" + actionArgument.getName() + "' node");
            }
        }
        return actionArgumentValueArr;
    }

    protected String findActionArgumentValue(Map<String, String> map, ActionArgument actionArgument) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (actionArgument.isNameOrAlias(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    protected ActionException readFaultElement(XmlPullParser xmlPullParser) throws Exception {
        XmlPullParserUtils.searchTag(xmlPullParser, "UPnPError");
        String str = null;
        String str2 = null;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("errorCode")) {
                    str = xmlPullParser.nextText();
                } else if (name.equals("errorDescription")) {
                    str2 = xmlPullParser.nextText();
                }
            }
            if (next == 1 || (next == 3 && xmlPullParser.getName().equals("UPnPError"))) {
                break;
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
        } else {
            throw new RuntimeException("Received fault element but no error code");
        }
    }
}
