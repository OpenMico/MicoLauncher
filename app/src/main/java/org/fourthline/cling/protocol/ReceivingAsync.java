package org.fourthline.cling.protocol;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.model.message.UpnpMessage;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.transport.RouterException;
import org.seamless.util.Exceptions;

/* loaded from: classes5.dex */
public abstract class ReceivingAsync<M extends UpnpMessage> implements Runnable {
    private static final Logger log = Logger.getLogger(UpnpService.class.getName());
    private M inputMessage;
    private final UpnpService upnpService;

    protected abstract void execute() throws RouterException;

    protected boolean waitBeforeExecution() throws InterruptedException {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ReceivingAsync(UpnpService upnpService, M m) {
        this.upnpService = upnpService;
        this.inputMessage = m;
    }

    public UpnpService getUpnpService() {
        return this.upnpService;
    }

    public M getInputMessage() {
        return this.inputMessage;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean z;
        try {
            z = waitBeforeExecution();
        } catch (InterruptedException unused) {
            Logger logger = log;
            logger.info("Protocol wait before execution interrupted (on shutdown?): " + getClass().getSimpleName());
            z = false;
        }
        if (z) {
            try {
                execute();
            } catch (Exception e) {
                Throwable unwrap = Exceptions.unwrap(e);
                if (unwrap instanceof InterruptedException) {
                    Logger logger2 = log;
                    Level level = Level.INFO;
                    logger2.log(level, "Interrupted protocol '" + getClass().getSimpleName() + "': " + e, unwrap);
                    return;
                }
                throw new RuntimeException("Fatal error while executing protocol '" + getClass().getSimpleName() + "': " + e, e);
            }
        }
    }

    protected <H extends UpnpHeader> H getFirstHeader(UpnpHeader.Type type, Class<H> cls) {
        return (H) getInputMessage().getHeaders().getFirstHeader(type, cls);
    }

    public String toString() {
        return "(" + getClass().getSimpleName() + ")";
    }
}
