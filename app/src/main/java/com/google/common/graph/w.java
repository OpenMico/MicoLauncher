package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: MultiEdgesConnecting.java */
/* loaded from: classes2.dex */
abstract class w<E> extends AbstractSet<E> {
    private final Map<E, ?> a;
    private final Object b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public w(Map<E, ?> map, Object obj) {
        this.a = (Map) Preconditions.checkNotNull(map);
        this.b = Preconditions.checkNotNull(obj);
    }

    /* renamed from: a */
    public UnmodifiableIterator<E> iterator() {
        final Iterator<Map.Entry<E, ?>> it = this.a.entrySet().iterator();
        return new AbstractIterator<E>() { // from class: com.google.common.graph.w.1
            @Override // com.google.common.collect.AbstractIterator
            protected E computeNext() {
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    if (w.this.b.equals(entry.getValue())) {
                        return (E) entry.getKey();
                    }
                }
                return endOfData();
            }
        };
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(@NullableDecl Object obj) {
        return this.b.equals(this.a.get(obj));
    }
}
