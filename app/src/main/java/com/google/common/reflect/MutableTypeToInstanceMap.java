package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes2.dex */
public final class MutableTypeToInstanceMap<B> extends ForwardingMap<TypeToken<? extends B>, B> implements TypeToInstanceMap<B> {
    private final Map<TypeToken<? extends B>, B> a = Maps.newHashMap();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
    @CanIgnoreReturnValue
    @Deprecated
    public /* bridge */ /* synthetic */ Object put(Object obj, Object obj2) {
        return put((TypeToken<? extends TypeToken<? extends B>>) obj, (TypeToken<? extends B>) obj2);
    }

    @Override // com.google.common.reflect.TypeToInstanceMap
    @NullableDecl
    public <T extends B> T getInstance(Class<T> cls) {
        return (T) a(TypeToken.of((Class) cls));
    }

    @Override // com.google.common.reflect.TypeToInstanceMap
    @NullableDecl
    public <T extends B> T getInstance(TypeToken<T> typeToken) {
        return (T) a(typeToken.d());
    }

    @Override // com.google.common.reflect.TypeToInstanceMap
    @CanIgnoreReturnValue
    @NullableDecl
    public <T extends B> T putInstance(Class<T> cls, @NullableDecl T t) {
        return (T) a(TypeToken.of((Class) cls), t);
    }

    @Override // com.google.common.reflect.TypeToInstanceMap
    @CanIgnoreReturnValue
    @NullableDecl
    public <T extends B> T putInstance(TypeToken<T> typeToken, @NullableDecl T t) {
        return (T) a(typeToken.d(), t);
    }

    @CanIgnoreReturnValue
    @Deprecated
    public B put(TypeToken<? extends B> typeToken, B b) {
        throw new UnsupportedOperationException("Please use putInstance() instead.");
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
    @Deprecated
    public void putAll(Map<? extends TypeToken<? extends B>, ? extends B> map) {
        throw new UnsupportedOperationException("Please use putInstance() instead.");
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map
    public Set<Map.Entry<TypeToken<? extends B>, B>> entrySet() {
        return a.a(super.entrySet());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
    public Map<TypeToken<? extends B>, B> delegate() {
        return this.a;
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [T extends B, java.lang.Object] */
    @NullableDecl
    private <T extends B> T a(TypeToken<T> typeToken, @NullableDecl T t) {
        return this.a.put(typeToken, t);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [T extends B, java.lang.Object] */
    @NullableDecl
    private <T extends B> T a(TypeToken<T> typeToken) {
        return this.a.get(typeToken);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a<K, V> extends ForwardingMapEntry<K, V> {
        private final Map.Entry<K, V> a;

        static <K, V> Set<Map.Entry<K, V>> a(final Set<Map.Entry<K, V>> set) {
            return new ForwardingSet<Map.Entry<K, V>>() { // from class: com.google.common.reflect.MutableTypeToInstanceMap.a.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
                public Set<Map.Entry<K, V>> delegate() {
                    return set;
                }

                @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable
                public Iterator<Map.Entry<K, V>> iterator() {
                    return a.b(super.iterator());
                }

                @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
                public Object[] toArray() {
                    return standardToArray();
                }

                @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
                public <T> T[] toArray(T[] tArr) {
                    return (T[]) standardToArray(tArr);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <K, V> Iterator<Map.Entry<K, V>> b(Iterator<Map.Entry<K, V>> it) {
            return Iterators.transform(it, new Function<Map.Entry<K, V>, Map.Entry<K, V>>() { // from class: com.google.common.reflect.MutableTypeToInstanceMap.a.2
                /* renamed from: a */
                public Map.Entry<K, V> apply(Map.Entry<K, V> entry) {
                    return new a(entry);
                }
            });
        }

        private a(Map.Entry<K, V> entry) {
            this.a = (Map.Entry) Preconditions.checkNotNull(entry);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
        public Map.Entry<K, V> delegate() {
            return this.a;
        }

        @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
        public V setValue(V v) {
            throw new UnsupportedOperationException();
        }
    }
}
