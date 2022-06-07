package org.apache.commons.lang3.builder;

/* compiled from: IDKey.java */
/* loaded from: classes5.dex */
final class a {
    private final Object a;
    private final int b;

    public a(Object obj) {
        this.b = System.identityHashCode(obj);
        this.a = obj;
    }

    public int hashCode() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return this.b == aVar.b && this.a == aVar.a;
    }
}
