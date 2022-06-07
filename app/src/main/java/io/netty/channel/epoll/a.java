package io.netty.channel.epoll;

import io.netty.util.internal.PlatformDependent;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: EpollEventArray.java */
/* loaded from: classes4.dex */
public final class a {
    private static final int a = Native.sizeofEpollEvent();
    private static final int b = Native.offsetofEpollData();
    private long c;
    private int d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(int i) {
        if (i >= 1) {
            this.d = i;
            this.c = c(i);
            return;
        }
        throw new IllegalArgumentException("length must be >= 1 but was " + i);
    }

    private static long c(int i) {
        return PlatformDependent.allocateMemory(i * a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long a() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c() {
        this.d <<= 1;
        d();
        this.c = c(this.d);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void d() {
        PlatformDependent.freeMemory(this.c);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(int i) {
        return PlatformDependent.getInt(this.c + (i * a));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b(int i) {
        return PlatformDependent.getInt(this.c + (i * a) + b);
    }
}
