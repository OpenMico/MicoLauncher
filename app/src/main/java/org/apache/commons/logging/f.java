package org.apache.commons.logging;

import java.security.PrivilegedAction;

/* compiled from: LogFactory.java */
/* loaded from: classes5.dex */
final class f implements PrivilegedAction {
    private final String a;
    private final String b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    @Override // java.security.PrivilegedAction
    public Object run() {
        return System.getProperty(this.a, this.b);
    }
}
