package org.eclipse.jetty.io;

import java.io.IOException;

/* loaded from: classes5.dex */
public interface Connection {
    long getTimeStamp();

    Connection handle() throws IOException;

    boolean isIdle();

    boolean isSuspended();

    void onClose();

    void onIdleExpired(long j);
}
