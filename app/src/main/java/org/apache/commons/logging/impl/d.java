package org.apache.commons.logging.impl;

import java.security.PrivilegedAction;

/* compiled from: SimpleLog.java */
/* loaded from: classes5.dex */
final class d implements PrivilegedAction {
    private final String a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(String str) {
        this.a = str;
    }

    @Override // java.security.PrivilegedAction
    public Object run() {
        ClassLoader a = SimpleLog.a();
        if (a != null) {
            return a.getResourceAsStream(this.a);
        }
        return ClassLoader.getSystemResourceAsStream(this.a);
    }
}
