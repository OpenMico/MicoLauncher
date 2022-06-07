package java8.util;

import com.milink.base.contract.LockContract;
import java.util.Comparator;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;
import java8.util.function.Consumer;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LBDSpliterator.java */
/* loaded from: classes5.dex */
public final class x<E> implements Spliterator<E> {
    private static final Unsafe g = al.a;
    private static final long h;
    private static final long i;
    private static final long j;
    private static final long k;
    private final LinkedBlockingDeque<E> a;
    private final ReentrantLock b;
    private Object c;
    private int d;
    private boolean e;
    private long f;

    @Override // java8.util.Spliterator
    public int characteristics() {
        return 4368;
    }

    private x(LinkedBlockingDeque<E> linkedBlockingDeque) {
        this.a = linkedBlockingDeque;
        this.f = linkedBlockingDeque.size();
        this.b = b((LinkedBlockingDeque<?>) linkedBlockingDeque);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Spliterator<T> a(LinkedBlockingDeque<T> linkedBlockingDeque) {
        return new x(linkedBlockingDeque);
    }

    Object a(Object obj) {
        Object b = b(obj);
        return obj == b ? c((LinkedBlockingDeque<?>) this.a) : b;
    }

    @Override // java8.util.Spliterator
    public long estimateSize() {
        return this.f;
    }

    @Override // java8.util.Spliterator
    public void forEachRemaining(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        if (!this.e) {
            this.e = true;
            Object obj = this.c;
            this.c = null;
            a(consumer, obj);
        }
    }

    @Override // java8.util.Spliterator
    public Comparator<? super E> getComparator() {
        return Spliterators.getComparator(this);
    }

    @Override // java8.util.Spliterator
    public long getExactSizeIfKnown() {
        return Spliterators.getExactSizeIfKnown(this);
    }

    @Override // java8.util.Spliterator
    public boolean hasCharacteristics(int i2) {
        return Spliterators.hasCharacteristics(this, i2);
    }

    @Override // java8.util.Spliterator
    public boolean tryAdvance(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        if (this.e) {
            return false;
        }
        Object obj = (Object) null;
        ReentrantLock reentrantLock = this.b;
        reentrantLock.lock();
        try {
            Object obj2 = this.c;
            if (obj2 != null || (obj2 = c((LinkedBlockingDeque<?>) this.a)) != null) {
                do {
                    obj = (Object) c(obj2);
                    obj2 = a(obj2);
                    if (obj != null) {
                        break;
                    }
                } while (obj2 != null);
            }
            this.c = obj2;
            if (obj2 == null) {
                this.e = true;
            }
            if (obj == null) {
                return false;
            }
            consumer.accept(obj);
            return true;
        } finally {
            reentrantLock.unlock();
        }
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    @Override // java8.util.Spliterator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java8.util.Spliterator<E> trySplit() {
        /*
            r9 = this;
            java.util.concurrent.LinkedBlockingDeque<E> r0 = r9.a
            boolean r1 = r9.e
            if (r1 != 0) goto L_0x0074
            java.lang.Object r1 = r9.c
            if (r1 != 0) goto L_0x0010
            java.lang.Object r1 = c(r0)
            if (r1 == 0) goto L_0x0074
        L_0x0010:
            java.lang.Object r1 = b(r1)
            if (r1 == 0) goto L_0x0074
            int r1 = r9.d
            r2 = 1
            int r1 = r1 + r2
            r3 = 33554432(0x2000000, float:9.403955E-38)
            int r1 = java.lang.Math.min(r1, r3)
            r9.d = r1
            java.lang.Object[] r3 = new java.lang.Object[r1]
            java.util.concurrent.locks.ReentrantLock r4 = r9.b
            java.lang.Object r5 = r9.c
            r4.lock()
            r6 = 0
            if (r5 != 0) goto L_0x0039
            java.lang.Object r5 = c(r0)     // Catch: all -> 0x0037
            if (r5 == 0) goto L_0x0035
            goto L_0x0039
        L_0x0035:
            r0 = r6
            goto L_0x0051
        L_0x0037:
            r0 = move-exception
            goto L_0x004d
        L_0x0039:
            r0 = r6
        L_0x003a:
            if (r5 == 0) goto L_0x0051
            if (r0 >= r1) goto L_0x0051
            java.lang.Object r7 = c(r5)     // Catch: all -> 0x0037
            r3[r0] = r7     // Catch: all -> 0x0037
            if (r7 == 0) goto L_0x0048
            int r0 = r0 + 1
        L_0x0048:
            java.lang.Object r5 = r9.a(r5)     // Catch: all -> 0x0037
            goto L_0x003a
        L_0x004d:
            r4.unlock()
            throw r0
        L_0x0051:
            r4.unlock()
            r9.c = r5
            r7 = 0
            if (r5 != 0) goto L_0x005f
            r9.f = r7
            r9.e = r2
            goto L_0x006b
        L_0x005f:
            long r1 = r9.f
            long r4 = (long) r0
            long r1 = r1 - r4
            r9.f = r1
            int r1 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
            if (r1 >= 0) goto L_0x006b
            r9.f = r7
        L_0x006b:
            if (r0 <= 0) goto L_0x0074
            r1 = 4368(0x1110, float:6.121E-42)
            java8.util.Spliterator r0 = java8.util.Spliterators.spliterator(r3, r6, r0, r1)
            return r0
        L_0x0074:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: java8.util.x.trySplit():java8.util.Spliterator");
    }

    void a(Consumer<? super E> consumer, Object obj) {
        ReentrantLock reentrantLock = this.b;
        Object[] objArr = null;
        int i2 = 0;
        do {
            reentrantLock.lock();
            if (objArr == null) {
                if (obj == null) {
                    try {
                        obj = c((LinkedBlockingDeque<?>) this.a);
                    } catch (Throwable th) {
                        reentrantLock.unlock();
                        throw th;
                    }
                }
                Object obj2 = obj;
                while (obj2 != null && (c(obj2) == null || (i2 = i2 + 1) != 64)) {
                    obj2 = a(obj2);
                }
                objArr = new Object[i2];
            }
            int i3 = 0;
            while (obj != null && i3 < i2) {
                Object c = c(obj);
                objArr[i3] = c;
                if (c != null) {
                    i3++;
                }
                obj = a(obj);
            }
            reentrantLock.unlock();
            for (int i4 = 0; i4 < i3; i4++) {
                consumer.accept(objArr[i4]);
            }
            if (i3 <= 0) {
                return;
            }
        } while (obj != null);
    }

    private static ReentrantLock b(LinkedBlockingDeque<?> linkedBlockingDeque) {
        return (ReentrantLock) g.getObject(linkedBlockingDeque, i);
    }

    private static Object c(LinkedBlockingDeque<?> linkedBlockingDeque) {
        return g.getObject(linkedBlockingDeque, h);
    }

    private static Object b(Object obj) {
        return g.getObject(obj, k);
    }

    private static <T> T c(Object obj) {
        return (T) g.getObject(obj, j);
    }

    static {
        try {
            Class<?> cls = Class.forName("java.util.concurrent.LinkedBlockingDeque$Node");
            h = g.objectFieldOffset(LinkedBlockingDeque.class.getDeclaredField("first"));
            i = g.objectFieldOffset(LinkedBlockingDeque.class.getDeclaredField(LockContract.Matcher.LOCK));
            j = g.objectFieldOffset(cls.getDeclaredField("item"));
            k = g.objectFieldOffset(cls.getDeclaredField("next"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
