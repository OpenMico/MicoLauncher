package org.apache.commons.lang;

import org.apache.commons.lang.exception.NestableRuntimeException;

/* loaded from: classes5.dex */
public class UnhandledException extends NestableRuntimeException {
    private static final long serialVersionUID = 1832101364842773720L;

    public UnhandledException(Throwable th) {
        super(th);
    }

    public UnhandledException(String str, Throwable th) {
        super(str, th);
    }
}
