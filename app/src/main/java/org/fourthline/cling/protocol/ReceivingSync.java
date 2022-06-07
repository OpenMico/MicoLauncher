package org.fourthline.cling.protocol;

import java.util.logging.Logger;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.model.profile.RemoteClientInfo;
import org.fourthline.cling.transport.RouterException;

/* loaded from: classes5.dex */
public abstract class ReceivingSync<IN extends StreamRequestMessage, OUT extends StreamResponseMessage> extends ReceivingAsync<IN> {
    private static final Logger log = Logger.getLogger(UpnpService.class.getName());
    protected OUT outputMessage;
    protected final RemoteClientInfo remoteClientInfo;

    protected abstract OUT executeSync() throws RouterException;

    public void responseException(Throwable th) {
    }

    public void responseSent(StreamResponseMessage streamResponseMessage) {
    }

    public ReceivingSync(UpnpService upnpService, IN in) {
        super(upnpService, in);
        this.remoteClientInfo = new RemoteClientInfo(in);
    }

    public OUT getOutputMessage() {
        return this.outputMessage;
    }

    @Override // org.fourthline.cling.protocol.ReceivingAsync
    protected final void execute() throws RouterException {
        this.outputMessage = executeSync();
        if (this.outputMessage != null && getRemoteClientInfo().getExtraResponseHeaders().size() > 0) {
            Logger logger = log;
            logger.fine("Setting extra headers on response message: " + getRemoteClientInfo().getExtraResponseHeaders().size());
            this.outputMessage.getHeaders().putAll(getRemoteClientInfo().getExtraResponseHeaders());
        }
    }

    public RemoteClientInfo getRemoteClientInfo() {
        return this.remoteClientInfo;
    }

    @Override // org.fourthline.cling.protocol.ReceivingAsync
    public String toString() {
        return "(" + getClass().getSimpleName() + ")";
    }
}
