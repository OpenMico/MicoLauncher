package java8.util;

import java.util.Comparator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java8.util.function.Consumer;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LBQSpliterator.java */
/* loaded from: classes5.dex */
public final class y<E> implements Spliterator<E> {
    private static final Unsafe h = al.a;
    private static final long i;
    private static final long j;
    private static final long k;
    private static final long l;
    private static final long m;
    private final LinkedBlockingQueue<E> a;
    private final ReentrantLock b;
    private final ReentrantLock c;
    private Object d;
    private int e;
    private boolean f;
    private long g;

    @Override // java8.util.Spliterator
    public int characteristics() {
        return 4368;
    }

    private y(LinkedBlockingQueue<E> linkedBlockingQueue) {
        this.a = linkedBlockingQueue;
        this.g = linkedBlockingQueue.size();
        this.b = b((LinkedBlockingQueue<?>) linkedBlockingQueue);
        this.c = c((LinkedBlockingQueue<?>) linkedBlockingQueue);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Spliterator<T> a(LinkedBlockingQueue<T> linkedBlockingQueue) {
        return new y(linkedBlockingQueue);
    }

    Object a(Object obj) {
        Object b = b(obj);
        return obj == b ? d(this.a) : b;
    }

    @Override // java8.util.Spliterator
    public long estimateSize() {
        return this.g;
    }

    @Override // java8.util.Spliterator
    public void forEachRemaining(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        if (!this.f) {
            this.f = true;
            Object obj = this.d;
            this.d = null;
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
        if (this.f) {
            return false;
        }
        Object obj = (Object) null;
        a();
        try {
            Object obj2 = this.d;
            if (obj2 != null || (obj2 = d(this.a)) != null) {
                do {
                    obj = (Object) c(obj2);
                    obj2 = a(obj2);
                    if (obj != null) {
                        break;
                    }
                } while (obj2 != null);
            }
            this.d = obj2;
            if (obj2 == null) {
                this.f = true;
            }
            if (obj == null) {
                return false;
            }
            consumer.accept(obj);
            return true;
        } finally {
            b();
        }
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    @Override // java8.util.Spliterator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java8.util.Spliterator<E> trySplit() {
        /*
            r10 = this;
            java.util.concurrent.LinkedBlockingQueue<E> r0 = r10.a
            boolean r1 = r10.f
            if (r1 != 0) goto L_0x0072
            java.lang.Object r1 = r10.d
            if (r1 != 0) goto L_0x0010
            java.lang.Object r1 = d(r0)
            if (r1 == 0) goto L_0x0072
        L_0x0010:
            java.lang.Object r1 = b(r1)
            if (r1 == 0) goto L_0x0072
            int r1 = r10.e
            r2 = 1
            int r1 = r1 + r2
            r3 = 33554432(0x2000000, float:9.403955E-38)
            int r1 = java.lang.Math.min(r1, r3)
            r10.e = r1
            java.lang.Object[] r3 = new java.lang.Object[r1]
            java.lang.Object r4 = r10.d
            r10.a()
            r5 = 0
            if (r4 != 0) goto L_0x0037
            java.lang.Object r4 = d(r0)     // Catch: all -> 0x0035
            if (r4 == 0) goto L_0x0033
            goto L_0x0037
        L_0x0033:
            r0 = r5
            goto L_0x004f
        L_0x0035:
            r0 = move-exception
            goto L_0x004b
        L_0x0037:
            r0 = r5
        L_0x0038:
            if (r4 == 0) goto L_0x004f
            if (r0 >= r1) goto L_0x004f
            java.lang.Object r6 = c(r4)     // Catch: all -> 0x0035
            r3[r0] = r6     // Catch: all -> 0x0035
            if (r6 == 0) goto L_0x0046
            int r0 = r0 + 1
        L_0x0046:
            java.lang.Object r4 = r10.a(r4)     // Catch: all -> 0x0035
            goto L_0x0038
        L_0x004b:
            r10.b()
            throw r0
        L_0x004f:
            r10.b()
            r10.d = r4
            r6 = 0
            if (r4 != 0) goto L_0x005d
            r10.g = r6
            r10.f = r2
            goto L_0x0069
        L_0x005d:
            long r1 = r10.g
            long r8 = (long) r0
            long r1 = r1 - r8
            r10.g = r1
            int r1 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r1 >= 0) goto L_0x0069
            r10.g = r6
        L_0x0069:
            if (r0 <= 0) goto L_0x0072
            r1 = 4368(0x1110, float:6.121E-42)
            java8.util.Spliterator r0 = java8.util.Spliterators.spliterator(r3, r5, r0, r1)
            return r0
        L_0x0072:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: java8.util.y.trySplit():java8.util.Spliterator");
    }

    void a(Consumer<? super E> consumer, Object obj) {
        Object[] objArr = null;
        int i2 = 0;
        do {
            a();
            if (objArr == null) {
                if (obj == null) {
                    try {
                        obj = d(this.a);
                    } catch (Throwable th) {
                        b();
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
            b();
            for (int i4 = 0; i4 < i3; i4++) {
                consumer.accept(objArr[i4]);
            }
            if (i3 <= 0) {
                return;
            }
        } while (obj != null);
    }

    private void a() {
        this.b.lock();
        this.c.lock();
    }

    private void b() {
        this.c.unlock();
        this.b.unlock();
    }

    private static ReentrantLock b(LinkedBlockingQueue<?> linkedBlockingQueue) {
        return (ReentrantLock) h.getObject(linkedBlockingQueue, l);
    }

    private static ReentrantLock c(LinkedBlockingQueue<?> linkedBlockingQueue) {
        return (ReentrantLock) h.getObject(linkedBlockingQueue, m);
    }

    private static <T> Object d(LinkedBlockingQueue<T> linkedBlockingQueue) {
        return b(h.getObject(linkedBlockingQueue, i));
    }

    private static Object b(Object obj) {
        return h.getObject(obj, k);
    }

    private static <T> T c(Object obj) {
        return (T) h.getObject(obj, j);
    }

    static {
        try {
            Class<?> cls = Class.forName("java.util.concurrent.LinkedBlockingQueue$Node");
            i = h.objectFieldOffset(LinkedBlockingQueue.class.getDeclaredField("head"));
            j = h.objectFieldOffset(cls.getDeclaredField("item"));
            k = h.objectFieldOffset(cls.getDeclaredField("next"));
            l = h.objectFieldOffset(LinkedBlockingQueue.class.getDeclaredField("putLock"));
            m = h.objectFieldOffset(LinkedBlockingQueue.class.getDeclaredField("takeLock"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
