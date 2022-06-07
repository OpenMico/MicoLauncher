package org.fourthline.cling.protocol;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.transport.RouterException;

/* loaded from: classes5.dex */
public abstract class SendingSync<IN extends StreamRequestMessage, OUT extends StreamResponseMessage> extends SendingAsync {
    private final IN inputMessage;
    protected OUT outputMessage;

    protected abstract OUT executeSync() throws RouterException;

    /* JADX INFO: Access modifiers changed from: protected */
    public SendingSync(UpnpService upnpService, IN in) {
        super(upnpService);
        this.inputMessage = in;
    }

    public IN getInputMessage() {
        return this.inputMessage;
    }

    public OUT getOutputMessage() {
        return this.outputMessage;
    }

    @Override // org.fourthline.cling.protocol.SendingAsync
    protected final void execute() throws RouterException {
        this.outputMessage = executeSync();
    }

    @Override // org.fourthline.cling.protocol.SendingAsync
    public String toString() {
        return "(" + getClass().getSimpleName() + ")";
    }
}
