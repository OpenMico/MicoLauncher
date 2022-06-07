package org.eclipse.jetty.io;

import java.io.IOException;

/* loaded from: classes5.dex */
public interface EndPoint {
    boolean blockReadable(long j) throws IOException;

    boolean blockWritable(long j) throws IOException;

    void close() throws IOException;

    int fill(Buffer buffer) throws IOException;

    int flush(Buffer buffer) throws IOException;

    int flush(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException;

    void flush() throws IOException;

    String getLocalAddr();

    String getLocalHost();

    int getLocalPort();

    int getMaxIdleTime();

    String getRemoteAddr();

    String getRemoteHost();

    int getRemotePort();

    Object getTransport();

    boolean isBlocking();

    boolean isInputShutdown();

    boolean isOpen();

    boolean isOutputShutdown();

    void setMaxIdleTime(int i) throws IOException;

    void shutdownInput() throws IOException;

    void shutdownOutput() throws IOException;
}
