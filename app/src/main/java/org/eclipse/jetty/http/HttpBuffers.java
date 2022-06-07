package org.eclipse.jetty.http;

import org.eclipse.jetty.io.Buffers;

/* loaded from: classes5.dex */
public interface HttpBuffers {
    int getMaxBuffers();

    int getRequestBufferSize();

    Buffers.Type getRequestBufferType();

    Buffers getRequestBuffers();

    int getRequestHeaderSize();

    Buffers.Type getRequestHeaderType();

    int getResponseBufferSize();

    Buffers.Type getResponseBufferType();

    Buffers getResponseBuffers();

    int getResponseHeaderSize();

    Buffers.Type getResponseHeaderType();

    void setMaxBuffers(int i);

    void setRequestBufferSize(int i);

    void setRequestBuffers(Buffers buffers);

    void setRequestHeaderSize(int i);

    void setResponseBufferSize(int i);

    void setResponseBuffers(Buffers buffers);

    void setResponseHeaderSize(int i);
}
