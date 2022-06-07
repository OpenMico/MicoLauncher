package io.netty.util;

import java.util.Arrays;

@Deprecated
/* loaded from: classes4.dex */
public class ResourceLeakException extends RuntimeException {
    private static final long serialVersionUID = 7186453858343358280L;
    private final StackTraceElement[] cachedStackTrace = getStackTrace();

    public ResourceLeakException() {
    }

    public ResourceLeakException(String str) {
        super(str);
    }

    public ResourceLeakException(String str, Throwable th) {
        super(str, th);
    }

    public ResourceLeakException(Throwable th) {
        super(th);
    }

    public int hashCode() {
        int i = 0;
        for (StackTraceElement stackTraceElement : this.cachedStackTrace) {
            i = (i * 31) + stackTraceElement.hashCode();
        }
        return i;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ResourceLeakException)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return Arrays.equals(this.cachedStackTrace, ((ResourceLeakException) obj).cachedStackTrace);
    }
}
