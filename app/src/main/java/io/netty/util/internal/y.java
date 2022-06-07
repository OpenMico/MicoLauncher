package io.netty.util.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import sun.misc.Unsafe;

/* compiled from: UnsafeAtomicIntegerFieldUpdater.java */
/* loaded from: classes4.dex */
final class y<T> extends AtomicIntegerFieldUpdater<T> {
    private final long a;
    private final Unsafe b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public y(Unsafe unsafe, Class<? super T> cls, String str) throws NoSuchFieldException {
        Field declaredField = cls.getDeclaredField(str);
        if (Modifier.isVolatile(declaredField.getModifiers())) {
            this.b = unsafe;
            this.a = unsafe.objectFieldOffset(declaredField);
            return;
        }
        throw new IllegalArgumentException("Must be volatile");
    }

    @Override // java.util.concurrent.atomic.AtomicIntegerFieldUpdater
    public boolean compareAndSet(T t, int i, int i2) {
        return this.b.compareAndSwapInt(t, this.a, i, i2);
    }

    @Override // java.util.concurrent.atomic.AtomicIntegerFieldUpdater
    public boolean weakCompareAndSet(T t, int i, int i2) {
        return this.b.compareAndSwapInt(t, this.a, i, i2);
    }

    @Override // java.util.concurrent.atomic.AtomicIntegerFieldUpdater
    public void set(T t, int i) {
        this.b.putIntVolatile(t, this.a, i);
    }

    @Override // java.util.concurrent.atomic.AtomicIntegerFieldUpdater
    public void lazySet(T t, int i) {
        this.b.putOrderedInt(t, this.a, i);
    }

    @Override // java.util.concurrent.atomic.AtomicIntegerFieldUpdater
    public int get(T t) {
        return this.b.getIntVolatile(t, this.a);
    }
}
