package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.j2objc.annotations.Weak;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: FilteredMultimapValues.java */
@GwtCompatible
/* loaded from: classes2.dex */
final class aq<K, V> extends AbstractCollection<V> {
    @Weak
    private final ap<K, V> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aq(ap<K, V> apVar) {
        this.a = (ap) Preconditions.checkNotNull(apVar);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<V> iterator() {
        return Maps.b(this.a.entries().iterator());
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean contains(@NullableDecl Object obj) {
        return this.a.containsValue(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        return this.a.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean remove(@NullableDecl Object obj) {
        Predicate<? super Map.Entry<K, V>> b = this.a.b();
        Iterator<Map.Entry<K, V>> it = this.a.a().entries().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (b.apply(next) && Objects.equal(next.getValue(), obj)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        return Iterables.removeIf(this.a.a().entries(), Predicates.and(this.a.b(), Maps.b(Predicates.in(collection))));
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        return Iterables.removeIf(this.a.a().entries(), Predicates.and(this.a.b(), Maps.b(Predicates.not(Predicates.in(collection)))));
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public void clear() {
        this.a.clear();
    }
}
