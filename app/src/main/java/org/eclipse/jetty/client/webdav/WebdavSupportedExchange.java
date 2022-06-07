package org.eclipse.jetty.client.webdav;

import java.io.IOException;
import org.eclipse.jetty.client.HttpExchange;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class WebdavSupportedExchange extends HttpExchange {
    private static final Logger LOG = Log.getLogger(WebdavSupportedExchange.class);
    private boolean _webdavSupported = false;
    private boolean _isComplete = false;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.client.HttpExchange
    public void onResponseHeader(Buffer buffer, Buffer buffer2) throws IOException {
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("WebdavSupportedExchange:Header:" + buffer.toString() + " / " + buffer2.toString(), new Object[0]);
        }
        if ("DAV".equals(buffer.toString()) && (buffer2.toString().indexOf("1") >= 0 || buffer2.toString().indexOf("2") >= 0)) {
            this._webdavSupported = true;
        }
        super.onResponseHeader(buffer, buffer2);
    }

    public void waitTilCompletion() throws InterruptedException {
        synchronized (this) {
            while (!this._isComplete) {
                wait();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.client.HttpExchange
    public void onResponseComplete() throws IOException {
        this._isComplete = true;
        super.onResponseComplete();
    }

    public boolean isWebdavSupported() {
        return this._webdavSupported;
    }
}
