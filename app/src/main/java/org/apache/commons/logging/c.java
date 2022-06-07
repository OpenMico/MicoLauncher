package org.apache.commons.logging;

import java.security.PrivilegedAction;

/* compiled from: LogFactory.java */
/* loaded from: classes5.dex */
final class c implements PrivilegedAction {
    private final ClassLoader a;
    private final String b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(ClassLoader classLoader, String str) {
        this.a = classLoader;
        this.b = str;
    }

    @Override // java.security.PrivilegedAction
    public Object run() {
        ClassLoader classLoader = this.a;
        if (classLoader != null) {
            return classLoader.getResourceAsStream(this.b);
        }
        return ClassLoader.getSystemResourceAsStream(this.b);
    }
}
