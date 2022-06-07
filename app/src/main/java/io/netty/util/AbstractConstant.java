package io.netty.util;

import io.netty.util.AbstractConstant;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThreadLocalRandom;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public abstract class AbstractConstant<T extends AbstractConstant<T>> implements Constant<T> {
    private final int a;
    private final String b;
    private volatile long c;
    private ByteBuffer d;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return compareTo((AbstractConstant<T>) obj);
    }

    public AbstractConstant(int i, String str) {
        this.a = i;
        this.b = str;
    }

    @Override // io.netty.util.Constant
    public final String name() {
        return this.b;
    }

    @Override // io.netty.util.Constant
    public final int id() {
        return this.a;
    }

    public final String toString() {
        return name();
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final int compareTo(T t) {
        if (this == t) {
            return 0;
        }
        int hashCode = hashCode() - t.hashCode();
        if (hashCode != 0) {
            return hashCode;
        }
        int i = (a() > t.a() ? 1 : (a() == t.a() ? 0 : -1));
        if (i < 0) {
            return -1;
        }
        if (i > 0) {
            return 1;
        }
        throw new Error("failed to compare two different constants");
    }

    private long a() {
        long j = this.c;
        if (j == 0) {
            synchronized (this) {
                while (true) {
                    j = this.c;
                    if (j != 0) {
                        break;
                    } else if (PlatformDependent.hasUnsafe()) {
                        this.d = ByteBuffer.allocateDirect(1);
                        this.c = PlatformDependent.directBufferAddress(this.d);
                    } else {
                        this.d = null;
                        this.c = ThreadLocalRandom.current().nextLong();
                    }
                }
            }
        }
        return j;
    }
}
