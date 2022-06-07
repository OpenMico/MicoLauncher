package org.eclipse.jetty.io;

import java.io.EOFException;

/* loaded from: classes5.dex */
public class EofException extends EOFException {
    public EofException() {
    }

    public EofException(String str) {
        super(str);
    }

    public EofException(Throwable th) {
        if (th != null) {
            initCause(th);
        }
    }
}
