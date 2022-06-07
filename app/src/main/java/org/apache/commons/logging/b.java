package org.apache.commons.logging;

import java.security.PrivilegedAction;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LogFactory.java */
/* loaded from: classes5.dex */
public final class b implements PrivilegedAction {
    private final String a;
    private final ClassLoader b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(String str, ClassLoader classLoader) {
        this.a = str;
        this.b = classLoader;
    }

    @Override // java.security.PrivilegedAction
    public Object run() {
        return LogFactory.createFactory(this.a, this.b);
    }
}
