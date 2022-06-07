package miuix.animation.internal;

import miuix.animation.utils.ObjectPool;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AnimStats.java */
/* loaded from: classes5.dex */
public class c implements ObjectPool.IPoolObject {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;

    public void a(c cVar) {
        this.g += cVar.g;
        this.a += cVar.a;
        this.b += cVar.b;
        this.c += cVar.c;
        this.d += cVar.d;
        this.e += cVar.e;
        this.f += cVar.f;
    }

    public boolean a() {
        return this.b > 0;
    }

    public boolean b() {
        return !a() || (this.e + this.f) + this.c < this.g;
    }

    @Override // miuix.animation.utils.ObjectPool.IPoolObject
    public void clear() {
        this.g = 0;
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
    }

    public String toString() {
        return "AnimStats{animCount = " + this.g + ", startCount=" + this.a + ", startedCount = " + this.b + ", failCount=" + this.c + ", updateCount=" + this.d + ", cancelCount=" + this.e + ", endCount=" + this.f + '}';
    }
}
