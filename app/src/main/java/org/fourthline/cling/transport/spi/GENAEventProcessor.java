package org.fourthline.cling.transport.spi;

import org.fourthline.cling.model.UnsupportedDataException;
import org.fourthline.cling.model.message.gena.IncomingEventRequestMessage;
import org.fourthline.cling.model.message.gena.OutgoingEventRequestMessage;

/* loaded from: classes5.dex */
public interface GENAEventProcessor {
    void readBody(IncomingEventRequestMessage incomingEventRequestMessage) throws UnsupportedDataException;

    void writeBody(OutgoingEventRequestMessage outgoingEventRequestMessage) throws UnsupportedDataException;
}
