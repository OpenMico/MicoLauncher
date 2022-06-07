package io.netty.util.concurrent;

import java.util.Arrays;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DefaultFutureListeners.java */
/* loaded from: classes4.dex */
public final class a {
    private GenericFutureListener<? extends Future<?>>[] a = new GenericFutureListener[2];
    private int b = 2;
    private int c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(GenericFutureListener<? extends Future<?>> genericFutureListener, GenericFutureListener<? extends Future<?>> genericFutureListener2) {
        GenericFutureListener<? extends Future<?>>[] genericFutureListenerArr = this.a;
        genericFutureListenerArr[0] = genericFutureListener;
        genericFutureListenerArr[1] = genericFutureListener2;
        if (genericFutureListener instanceof GenericProgressiveFutureListener) {
            this.c++;
        }
        if (genericFutureListener2 instanceof GenericProgressiveFutureListener) {
            this.c++;
        }
    }

    public void a(GenericFutureListener<? extends Future<?>> genericFutureListener) {
        GenericFutureListener<? extends Future<?>>[] genericFutureListenerArr = this.a;
        int i = this.b;
        if (i == genericFutureListenerArr.length) {
            genericFutureListenerArr = (GenericFutureListener[]) Arrays.copyOf(genericFutureListenerArr, i << 1);
            this.a = genericFutureListenerArr;
        }
        genericFutureListenerArr[i] = genericFutureListener;
        this.b = i + 1;
        if (genericFutureListener instanceof GenericProgressiveFutureListener) {
            this.c++;
        }
    }

    public void b(GenericFutureListener<? extends Future<?>> genericFutureListener) {
        GenericFutureListener<? extends Future<?>>[] genericFutureListenerArr = this.a;
        int i = this.b;
        for (int i2 = 0; i2 < i; i2++) {
            if (genericFutureListenerArr[i2] == genericFutureListener) {
                int i3 = (i - i2) - 1;
                if (i3 > 0) {
                    System.arraycopy(genericFutureListenerArr, i2 + 1, genericFutureListenerArr, i2, i3);
                }
                int i4 = i - 1;
                genericFutureListenerArr[i4] = null;
                this.b = i4;
                if (genericFutureListener instanceof GenericProgressiveFutureListener) {
                    this.c--;
                    return;
                }
                return;
            }
        }
    }

    public GenericFutureListener<? extends Future<?>>[] a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }

    public int c() {
        return this.c;
    }
}
