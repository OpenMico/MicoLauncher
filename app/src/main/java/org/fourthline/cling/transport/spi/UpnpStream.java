package org.fourthline.cling.transport.spi;

import java.util.logging.Logger;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.protocol.ProtocolCreationException;
import org.fourthline.cling.protocol.ProtocolFactory;
import org.fourthline.cling.protocol.ReceivingSync;
import org.seamless.util.Exceptions;

/* loaded from: classes5.dex */
public abstract class UpnpStream implements Runnable {
    private static Logger log = Logger.getLogger(UpnpStream.class.getName());
    protected final ProtocolFactory protocolFactory;
    protected ReceivingSync syncProtocol;

    /* JADX INFO: Access modifiers changed from: protected */
    public UpnpStream(ProtocolFactory protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    public ProtocolFactory getProtocolFactory() {
        return this.protocolFactory;
    }

    public StreamResponseMessage process(StreamRequestMessage streamRequestMessage) {
        Logger logger = log;
        logger.fine("Processing stream request message: " + streamRequestMessage);
        try {
            this.syncProtocol = getProtocolFactory().createReceivingSync(streamRequestMessage);
            Logger logger2 = log;
            logger2.fine("Running protocol for synchronous message processing: " + this.syncProtocol);
            this.syncProtocol.run();
            StreamResponseMessage outputMessage = this.syncProtocol.getOutputMessage();
            if (outputMessage == null) {
                log.finer("Protocol did not return any response message");
                return null;
            }
            Logger logger3 = log;
            logger3.finer("Protocol returned response: " + outputMessage);
            return outputMessage;
        } catch (ProtocolCreationException e) {
            Logger logger4 = log;
            logger4.warning("Processing stream request failed - " + Exceptions.unwrap(e).toString());
            return new StreamResponseMessage(UpnpResponse.Status.NOT_IMPLEMENTED);
        }
    }

    protected void responseSent(StreamResponseMessage streamResponseMessage) {
        ReceivingSync receivingSync = this.syncProtocol;
        if (receivingSync != null) {
            receivingSync.responseSent(streamResponseMessage);
        }
    }

    protected void responseException(Throwable th) {
        ReceivingSync receivingSync = this.syncProtocol;
        if (receivingSync != null) {
            receivingSync.responseException(th);
        }
    }

    public String toString() {
        return "(" + getClass().getSimpleName() + ")";
    }
}
