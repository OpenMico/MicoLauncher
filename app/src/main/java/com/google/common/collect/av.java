package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/* compiled from: ImmutableAsList.java */
@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
abstract class av<E> extends ImmutableList<E> {
    abstract ImmutableCollection<E> b();

    @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
    public boolean contains(Object obj) {
        return b().contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return b().size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean isEmpty() {
        return b().isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public boolean a() {
        return b().a();
    }

    /* compiled from: ImmutableAsList.java */
    @GwtIncompatible
    /* loaded from: classes2.dex */
    static class a implements Serializable {
        private static final long serialVersionUID = 0;
        final ImmutableCollection<?> collection;

        a(ImmutableCollection<?> immutableCollection) {
            this.collection = immutableCollection;
        }

        Object readResolve() {
            return this.collection.asList();
        }
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection
    @GwtIncompatible
    Object writeReplace() {
        return new a(b());
    }
}
