package com.google.common.hash;

import com.google.common.base.Supplier;
import java.util.concurrent.atomic.AtomicLong;

/* compiled from: LongAddables.java */
/* loaded from: classes2.dex */
final class n {
    private static final Supplier<m> a;

    static {
        Supplier<m> supplier;
        try {
            new o();
            supplier = new Supplier<m>() { // from class: com.google.common.hash.n.1
                /* renamed from: a */
                public m get() {
                    return new o();
                }
            };
        } catch (Throwable unused) {
            supplier = new Supplier<m>() { // from class: com.google.common.hash.n.2
                /* renamed from: a */
                public m get() {
                    return new a();
                }
            };
        }
        a = supplier;
    }

    public static m a() {
        return a.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: LongAddables.java */
    /* loaded from: classes2.dex */
    public static final class a extends AtomicLong implements m {
        private a() {
        }

        @Override // com.google.common.hash.m
        public void a() {
            getAndIncrement();
        }

        @Override // com.google.common.hash.m
        public void a(long j) {
            getAndAdd(j);
        }

        @Override // com.google.common.hash.m
        public long b() {
            return get();
        }
    }
}
