package io.netty.buffer;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PoolChunkList.java */
/* loaded from: classes4.dex */
public final class j<T> implements PoolChunkListMetric {
    static final /* synthetic */ boolean a = !j.class.desiredAssertionStatus();
    private static final Iterator<PoolChunkMetric> b = Collections.emptyList().iterator();
    private final j<T> c;
    private final int d;
    private final int e;
    private final int f;
    private i<T> g;
    private j<T> h;

    /* JADX INFO: Access modifiers changed from: package-private */
    public j(j<T> jVar, int i, int i2, int i3) {
        if (a || i <= i2) {
            this.c = jVar;
            this.d = i;
            this.e = i2;
            this.f = a(i, i3);
            return;
        }
        throw new AssertionError();
    }

    private static int a(int i, int i2) {
        int a2 = a(i);
        if (a2 == 100) {
            return 0;
        }
        return (int) ((i2 * (100 - a2)) / 100);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(j<T> jVar) {
        if (a || this.h == null) {
            this.h = jVar;
            return;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(m<T> mVar, int i, int i2) {
        i<T> iVar = this.g;
        if (iVar == null || i2 > this.f) {
            return false;
        }
        do {
            long a2 = iVar.a(i2);
            if (a2 < 0) {
                iVar = iVar.f;
            } else {
                iVar.a(mVar, a2, i);
                if (iVar.usage() < this.e) {
                    return true;
                }
                e(iVar);
                this.c.a(iVar);
                return true;
            }
        } while (iVar != null);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(i<T> iVar, long j) {
        iVar.a(j);
        if (iVar.usage() >= this.d) {
            return true;
        }
        e(iVar);
        return d(iVar);
    }

    private boolean c(i<T> iVar) {
        if (!a && iVar.usage() >= this.e) {
            throw new AssertionError();
        } else if (iVar.usage() < this.d) {
            return d(iVar);
        } else {
            b(iVar);
            return true;
        }
    }

    private boolean d(i<T> iVar) {
        j<T> jVar = this.h;
        if (jVar != null) {
            return jVar.c(iVar);
        }
        if (a || iVar.usage() == 0) {
            return false;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(i<T> iVar) {
        if (iVar.usage() >= this.e) {
            this.c.a(iVar);
        } else {
            b(iVar);
        }
    }

    void b(i<T> iVar) {
        iVar.d = this;
        i<T> iVar2 = this.g;
        if (iVar2 == null) {
            this.g = iVar;
            iVar.e = null;
            iVar.f = null;
            return;
        }
        iVar.e = null;
        iVar.f = iVar2;
        iVar2.e = iVar;
        this.g = iVar;
    }

    private void e(i<T> iVar) {
        if (iVar == this.g) {
            this.g = iVar.f;
            i<T> iVar2 = this.g;
            if (iVar2 != null) {
                iVar2.e = null;
                return;
            }
            return;
        }
        i<T> iVar3 = iVar.f;
        iVar.e.f = iVar3;
        if (iVar3 != null) {
            iVar3.e = iVar.e;
        }
    }

    @Override // io.netty.buffer.PoolChunkListMetric
    public int minUsage() {
        return a(this.d);
    }

    @Override // io.netty.buffer.PoolChunkListMetric
    public int maxUsage() {
        return Math.min(this.e, 100);
    }

    private static int a(int i) {
        return Math.max(1, i);
    }

    @Override // java.lang.Iterable
    public Iterator<PoolChunkMetric> iterator() {
        if (this.g == null) {
            return b;
        }
        ArrayList arrayList = new ArrayList();
        i<T> iVar = this.g;
        do {
            arrayList.add(iVar);
            iVar = iVar.f;
        } while (iVar != null);
        return arrayList.iterator();
    }

    public String toString() {
        if (this.g == null) {
            return "none";
        }
        StringBuilder sb = new StringBuilder();
        i<T> iVar = this.g;
        while (true) {
            sb.append(iVar);
            iVar = iVar.f;
            if (iVar == null) {
                return sb.toString();
            }
            sb.append(StringUtil.NEWLINE);
        }
    }
}
