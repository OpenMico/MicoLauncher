package io.realm;

import io.realm.internal.OsResults;
import io.realm.internal.UncheckedRow;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class OrderedRealmCollectionSnapshot<E> extends l<E> {
    private int f = -1;

    @Override // io.realm.internal.ManageableObject
    public boolean isFrozen() {
        return false;
    }

    @Override // io.realm.RealmCollection
    public boolean isLoaded() {
        return true;
    }

    @Override // io.realm.RealmCollection
    public boolean load() {
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.l, java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ void add(int i, Object obj) {
        super.add(i, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.l, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean add(Object obj) {
        return super.add(obj);
    }

    @Override // io.realm.l, java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean addAll(int i, Collection collection) {
        return super.addAll(i, collection);
    }

    @Override // io.realm.l, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean addAll(Collection collection) {
        return super.addAll(collection);
    }

    @Override // io.realm.l, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ double average(String str) {
        return super.average(str);
    }

    @Override // io.realm.l, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    @Override // io.realm.l, java.util.AbstractCollection, java.util.Collection, java.util.List, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ boolean contains(@Nullable Object obj) {
        return super.contains(obj);
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    @Nullable
    public /* bridge */ /* synthetic */ Object first() {
        return super.first();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.l, io.realm.OrderedRealmCollection
    @Nullable
    public /* bridge */ /* synthetic */ Object first(@Nullable Object obj) {
        return super.first(obj);
    }

    @Override // io.realm.l, java.util.AbstractList, java.util.List
    @Nullable
    public /* bridge */ /* synthetic */ Object get(int i) {
        return super.get(i);
    }

    @Override // io.realm.l
    public /* bridge */ /* synthetic */ Realm getRealm() {
        return super.getRealm();
    }

    @Override // io.realm.l, io.realm.RealmCollection, io.realm.internal.ManageableObject
    public /* bridge */ /* synthetic */ boolean isManaged() {
        return super.isManaged();
    }

    @Override // io.realm.l, io.realm.RealmCollection, io.realm.internal.ManageableObject
    public /* bridge */ /* synthetic */ boolean isValid() {
        return super.isValid();
    }

    @Override // io.realm.l, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public /* bridge */ /* synthetic */ Iterator iterator() {
        return super.iterator();
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    @Nullable
    public /* bridge */ /* synthetic */ Object last() {
        return super.last();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.l, io.realm.OrderedRealmCollection
    @Nullable
    public /* bridge */ /* synthetic */ Object last(@Nullable Object obj) {
        return super.last(obj);
    }

    @Override // io.realm.l, java.util.AbstractList, java.util.List
    public /* bridge */ /* synthetic */ ListIterator listIterator() {
        return super.listIterator();
    }

    @Override // io.realm.l, java.util.AbstractList, java.util.List
    public /* bridge */ /* synthetic */ ListIterator listIterator(int i) {
        return super.listIterator(i);
    }

    @Override // io.realm.l, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Number max(String str) {
        return super.max(str);
    }

    @Override // io.realm.l, io.realm.RealmCollection
    @Nullable
    public /* bridge */ /* synthetic */ Date maxDate(String str) {
        return super.maxDate(str);
    }

    @Override // io.realm.l, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Number min(String str) {
        return super.min(str);
    }

    @Override // io.realm.l, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Date minDate(String str) {
        return super.minDate(str);
    }

    @Override // io.realm.l, java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ Object remove(int i) {
        return super.remove(i);
    }

    @Override // io.realm.l, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // io.realm.l, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    @Override // io.realm.l, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.l, java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ Object set(int i, Object obj) {
        return super.set(i, obj);
    }

    @Override // io.realm.l, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Number sum(String str) {
        return super.sum(str);
    }

    public OrderedRealmCollectionSnapshot(BaseRealm baseRealm, OsResults osResults, Class<E> cls) {
        super(baseRealm, osResults.createSnapshot(), cls);
    }

    public OrderedRealmCollectionSnapshot(BaseRealm baseRealm, OsResults osResults, String str) {
        super(baseRealm, osResults.createSnapshot(), str);
    }

    @Override // io.realm.l, java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        if (this.f == -1) {
            this.f = super.size();
        }
        return this.f;
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str) {
        throw a("sort");
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort) {
        throw a("sort");
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort, String str2, Sort sort2) {
        throw a("sort");
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String[] strArr, Sort[] sortArr) {
        throw a("sort");
    }

    @Override // io.realm.RealmCollection
    @Deprecated
    public RealmQuery<E> where() {
        throw a("where");
    }

    private UnsupportedOperationException a(String str) {
        return new UnsupportedOperationException(String.format(Locale.US, "'%s()' is not supported by OrderedRealmCollectionSnapshot. Call '%s()' on the original 'RealmCollection' instead.", str, str));
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public OrderedRealmCollectionSnapshot<E> createSnapshot() {
        this.a.checkIfValid();
        return this;
    }

    @Override // io.realm.RealmCollection
    public OrderedRealmCollection<E> freeze() {
        throw a("freeze");
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public void deleteFromRealm(int i) {
        this.a.checkIfValidAndInTransaction();
        if (this.e.getUncheckedRow(i).isValid()) {
            this.e.delete(i);
        }
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public boolean deleteFirstFromRealm() {
        this.a.checkIfValidAndInTransaction();
        UncheckedRow firstUncheckedRow = this.e.firstUncheckedRow();
        return firstUncheckedRow != null && firstUncheckedRow.isValid() && this.e.deleteFirst();
    }

    @Override // io.realm.l, io.realm.OrderedRealmCollection
    public boolean deleteLastFromRealm() {
        this.a.checkIfValidAndInTransaction();
        UncheckedRow lastUncheckedRow = this.e.lastUncheckedRow();
        return lastUncheckedRow != null && lastUncheckedRow.isValid() && this.e.deleteLast();
    }

    @Override // io.realm.l, io.realm.RealmCollection
    public boolean deleteAllFromRealm() {
        return super.deleteAllFromRealm();
    }
}
