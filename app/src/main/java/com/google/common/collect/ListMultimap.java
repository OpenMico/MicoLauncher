package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public interface ListMultimap<K, V> extends Multimap<K, V> {
    @Override // 
    Map<K, Collection<V>> asMap();

    @Override // 
    boolean equals(@NullableDecl Object obj);

    @Override // 
    List<V> get(@NullableDecl K k);

    @Override // 
    @CanIgnoreReturnValue
    List<V> removeAll(@NullableDecl Object obj);

    @Override // 
    @CanIgnoreReturnValue
    List<V> replaceValues(K k, Iterable<? extends V> iterable);
}
