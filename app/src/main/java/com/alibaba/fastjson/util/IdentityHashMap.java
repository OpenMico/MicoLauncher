package com.alibaba.fastjson.util;

import java.util.Arrays;

/* loaded from: classes.dex */
public class IdentityHashMap<K, V> {
    public static final int DEFAULT_SIZE = 8192;
    private final Entry<K, V>[] buckets;
    private final int indexMask;

    public IdentityHashMap() {
        this(8192);
    }

    public IdentityHashMap(int i) {
        this.indexMask = i - 1;
        this.buckets = new Entry[i];
    }

    public final V get(K k) {
        for (Entry<K, V> entry = this.buckets[System.identityHashCode(k) & this.indexMask]; entry != null; entry = entry.next) {
            if (k == entry.key) {
                return entry.value;
            }
        }
        return null;
    }

    public Class findClass(String str) {
        int i = 0;
        while (true) {
            Entry<K, V>[] entryArr = this.buckets;
            if (i >= entryArr.length) {
                return null;
            }
            Entry<K, V> entry = entryArr[i];
            if (entry != null) {
                for (Entry<K, V> entry2 = entry; entry2 != null; entry2 = entry2.next) {
                    K k = entry.key;
                    if (k instanceof Class) {
                        Class cls = (Class) k;
                        if (cls.getName().equals(str)) {
                            return cls;
                        }
                    }
                }
                continue;
            }
            i++;
        }
    }

    public boolean put(K k, V v) {
        int identityHashCode = System.identityHashCode(k);
        int i = this.indexMask & identityHashCode;
        for (Entry<K, V> entry = this.buckets[i]; entry != null; entry = entry.next) {
            if (k == entry.key) {
                entry.value = v;
                return true;
            }
        }
        this.buckets[i] = new Entry<>(k, v, identityHashCode, this.buckets[i]);
        return false;
    }

    /* loaded from: classes.dex */
    protected static final class Entry<K, V> {
        public final int hashCode;
        public final K key;
        public final Entry<K, V> next;
        public V value;

        public Entry(K k, V v, int i, Entry<K, V> entry) {
            this.key = k;
            this.value = v;
            this.next = entry;
            this.hashCode = i;
        }
    }

    public void clear() {
        Arrays.fill(this.buckets, (Object) null);
    }
}
