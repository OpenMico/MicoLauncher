package java8.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java8.util.function.Consumer;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: HMSpliterators.java */
/* loaded from: classes5.dex */
public final class q {
    private static final Unsafe a = al.a;
    private static final long b;
    private static final long c;
    private static final long d;
    private static final long e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K> Spliterator<K> a(Set<K> set) {
        return new c(c(set), 0, -1, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> Spliterator<Map.Entry<K, V>> b(Set<Map.Entry<K, V>> set) {
        return new a(d(set), 0, -1, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <V> Spliterator<V> a(Collection<V> collection) {
        return new d(b(collection), 0, -1, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <E> Spliterator<E> a(HashSet<E> hashSet) {
        return new c(b((HashSet) hashSet), 0, -1, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: HMSpliterators.java */
    /* loaded from: classes5.dex */
    public static final class c<K, V> extends b<K, V> implements Spliterator<K> {
        c(HashMap<K, V> hashMap, int i, int i2, int i3, int i4) {
            super(hashMap, i, i2, i3, i4);
        }

        /* renamed from: a */
        public c<K, V> trySplit() {
            int b = b();
            int i = this.c;
            int i2 = (b + i) >>> 1;
            if (i >= i2 || this.b != null) {
                return null;
            }
            HashMap hashMap = this.a;
            this.c = i2;
            int i3 = this.e >>> 1;
            this.e = i3;
            return new c<>(hashMap, i, i2, i3, this.f);
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super K> consumer) {
            int i;
            int i2;
            Objects.requireNonNull(consumer);
            HashMap hashMap = this.a;
            Object[] b = b((HashMap<?, ?>) hashMap);
            int i3 = this.d;
            if (i3 < 0) {
                int a = a((HashMap<?, ?>) hashMap);
                this.f = a;
                int length = b == null ? 0 : b.length;
                this.d = length;
                i = a;
                i3 = length;
            } else {
                i = this.f;
            }
            if (b != null && b.length >= i3 && (i2 = this.c) >= 0) {
                this.c = i3;
                if (i2 < i3 || this.b != null) {
                    Object obj = this.b;
                    this.b = null;
                    while (true) {
                        if (obj == null) {
                            i2++;
                            obj = b[i2];
                        } else {
                            consumer.accept((Object) b.a(obj));
                            obj = c(obj);
                        }
                        if (obj == null && i2 >= i3) {
                            break;
                        }
                    }
                    if (i != a((HashMap<?, ?>) hashMap)) {
                        throw new ConcurrentModificationException();
                    }
                }
            }
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super K> consumer) {
            int b;
            Objects.requireNonNull(consumer);
            Object[] b2 = b((HashMap<?, ?>) this.a);
            if (b2 == null || b2.length < (b = b()) || this.c < 0) {
                return false;
            }
            while (true) {
                if (this.b == null && this.c >= b) {
                    return false;
                }
                if (this.b == null) {
                    int i = this.c;
                    this.c = i + 1;
                    this.b = b2[i];
                } else {
                    this.b = c(this.b);
                    consumer.accept((Object) a(this.b));
                    if (this.f == a((HashMap<?, ?>) this.a)) {
                        return true;
                    }
                    throw new ConcurrentModificationException();
                }
            }
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return ((this.d < 0 || this.e == this.a.size()) ? 64 : 0) | 1;
        }

        @Override // java8.util.Spliterator
        public Comparator<? super K> getComparator() {
            return Spliterators.getComparator(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: HMSpliterators.java */
    /* loaded from: classes5.dex */
    public static final class d<K, V> extends b<K, V> implements Spliterator<V> {
        d(HashMap<K, V> hashMap, int i, int i2, int i3, int i4) {
            super(hashMap, i, i2, i3, i4);
        }

        /* renamed from: a */
        public d<K, V> trySplit() {
            int b = b();
            int i = this.c;
            int i2 = (b + i) >>> 1;
            if (i >= i2 || this.b != null) {
                return null;
            }
            HashMap hashMap = this.a;
            this.c = i2;
            int i3 = this.e >>> 1;
            this.e = i3;
            return new d<>(hashMap, i, i2, i3, this.f);
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super V> consumer) {
            int i;
            int i2;
            Objects.requireNonNull(consumer);
            HashMap hashMap = this.a;
            Object[] b = b((HashMap<?, ?>) hashMap);
            int i3 = this.d;
            if (i3 < 0) {
                int a = a((HashMap<?, ?>) hashMap);
                this.f = a;
                int length = b == null ? 0 : b.length;
                this.d = length;
                i = a;
                i3 = length;
            } else {
                i = this.f;
            }
            if (b != null && b.length >= i3 && (i2 = this.c) >= 0) {
                this.c = i3;
                if (i2 < i3 || this.b != null) {
                    Object obj = this.b;
                    this.b = null;
                    while (true) {
                        if (obj == null) {
                            i2++;
                            obj = b[i2];
                        } else {
                            consumer.accept((Object) b.b(obj));
                            obj = c(obj);
                        }
                        if (obj == null && i2 >= i3) {
                            break;
                        }
                    }
                    if (i != a((HashMap<?, ?>) hashMap)) {
                        throw new ConcurrentModificationException();
                    }
                }
            }
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super V> consumer) {
            int b;
            Objects.requireNonNull(consumer);
            Object[] b2 = b((HashMap<?, ?>) this.a);
            if (b2 == null || b2.length < (b = b()) || this.c < 0) {
                return false;
            }
            while (true) {
                if (this.b == null && this.c >= b) {
                    return false;
                }
                if (this.b == null) {
                    int i = this.c;
                    this.c = i + 1;
                    this.b = b2[i];
                } else {
                    this.b = c(this.b);
                    consumer.accept((Object) b(this.b));
                    if (this.f == a((HashMap<?, ?>) this.a)) {
                        return true;
                    }
                    throw new ConcurrentModificationException();
                }
            }
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return (this.d < 0 || this.e == this.a.size()) ? 64 : 0;
        }

        @Override // java8.util.Spliterator
        public Comparator<? super V> getComparator() {
            return Spliterators.getComparator(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: HMSpliterators.java */
    /* loaded from: classes5.dex */
    public static final class a<K, V> extends b<K, V> implements Spliterator<Map.Entry<K, V>> {
        a(HashMap<K, V> hashMap, int i, int i2, int i3, int i4) {
            super(hashMap, i, i2, i3, i4);
        }

        /* renamed from: a */
        public a<K, V> trySplit() {
            int b = b();
            int i = this.c;
            int i2 = (b + i) >>> 1;
            if (i >= i2 || this.b != null) {
                return null;
            }
            HashMap hashMap = this.a;
            this.c = i2;
            int i3 = this.e >>> 1;
            this.e = i3;
            return new a<>(hashMap, i, i2, i3, this.f);
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Map.Entry<K, V>> consumer) {
            int i;
            int i2;
            Objects.requireNonNull(consumer);
            HashMap hashMap = this.a;
            Object[] b = b((HashMap<?, ?>) hashMap);
            int i3 = this.d;
            if (i3 < 0) {
                int a = a((HashMap<?, ?>) hashMap);
                this.f = a;
                int length = b == null ? 0 : b.length;
                this.d = length;
                i = a;
                i3 = length;
            } else {
                i = this.f;
            }
            if (b != null && b.length >= i3 && (i2 = this.c) >= 0) {
                this.c = i3;
                if (i2 < i3 || this.b != null) {
                    Object obj = this.b;
                    this.b = null;
                    while (true) {
                        if (obj == null) {
                            i2++;
                            obj = b[i2];
                        } else {
                            consumer.accept((Map.Entry) obj);
                            obj = c(obj);
                        }
                        if (obj == null && i2 >= i3) {
                            break;
                        }
                    }
                    if (i != a((HashMap<?, ?>) hashMap)) {
                        throw new ConcurrentModificationException();
                    }
                }
            }
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> consumer) {
            int b;
            Objects.requireNonNull(consumer);
            Object[] b2 = b((HashMap<?, ?>) this.a);
            if (b2 == null || b2.length < (b = b()) || this.c < 0) {
                return false;
            }
            while (true) {
                if (this.b == null && this.c >= b) {
                    return false;
                }
                if (this.b == null) {
                    int i = this.c;
                    this.c = i + 1;
                    this.b = b2[i];
                } else {
                    this.b = c(this.b);
                    consumer.accept((Map.Entry) this.b);
                    if (this.f == a((HashMap<?, ?>) this.a)) {
                        return true;
                    }
                    throw new ConcurrentModificationException();
                }
            }
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            return ((this.d < 0 || this.e == this.a.size()) ? 64 : 0) | 1;
        }

        @Override // java8.util.Spliterator
        public Comparator<? super Map.Entry<K, V>> getComparator() {
            return Spliterators.getComparator(null);
        }
    }

    /* compiled from: HMSpliterators.java */
    /* loaded from: classes5.dex */
    private static abstract class b<K, V> {
        private static final Unsafe g = al.a;
        private static final long h;
        private static final long i;
        private static final long j;
        private static final long k;
        private static final long l;
        final HashMap<K, V> a;
        Object b;
        int c;
        int d;
        int e;
        int f;

        b(HashMap<K, V> hashMap, int i2, int i3, int i4, int i5) {
            this.a = hashMap;
            this.c = i2;
            this.d = i3;
            this.e = i4;
            this.f = i5;
        }

        final int b() {
            int i2 = this.d;
            if (i2 < 0) {
                HashMap<K, V> hashMap = this.a;
                this.e = hashMap.size();
                this.f = a((HashMap<?, ?>) hashMap);
                Object[] b = b((HashMap<?, ?>) hashMap);
                i2 = b == null ? 0 : b.length;
                this.d = i2;
            }
            return i2;
        }

        public final long getExactSizeIfKnown() {
            return Spliterators.getExactSizeIfKnown((Spliterator) this);
        }

        public final boolean hasCharacteristics(int i2) {
            return Spliterators.hasCharacteristics((Spliterator) this, i2);
        }

        public final long estimateSize() {
            b();
            return this.e;
        }

        static int a(HashMap<?, ?> hashMap) {
            return g.getInt(hashMap, i);
        }

        static Object[] b(HashMap<?, ?> hashMap) {
            return (Object[]) g.getObject(hashMap, h);
        }

        static <K> K a(Object obj) {
            return (K) g.getObject(obj, j);
        }

        static <T> T b(Object obj) {
            return (T) g.getObject(obj, k);
        }

        static Object c(Object obj) {
            return g.getObject(obj, l);
        }

        static Class<?> c() throws ClassNotFoundException {
            StringBuilder sb = new StringBuilder("java.util.HashMap$");
            sb.append((Spliterators.c || Spliterators.g) ? "Node" : "Entry");
            try {
                return Class.forName(sb.toString());
            } catch (ClassNotFoundException e) {
                if (Spliterators.c) {
                    return Class.forName("java.util.HashMap$HashMapEntry");
                }
                throw e;
            }
        }

        static {
            try {
                h = g.objectFieldOffset(HashMap.class.getDeclaredField("table"));
                i = g.objectFieldOffset(HashMap.class.getDeclaredField("modCount"));
                Class<?> c = c();
                j = g.objectFieldOffset(c.getDeclaredField("key"));
                k = g.objectFieldOffset(c.getDeclaredField(com.xiaomi.onetrack.api.b.p));
                l = g.objectFieldOffset(c.getDeclaredField("next"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    private static <K, V> HashMap<K, V> c(Set<K> set) {
        return (HashMap) a.getObject(set, c);
    }

    private static <K, V> HashMap<K, V> d(Set<Map.Entry<K, V>> set) {
        return (HashMap) a.getObject(set, d);
    }

    private static <K, V> HashMap<K, V> b(Collection<V> collection) {
        return (HashMap) a.getObject(collection, b);
    }

    private static <K, V> HashMap<K, V> b(HashSet<K> hashSet) {
        return (HashMap) a.getObject(hashSet, e);
    }

    static {
        try {
            Class<?> cls = Class.forName("java.util.HashMap$Values");
            Class<?> cls2 = Class.forName("java.util.HashMap$KeySet");
            Class<?> cls3 = Class.forName("java.util.HashMap$EntrySet");
            b = a.objectFieldOffset(cls.getDeclaredField("this$0"));
            c = a.objectFieldOffset(cls2.getDeclaredField("this$0"));
            d = a.objectFieldOffset(cls3.getDeclaredField("this$0"));
            e = a.objectFieldOffset(HashSet.class.getDeclaredField("map"));
        } catch (Exception e2) {
            throw new Error(e2);
        }
    }
}
