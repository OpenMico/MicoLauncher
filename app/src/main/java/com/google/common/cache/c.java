package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Supplier;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LongAddables.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class c {
    private static final Supplier<b> a;

    static {
        Supplier<b> supplier;
        try {
            new d();
            supplier = new Supplier<b>() { // from class: com.google.common.cache.c.1
                /* renamed from: a */
                public b get() {
                    return new d();
                }
            };
        } catch (Throwable unused) {
            supplier = new Supplier<b>() { // from class: com.google.common.cache.c.2
                /* renamed from: a */
                public b get() {
                    return new a();
                }
            };
        }
        a = supplier;
    }

    public static b a() {
        return a.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: LongAddables.java */
    /* loaded from: classes2.dex */
    public static final class a extends AtomicLong implements b {
        private a() {
        }

        @Override // com.google.common.cache.b
        public void a() {
            getAndIncrement();
        }

        @Override // com.google.common.cache.b
        public void a(long j) {
            getAndAdd(j);
        }

        @Override // com.google.common.cache.b
        public long b() {
            return get();
        }
    }
}
