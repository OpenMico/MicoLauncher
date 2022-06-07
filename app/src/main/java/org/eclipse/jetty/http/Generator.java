package org.eclipse.jetty.http;

import java.io.IOException;
import org.eclipse.jetty.io.Buffer;

/* loaded from: classes5.dex */
public interface Generator {
    public static final boolean LAST = true;
    public static final boolean MORE = false;

    void addContent(Buffer buffer, boolean z) throws IOException;

    void complete() throws IOException;

    void completeHeader(HttpFields httpFields, boolean z) throws IOException;

    int flushBuffer() throws IOException;

    int getContentBufferSize();

    long getContentWritten();

    void increaseContentBufferSize(int i);

    boolean isAllContentWritten();

    boolean isBufferFull();

    boolean isCommitted();

    boolean isComplete();

    boolean isIdle();

    boolean isPersistent();

    boolean isWritten();

    void reset();

    void resetBuffer();

    void returnBuffers();

    void sendError(int i, String str, String str2, boolean z) throws IOException;

    void setContentLength(long j);

    void setDate(Buffer buffer);

    void setHead(boolean z);

    void setPersistent(boolean z);

    void setRequest(String str, String str2);

    void setResponse(int i, String str);

    void setSendServerVersion(boolean z);

    void setVersion(int i);
}
