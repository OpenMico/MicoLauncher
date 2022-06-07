package org.eclipse.jetty.client.webdav;

import java.io.IOException;
import org.eclipse.jetty.client.HttpExchange;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class PropfindExchange extends HttpExchange {
    private static final Logger LOG = Log.getLogger(PropfindExchange.class);
    boolean _propertyExists = false;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.client.HttpExchange
    public void onResponseStatus(Buffer buffer, int i, Buffer buffer2) throws IOException {
        if (i == 200) {
            LOG.debug("PropfindExchange:Status: Exists", new Object[0]);
            this._propertyExists = true;
        } else {
            LOG.debug("PropfindExchange:Status: Not Exists", new Object[0]);
        }
        super.onResponseStatus(buffer, i, buffer2);
    }

    public boolean exists() {
        return this._propertyExists;
    }
}
