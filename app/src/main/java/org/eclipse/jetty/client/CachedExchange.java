package org.eclipse.jetty.client;

import java.io.IOException;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.io.Buffer;

/* loaded from: classes5.dex */
public class CachedExchange extends HttpExchange {
    private final HttpFields _responseFields;
    private volatile int _responseStatus;

    public CachedExchange(boolean z) {
        this._responseFields = z ? new HttpFields() : null;
    }

    public synchronized int getResponseStatus() {
        if (getStatus() >= 5) {
        } else {
            throw new IllegalStateException("Response not received yet");
        }
        return this._responseStatus;
    }

    public synchronized HttpFields getResponseFields() {
        if (getStatus() >= 6) {
        } else {
            throw new IllegalStateException("Headers not completely received yet");
        }
        return this._responseFields;
    }

    @Override // org.eclipse.jetty.client.HttpExchange
    public synchronized void onResponseStatus(Buffer buffer, int i, Buffer buffer2) throws IOException {
        this._responseStatus = i;
        super.onResponseStatus(buffer, i, buffer2);
    }

    @Override // org.eclipse.jetty.client.HttpExchange
    public synchronized void onResponseHeader(Buffer buffer, Buffer buffer2) throws IOException {
        if (this._responseFields != null) {
            this._responseFields.add(buffer, buffer2.asImmutableBuffer());
        }
        super.onResponseHeader(buffer, buffer2);
    }
}
