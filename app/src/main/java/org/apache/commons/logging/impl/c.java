package org.apache.commons.logging.impl;

import java.security.PrivilegedAction;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LogFactoryImpl.java */
/* loaded from: classes5.dex */
public class c implements PrivilegedAction {
    private final ClassLoader a;
    private final LogFactoryImpl b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(LogFactoryImpl logFactoryImpl, ClassLoader classLoader) {
        this.b = logFactoryImpl;
        this.a = classLoader;
    }

    @Override // java.security.PrivilegedAction
    public Object run() {
        return this.a.getParent();
    }
}
