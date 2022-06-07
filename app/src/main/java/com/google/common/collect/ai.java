package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: EmptyImmutableSetMultimap.java */
@GwtCompatible(serializable = true)
/* loaded from: classes2.dex */
public class ai extends ImmutableSetMultimap<Object, Object> {
    static final ai a = new ai();
    private static final long serialVersionUID = 0;

    private ai() {
        super(ImmutableMap.of(), 0, null);
    }

    private Object readResolve() {
        return a;
    }
}
