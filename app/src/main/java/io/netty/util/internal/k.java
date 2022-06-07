package io.netty.util.internal;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MpscArrayQueue.java */
/* loaded from: classes4.dex */
public final class k<E> extends l<E> {
    public k(int i) {
        super(i);
    }

    @Override // java.util.Queue
    public boolean offer(E e) {
        long c;
        if (e != null) {
            long j = this.b;
            long j2 = j + 1;
            long b = b();
            do {
                c = c();
                long j3 = c - j2;
                if (b <= j3) {
                    b = a();
                    if (b <= j3) {
                        return false;
                    }
                    c(b);
                }
            } while (!b(c, c + 1));
            a(a(c, j), (long) e);
            return true;
        }
        throw new NullPointerException("Null is not a valid element");
    }

    @Override // java.util.Queue
    public E poll() {
        long a = a();
        long a2 = a(a);
        Object[] objArr = this.c;
        E e = (E) a(objArr, a2);
        if (e != null) {
            a(objArr, a2, null);
            b(a + 1);
            return e;
        } else if (a == c()) {
            return null;
        } else {
            do {
                e = (E) a(objArr, a2);
            } while (e == null);
            a(objArr, a2, null);
            b(a + 1);
            return e;
        }
    }

    @Override // java.util.Queue
    public E peek() {
        Object[] objArr = this.c;
        long a = a();
        long a2 = a(a);
        E e = (E) a(objArr, a2);
        if (e != null) {
            return e;
        }
        if (a == c()) {
            return null;
        }
        do {
            e = (E) a(objArr, a2);
        } while (e == null);
        return e;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        long a = a();
        while (true) {
            long c = c();
            long a2 = a();
            if (a == a2) {
                return (int) (c - a2);
            }
            a = a2;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return a() == c();
    }
}
