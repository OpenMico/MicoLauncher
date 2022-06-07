package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Enum;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class EnumHashBiMap<K extends Enum<K>, V> extends a<K, V> {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    private transient Class<K> b;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.a
    /* bridge */ /* synthetic */ Object a(Object obj) {
        return a((EnumHashBiMap<K, V>) ((Enum) obj));
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.a, com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ Object forcePut(Object obj, @NullableDecl Object obj2) {
        return forcePut((EnumHashBiMap<K, V>) ((Enum) obj), (Enum) obj2);
    }

    @Override // com.google.common.collect.a, com.google.common.collect.BiMap
    public /* bridge */ /* synthetic */ BiMap inverse() {
        return super.inverse();
    }

    @Override // com.google.common.collect.a, com.google.common.collect.ForwardingMap, java.util.Map
    public /* bridge */ /* synthetic */ Set keySet() {
        return super.keySet();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.a, com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ Object put(Object obj, @NullableDecl Object obj2) {
        return put((EnumHashBiMap<K, V>) ((Enum) obj), (Enum) obj2);
    }

    @Override // com.google.common.collect.a, com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
    public /* bridge */ /* synthetic */ void putAll(Map map) {
        super.putAll(map);
    }

    @Override // com.google.common.collect.a, com.google.common.collect.ForwardingMap, java.util.Map
    @CanIgnoreReturnValue
    public /* bridge */ /* synthetic */ Object remove(@NullableDecl Object obj) {
        return super.remove(obj);
    }

    @Override // com.google.common.collect.a, com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
    public /* bridge */ /* synthetic */ Set values() {
        return super.values();
    }

    public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Class<K> cls) {
        return new EnumHashBiMap<>(cls);
    }

    public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Map<K, ? extends V> map) {
        EnumHashBiMap<K, V> create = create(EnumBiMap.b((Map) map));
        create.putAll(map);
        return create;
    }

    private EnumHashBiMap(Class<K> cls) {
        super(ct.a(new EnumMap(cls)), Maps.newHashMapWithExpectedSize(cls.getEnumConstants().length));
        this.b = cls;
    }

    K a(K k) {
        return (K) ((Enum) Preconditions.checkNotNull(k));
    }

    @CanIgnoreReturnValue
    public V put(K k, @NullableDecl V v) {
        return (V) super.put((EnumHashBiMap<K, V>) k, (K) v);
    }

    @CanIgnoreReturnValue
    public V forcePut(K k, @NullableDecl V v) {
        return (V) super.forcePut((EnumHashBiMap<K, V>) k, (K) v);
    }

    public Class<K> keyType() {
        return this.b;
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.b);
        cc.a(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.b = (Class) objectInputStream.readObject();
        a(ct.a(new EnumMap(this.b)), new HashMap((this.b.getEnumConstants().length * 3) / 2));
        cc.a(this, objectInputStream);
    }
}
