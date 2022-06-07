package org.apache.commons.logging;

import java.security.PrivilegedAction;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LogFactory.java */
/* loaded from: classes5.dex */
public final class a implements PrivilegedAction {
    @Override // java.security.PrivilegedAction
    public Object run() {
        return LogFactory.directGetContextClassLoader();
    }
}
