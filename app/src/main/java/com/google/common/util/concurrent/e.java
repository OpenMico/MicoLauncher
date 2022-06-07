package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.c;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: CollectionFuture.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
abstract class e<V, C> extends c<V, C> {
    e() {
    }

    /* compiled from: CollectionFuture.java */
    /* loaded from: classes2.dex */
    abstract class a extends c<V, C>.a {
        private List<Optional<V>> c;

        abstract C a(List<Optional<V>> list);

        a(ImmutableCollection<? extends ListenableFuture<? extends V>> immutableCollection, boolean z) {
            super(immutableCollection, z, true);
            List<Optional<V>> list;
            if (immutableCollection.isEmpty()) {
                list = ImmutableList.of();
            } else {
                list = Lists.newArrayListWithCapacity(immutableCollection.size());
            }
            this.c = list;
            for (int i = 0; i < immutableCollection.size(); i++) {
                this.c.add(null);
            }
        }

        @Override // com.google.common.util.concurrent.c.a
        final void a(boolean z, int i, @NullableDecl V v) {
            List<Optional<V>> list = this.c;
            if (list != null) {
                list.set(i, Optional.fromNullable(v));
            } else {
                Preconditions.checkState(z || e.this.isCancelled(), "Future was done before all dependencies completed");
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.util.concurrent.c.a
        final void b() {
            List<Optional<V>> list = this.c;
            if (list != null) {
                e.this.set(a(list));
            } else {
                Preconditions.checkState(e.this.isDone());
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.util.concurrent.c.a
        public void a() {
            super.a();
            this.c = null;
        }
    }

    /* compiled from: CollectionFuture.java */
    /* loaded from: classes2.dex */
    static final class b<V> extends e<V, List<V>> {
        /* JADX INFO: Access modifiers changed from: package-private */
        public b(ImmutableCollection<? extends ListenableFuture<? extends V>> immutableCollection, boolean z) {
            a((c.a) new a(immutableCollection, z));
        }

        /* compiled from: CollectionFuture.java */
        /* loaded from: classes2.dex */
        private final class a extends e<V, List<V>>.a {
            a(ImmutableCollection<? extends ListenableFuture<? extends V>> immutableCollection, boolean z) {
                super(immutableCollection, z);
            }

            /* renamed from: b */
            public List<V> a(List<Optional<V>> list) {
                ArrayList newArrayListWithCapacity = Lists.newArrayListWithCapacity(list.size());
                Iterator<Optional<V>> it = list.iterator();
                while (it.hasNext()) {
                    Optional<V> next = it.next();
                    newArrayListWithCapacity.add(next != null ? next.orNull() : null);
                }
                return Collections.unmodifiableList(newArrayListWithCapacity);
            }
        }
    }
}
