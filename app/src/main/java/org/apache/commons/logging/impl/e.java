package org.apache.commons.logging.impl;

import java.util.Enumeration;
import org.apache.commons.logging.impl.WeakHashtable;

/* compiled from: WeakHashtable.java */
/* loaded from: classes5.dex */
class e implements Enumeration {
    private final Enumeration a;
    private final WeakHashtable b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(WeakHashtable weakHashtable, Enumeration enumeration) {
        this.b = weakHashtable;
        this.a = enumeration;
    }

    @Override // java.util.Enumeration
    public boolean hasMoreElements() {
        return this.a.hasMoreElements();
    }

    @Override // java.util.Enumeration
    public Object nextElement() {
        return WeakHashtable.b.a((WeakHashtable.b) this.a.nextElement());
    }
}
