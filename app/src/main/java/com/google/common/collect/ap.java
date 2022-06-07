package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Predicate;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FilteredMultimap.java */
@GwtCompatible
/* loaded from: classes2.dex */
public interface ap<K, V> extends Multimap<K, V> {
    Multimap<K, V> a();

    Predicate<? super Map.Entry<K, V>> b();
}
