package org.apache.commons.logging.impl;

import java.security.PrivilegedAction;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LogFactoryImpl.java */
/* loaded from: classes5.dex */
public final class a implements PrivilegedAction {
    @Override // java.security.PrivilegedAction
    public Object run() {
        return LogFactoryImpl.a();
    }
}
