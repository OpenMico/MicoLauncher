package com.google.zxing.oned.rss.expanded;

import java.util.ArrayList;
import java.util.List;

/* compiled from: ExpandedRow.java */
/* loaded from: classes2.dex */
final class c {
    private final List<b> a;
    private final int b;
    private final boolean c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(List<b> list, int i, boolean z) {
        this.a = new ArrayList(list);
        this.b = i;
        this.c = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<b> a() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(List<b> list) {
        return this.a.equals(list);
    }

    public String toString() {
        return "{ " + this.a + " }";
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return this.a.equals(cVar.a()) && this.c == cVar.c;
    }

    public int hashCode() {
        return this.a.hashCode() ^ Boolean.valueOf(this.c).hashCode();
    }
}
