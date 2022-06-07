package org.fourthline.cling.transport.spi;

import org.fourthline.cling.model.UnsupportedDataException;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.control.ActionRequestMessage;
import org.fourthline.cling.model.message.control.ActionResponseMessage;

/* loaded from: classes5.dex */
public interface SOAPActionProcessor {
    void readBody(ActionRequestMessage actionRequestMessage, ActionInvocation actionInvocation) throws UnsupportedDataException;

    void readBody(ActionResponseMessage actionResponseMessage, ActionInvocation actionInvocation) throws UnsupportedDataException;

    void writeBody(ActionRequestMessage actionRequestMessage, ActionInvocation actionInvocation) throws UnsupportedDataException;

    void writeBody(ActionResponseMessage actionResponseMessage, ActionInvocation actionInvocation) throws UnsupportedDataException;
}
