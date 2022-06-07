package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Enum;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class EnumBiMap<K extends Enum<K>, V extends Enum<V>> extends a<K, V> {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    private transient Class<K> b;
    private transient Class<V> c;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.a
    /* bridge */ /* synthetic */ Object a(Object obj) {
        return a((EnumBiMap<K, V>) ((Enum) obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.a
    /* bridge */ /* synthetic */ Object b(Object obj) {
        return b((EnumBiMap<K, V>) ((Enum) obj));
    }

    @Override // com.google.common.collect.a, com.google.common.collect.ForwardingMap, java.util.Map
    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    @Override // com.google.common.collect.a, com.google.common.collect.ForwardingMap, java.util.Map
    public /* bridge */ /* synthetic */ boolean containsValue(@NullableDecl Object obj) {
        return super.containsValue(obj);
    }

    @Override // com.google.common.collect.a, com.google.common.collect.ForwardingMap, java.util.Map
    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    @Override // com.google.common.collect.a, com.google.common.collect.BiMap
    public /* bridge */ /* synthetic */ BiMap inverse() {
        return super.inverse();
    }

    @Override // com.google.common.collect.a, com.google.common.collect.ForwardingMap, java.util.Map
    public /* bridge */ /* synthetic */ Set keySet() {
        return super.keySet();
    }

    @Override // com.google.common.collect.a, com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
    public /* bridge */ /* synthetic */ void putAll(Map map) {
        super.putAll(map);
    }

    @Override // com.google.common.collect.a, com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
    public /* bridge */ /* synthetic */ Set values() {
        return super.values();
    }

    public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Class<K> cls, Class<V> cls2) {
        return new EnumBiMap<>(cls, cls2);
    }

    public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Map<K, V> map) {
        EnumBiMap<K, V> create = create(b((Map) map), c((Map) map));
        create.putAll(map);
        return create;
    }

    private EnumBiMap(Class<K> cls, Class<V> cls2) {
        super(ct.a(new EnumMap(cls)), ct.a(new EnumMap(cls2)));
        this.b = cls;
        this.c = cls2;
    }

    public static <K extends Enum<K>> Class<K> b(Map<K, ?> map) {
        if (map instanceof EnumBiMap) {
            return ((EnumBiMap) map).keyType();
        }
        if (map instanceof EnumHashBiMap) {
            return ((EnumHashBiMap) map).keyType();
        }
        Preconditions.checkArgument(!map.isEmpty());
        return map.keySet().iterator().next().getDeclaringClass();
    }

    private static <V extends Enum<V>> Class<V> c(Map<?, V> map) {
        if (map instanceof EnumBiMap) {
            return ((EnumBiMap) map).c;
        }
        Preconditions.checkArgument(!map.isEmpty());
        return map.values().iterator().next().getDeclaringClass();
    }

    public Class<K> keyType() {
        return this.b;
    }

    public Class<V> valueType() {
        return this.c;
    }

    K a(K k) {
        return (K) ((Enum) Preconditions.checkNotNull(k));
    }

    V b(V v) {
        return (V) ((Enum) Preconditions.checkNotNull(v));
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.b);
        objectOutputStream.writeObject(this.c);
        cc.a(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.b = (Class) objectInputStream.readObject();
        this.c = (Class) objectInputStream.readObject();
        a(ct.a(new EnumMap(this.b)), ct.a(new EnumMap(this.c)));
        cc.a(this, objectInputStream);
    }
}
