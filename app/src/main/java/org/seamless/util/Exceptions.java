package org.seamless.util;

/* loaded from: classes4.dex */
public class Exceptions {
    public static Throwable unwrap(Throwable th) throws IllegalArgumentException {
        if (th != null) {
            Throwable th2 = th;
            while (th != null) {
                th2 = th;
                th = th.getCause();
            }
            return th2;
        }
        throw new IllegalArgumentException("Cannot unwrap null throwable");
    }
}
