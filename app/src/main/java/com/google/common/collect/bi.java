package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Collection;
import java.util.Map;

/* compiled from: LinkedHashMultimapGwtSerializationDependencies.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
abstract class bi<K, V> extends k<K, V> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public bi(Map<K, Collection<V>> map) {
        super(map);
    }
}
