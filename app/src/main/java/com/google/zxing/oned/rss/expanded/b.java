package com.google.zxing.oned.rss.expanded;

import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ExpandedPair.java */
/* loaded from: classes2.dex */
public final class b {
    private final boolean a;
    private final DataCharacter b;
    private final DataCharacter c;
    private final FinderPattern d;

    public b(DataCharacter dataCharacter, DataCharacter dataCharacter2, FinderPattern finderPattern, boolean z) {
        this.b = dataCharacter;
        this.c = dataCharacter2;
        this.d = finderPattern;
        this.a = z;
    }

    public DataCharacter a() {
        return this.b;
    }

    public DataCharacter b() {
        return this.c;
    }

    public FinderPattern c() {
        return this.d;
    }

    public boolean d() {
        return this.c == null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        sb.append(this.b);
        sb.append(" , ");
        sb.append(this.c);
        sb.append(" : ");
        FinderPattern finderPattern = this.d;
        sb.append(finderPattern == null ? "null" : Integer.valueOf(finderPattern.getValue()));
        sb.append(" ]");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return a(this.b, bVar.b) && a(this.c, bVar.c) && a(this.d, bVar.d);
    }

    private static boolean a(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    public int hashCode() {
        return (a(this.b) ^ a(this.c)) ^ a(this.d);
    }

    private static int a(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }
}
