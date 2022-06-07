package io.netty.util.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import sun.misc.Unsafe;

/* compiled from: UnsafeAtomicReferenceFieldUpdater.java */
/* loaded from: classes4.dex */
final class aa<U, M> extends AtomicReferenceFieldUpdater<U, M> {
    private final long a;
    private final Unsafe b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aa(Unsafe unsafe, Class<? super U> cls, String str) throws NoSuchFieldException {
        Field declaredField = cls.getDeclaredField(str);
        if (Modifier.isVolatile(declaredField.getModifiers())) {
            this.b = unsafe;
            this.a = unsafe.objectFieldOffset(declaredField);
            return;
        }
        throw new IllegalArgumentException("Must be volatile");
    }

    @Override // java.util.concurrent.atomic.AtomicReferenceFieldUpdater
    public boolean compareAndSet(U u, M m, M m2) {
        return this.b.compareAndSwapObject(u, this.a, m, m2);
    }

    @Override // java.util.concurrent.atomic.AtomicReferenceFieldUpdater
    public boolean weakCompareAndSet(U u, M m, M m2) {
        return this.b.compareAndSwapObject(u, this.a, m, m2);
    }

    @Override // java.util.concurrent.atomic.AtomicReferenceFieldUpdater
    public void set(U u, M m) {
        this.b.putObjectVolatile(u, this.a, m);
    }

    @Override // java.util.concurrent.atomic.AtomicReferenceFieldUpdater
    public void lazySet(U u, M m) {
        this.b.putOrderedObject(u, this.a, m);
    }

    @Override // java.util.concurrent.atomic.AtomicReferenceFieldUpdater
    public M get(U u) {
        return (M) this.b.getObjectVolatile(u, this.a);
    }
}
