package com.bumptech.glide.load.engine.bitmap_recycle;

import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.util.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/* loaded from: classes.dex */
public final class LruArrayPool implements ArrayPool {
    private final d<a, Object> a;
    private final b b;
    private final Map<Class<?>, NavigableMap<Integer, Integer>> c;
    private final Map<Class<?>, a<?>> d;
    private final int e;
    private int f;

    @VisibleForTesting
    public LruArrayPool() {
        this.a = new d<>();
        this.b = new b();
        this.c = new HashMap();
        this.d = new HashMap();
        this.e = 4194304;
    }

    public LruArrayPool(int i) {
        this.a = new d<>();
        this.b = new b();
        this.c = new HashMap();
        this.d = new HashMap();
        this.e = i;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    @Deprecated
    public <T> void put(T t, Class<T> cls) {
        put(t);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    public synchronized <T> void put(T t) {
        Class<?> cls = t.getClass();
        a<T> b2 = b(cls);
        int arrayLength = b2.getArrayLength(t);
        int elementSizeInBytes = b2.getElementSizeInBytes() * arrayLength;
        if (a(elementSizeInBytes)) {
            a a2 = this.b.a(arrayLength, cls);
            this.a.a(a2, t);
            NavigableMap<Integer, Integer> a3 = a(cls);
            Integer num = (Integer) a3.get(Integer.valueOf(a2.a));
            Integer valueOf = Integer.valueOf(a2.a);
            int i = 1;
            if (num != null) {
                i = 1 + num.intValue();
            }
            a3.put(valueOf, Integer.valueOf(i));
            this.f += elementSizeInBytes;
            b();
        }
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    public synchronized <T> T getExact(int i, Class<T> cls) {
        return (T) a(this.b.a(i, cls), cls);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    public synchronized <T> T get(int i, Class<T> cls) {
        a aVar;
        Integer ceilingKey = a((Class<?>) cls).ceilingKey(Integer.valueOf(i));
        if (a(i, ceilingKey)) {
            aVar = this.b.a(ceilingKey.intValue(), cls);
        } else {
            aVar = this.b.a(i, cls);
        }
        return (T) a(aVar, cls);
    }

    private <T> T a(a aVar, Class<T> cls) {
        a<T> b2 = b(cls);
        T t = (T) a(aVar);
        if (t != null) {
            this.f -= b2.getArrayLength(t) * b2.getElementSizeInBytes();
            a(b2.getArrayLength(t), (Class<?>) cls);
        }
        if (t != null) {
            return t;
        }
        if (Log.isLoggable(b2.getTag(), 2)) {
            Log.v(b2.getTag(), "Allocated " + aVar.a + " bytes");
        }
        return b2.newArray(aVar.a);
    }

    @Nullable
    private <T> T a(a aVar) {
        return (T) this.a.a((d<a, Object>) aVar);
    }

    private boolean a(int i) {
        return i <= this.e / 2;
    }

    private boolean a(int i, Integer num) {
        return num != null && (a() || num.intValue() <= i * 8);
    }

    private boolean a() {
        int i = this.f;
        return i == 0 || this.e / i >= 2;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    public synchronized void clearMemory() {
        b(0);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    public synchronized void trimMemory(int i) {
        try {
            if (i >= 40) {
                clearMemory();
            } else if (i >= 20 || i == 15) {
                b(this.e / 2);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    private void b() {
        b(this.e);
    }

    private void b(int i) {
        while (this.f > i) {
            Object a2 = this.a.a();
            Preconditions.checkNotNull(a2);
            a a3 = a((LruArrayPool) a2);
            this.f -= a3.getArrayLength(a2) * a3.getElementSizeInBytes();
            a(a3.getArrayLength(a2), a2.getClass());
            if (Log.isLoggable(a3.getTag(), 2)) {
                Log.v(a3.getTag(), "evicted: " + a3.getArrayLength(a2));
            }
        }
    }

    private void a(int i, Class<?> cls) {
        NavigableMap<Integer, Integer> a2 = a(cls);
        Integer num = (Integer) a2.get(Integer.valueOf(i));
        if (num == null) {
            throw new NullPointerException("Tried to decrement empty size, size: " + i + ", this: " + this);
        } else if (num.intValue() == 1) {
            a2.remove(Integer.valueOf(i));
        } else {
            a2.put(Integer.valueOf(i), Integer.valueOf(num.intValue() - 1));
        }
    }

    private NavigableMap<Integer, Integer> a(Class<?> cls) {
        NavigableMap<Integer, Integer> navigableMap = this.c.get(cls);
        if (navigableMap != null) {
            return navigableMap;
        }
        TreeMap treeMap = new TreeMap();
        this.c.put(cls, treeMap);
        return treeMap;
    }

    private <T> a<T> a(T t) {
        return b(t.getClass());
    }

    private <T> a<T> b(Class<T> cls) {
        ByteArrayAdapter byteArrayAdapter = (a<T>) this.d.get(cls);
        if (byteArrayAdapter == null) {
            if (cls.equals(int[].class)) {
                byteArrayAdapter = new IntegerArrayAdapter();
            } else if (cls.equals(byte[].class)) {
                byteArrayAdapter = new ByteArrayAdapter();
            } else {
                throw new IllegalArgumentException("No array pool found for: " + cls.getSimpleName());
            }
            this.d.put(cls, byteArrayAdapter);
        }
        return byteArrayAdapter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class b extends c<a> {
        b() {
        }

        a a(int i, Class<?> cls) {
            a c = c();
            c.a(i, cls);
            return c;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public a b() {
            return new a(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a implements f {
        int a;
        private final b b;
        private Class<?> c;

        a(b bVar) {
            this.b = bVar;
        }

        void a(int i, Class<?> cls) {
            this.a = i;
            this.c = cls;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.a == aVar.a && this.c == aVar.c;
        }

        public String toString() {
            return "Key{size=" + this.a + "array=" + this.c + '}';
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.f
        public void a() {
            this.b.a(this);
        }

        public int hashCode() {
            int i = this.a * 31;
            Class<?> cls = this.c;
            return i + (cls != null ? cls.hashCode() : 0);
        }
    }
}
