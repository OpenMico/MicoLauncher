package com.bumptech.glide.load.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.util.LruCache;
import com.bumptech.glide.util.Util;
import java.util.Queue;

/* loaded from: classes.dex */
public class ModelCache<A, B> {
    private final LruCache<a<A>, B> a;

    public ModelCache() {
        this(250L);
    }

    public ModelCache(long j) {
        this.a = new LruCache<a<A>, B>(j) { // from class: com.bumptech.glide.load.model.ModelCache.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public void onItemEvicted(@NonNull a<A> aVar, @Nullable B b) {
                aVar.a();
            }
        };
    }

    @Nullable
    public B get(A a2, int i, int i2) {
        a<A> a3 = a.a(a2, i, i2);
        B b = this.a.get(a3);
        a3.a();
        return b;
    }

    public void put(A a2, int i, int i2, B b) {
        this.a.put(a.a(a2, i, i2), b);
    }

    public void clear() {
        this.a.clearMemory();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* loaded from: classes.dex */
    public static final class a<A> {
        private static final Queue<a<?>> a = Util.createQueue(0);
        private int b;
        private int c;
        private A d;

        static <A> a<A> a(A a2, int i, int i2) {
            a<A> aVar;
            synchronized (a) {
                aVar = (a<A>) a.poll();
            }
            if (aVar == null) {
                aVar = new a<>();
            }
            aVar.b(a2, i, i2);
            return aVar;
        }

        private a() {
        }

        private void b(A a2, int i, int i2) {
            this.d = a2;
            this.c = i;
            this.b = i2;
        }

        public void a() {
            synchronized (a) {
                a.offer(this);
            }
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.c == aVar.c && this.b == aVar.b && this.d.equals(aVar.d);
        }

        public int hashCode() {
            return (((this.b * 31) + this.c) * 31) + this.d.hashCode();
        }
    }
}
