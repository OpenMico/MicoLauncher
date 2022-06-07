package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Collection;
import java.util.Map;

/* compiled from: HashMultimapGwtSerializationDependencies.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
abstract class at<K, V> extends k<K, V> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public at(Map<K, Collection<V>> map) {
        super(map);
    }
}
