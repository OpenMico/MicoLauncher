package org.eclipse.jetty.io;

/* loaded from: classes5.dex */
public class UncheckedIOException extends RuntimeException {
    public UncheckedIOException() {
    }

    public UncheckedIOException(String str) {
        super(str);
    }

    public UncheckedIOException(Throwable th) {
        super(th);
    }

    public UncheckedIOException(String str, Throwable th) {
        super(str, th);
    }
}
