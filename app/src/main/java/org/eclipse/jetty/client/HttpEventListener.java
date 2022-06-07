package org.eclipse.jetty.client;

import java.io.IOException;
import org.eclipse.jetty.io.Buffer;

/* loaded from: classes5.dex */
public interface HttpEventListener {
    void onConnectionFailed(Throwable th);

    void onException(Throwable th);

    void onExpire();

    void onRequestCommitted() throws IOException;

    void onRequestComplete() throws IOException;

    void onResponseComplete() throws IOException;

    void onResponseContent(Buffer buffer) throws IOException;

    void onResponseHeader(Buffer buffer, Buffer buffer2) throws IOException;

    void onResponseHeaderComplete() throws IOException;

    void onResponseStatus(Buffer buffer, int i, Buffer buffer2) throws IOException;

    void onRetry();
}
