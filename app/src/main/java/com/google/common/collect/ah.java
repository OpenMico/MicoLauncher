package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: EmptyImmutableListMultimap.java */
@GwtCompatible(serializable = true)
/* loaded from: classes2.dex */
public class ah extends ImmutableListMultimap<Object, Object> {
    static final ah a = new ah();
    private static final long serialVersionUID = 0;

    private ah() {
        super(ImmutableMap.of(), 0);
    }

    private Object readResolve() {
        return a;
    }
}
