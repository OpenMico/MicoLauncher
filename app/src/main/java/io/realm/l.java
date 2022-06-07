package io.realm;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.realm.internal.InvalidRow;
import io.realm.internal.OsResults;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import io.realm.internal.core.QueryDescriptor;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: OrderedRealmCollectionImpl.java */
/* loaded from: classes5.dex */
public abstract class l<E> extends AbstractList<E> implements OrderedRealmCollection<E> {
    final BaseRealm a;
    @Nullable
    final Class<E> b;
    @Nullable
    final String c;
    @SuppressFBWarnings({"SS_SHOULD_BE_STATIC"})
    final boolean d;
    final OsResults e;

    public boolean isManaged() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public l(BaseRealm baseRealm, OsResults osResults, Class<E> cls) {
        this(baseRealm, osResults, cls, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public l(BaseRealm baseRealm, OsResults osResults, String str) {
        this(baseRealm, osResults, null, str);
    }

    private l(BaseRealm baseRealm, OsResults osResults, @Nullable Class<E> cls, @Nullable String str) {
        this.d = false;
        this.a = baseRealm;
        this.e = osResults;
        this.b = cls;
        this.c = str;
    }

    Table a() {
        return this.e.getTable();
    }

    OsResults b() {
        return this.e;
    }

    public boolean isValid() {
        return this.e.isValid();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, io.realm.RealmCollection
    public boolean contains(@Nullable Object obj) {
        if (!isLoaded() || ((obj instanceof RealmObjectProxy) && ((RealmObjectProxy) obj).realmGet$proxyState().getRow$realm() == InvalidRow.INSTANCE)) {
            return false;
        }
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (it.next().equals(obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractList, java.util.List
    @Nullable
    public E get(int i) {
        this.a.checkIfValid();
        return (E) this.a.a((Class<RealmModel>) this.b, this.c, this.e.getUncheckedRow(i));
    }

    @Nullable
    public E first() {
        return a(true, null);
    }

    @Nullable
    public E first(@Nullable E e) {
        return a(false, e);
    }

    @Nullable
    private E a(boolean z, @Nullable E e) {
        UncheckedRow firstUncheckedRow = this.e.firstUncheckedRow();
        if (firstUncheckedRow != null) {
            return (E) this.a.a((Class<RealmModel>) this.b, this.c, firstUncheckedRow);
        }
        if (!z) {
            return e;
        }
        throw new IndexOutOfBoundsException("No results were found.");
    }

    @Nullable
    public E last() {
        return b(true, null);
    }

    @Nullable
    public E last(@Nullable E e) {
        return b(false, e);
    }

    @Nullable
    private E b(boolean z, @Nullable E e) {
        UncheckedRow lastUncheckedRow = this.e.lastUncheckedRow();
        if (lastUncheckedRow != null) {
            return (E) this.a.a((Class<RealmModel>) this.b, this.c, lastUncheckedRow);
        }
        if (!z) {
            return e;
        }
        throw new IndexOutOfBoundsException("No results were found.");
    }

    public void deleteFromRealm(int i) {
        this.a.checkIfValidAndInTransaction();
        this.e.delete(i);
    }

    public boolean deleteAllFromRealm() {
        this.a.checkIfValid();
        if (size() <= 0) {
            return false;
        }
        this.e.clear();
        return true;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<E> iterator() {
        return new a();
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<E> listIterator() {
        return new b(0);
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<E> listIterator(int i) {
        return new b(i);
    }

    private long a(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Non-empty field name required.");
        } else if (!str.contains(".")) {
            long columnKey = this.e.getTable().getColumnKey(str);
            if (columnKey >= 0) {
                return columnKey;
            }
            throw new IllegalArgumentException(String.format(Locale.US, "Field '%s' does not exist.", str));
        } else {
            throw new IllegalArgumentException("Aggregates on child object fields are not supported: " + str);
        }
    }

    public RealmResults<E> sort(String str) {
        return a(this.e.sort(QueryDescriptor.getInstanceForSort(c(), this.e.getTable(), str, Sort.ASCENDING)));
    }

    public RealmResults<E> sort(String str, Sort sort) {
        return a(this.e.sort(QueryDescriptor.getInstanceForSort(c(), this.e.getTable(), str, sort)));
    }

    public RealmResults<E> sort(String[] strArr, Sort[] sortArr) {
        return a(this.e.sort(QueryDescriptor.getInstanceForSort(c(), this.e.getTable(), strArr, sortArr)));
    }

    public RealmResults<E> sort(String str, Sort sort, String str2, Sort sort2) {
        return sort(new String[]{str, str2}, new Sort[]{sort, sort2});
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        if (!isLoaded()) {
            return 0;
        }
        long size = this.e.size();
        if (size > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) size;
    }

    public Number min(String str) {
        this.a.checkIfValid();
        return this.e.aggregateNumber(OsResults.Aggregate.MINIMUM, a(str));
    }

    public Date minDate(String str) {
        this.a.checkIfValid();
        return this.e.aggregateDate(OsResults.Aggregate.MINIMUM, a(str));
    }

    public Number max(String str) {
        this.a.checkIfValid();
        return this.e.aggregateNumber(OsResults.Aggregate.MAXIMUM, a(str));
    }

    @Nullable
    public Date maxDate(String str) {
        this.a.checkIfValid();
        return this.e.aggregateDate(OsResults.Aggregate.MAXIMUM, a(str));
    }

    public Number sum(String str) {
        this.a.checkIfValid();
        return this.e.aggregateNumber(OsResults.Aggregate.SUM, a(str));
    }

    public double average(String str) {
        this.a.checkIfValid();
        return this.e.aggregateNumber(OsResults.Aggregate.AVERAGE, a(str)).doubleValue();
    }

    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public E remove(int i) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public E set(int i, E e) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    public boolean deleteLastFromRealm() {
        this.a.checkIfValidAndInTransaction();
        return this.e.deleteLast();
    }

    public boolean deleteFirstFromRealm() {
        this.a.checkIfValidAndInTransaction();
        return this.e.deleteFirst();
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean add(E e) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public void add(int i, E e) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Override // java.util.AbstractList, java.util.List
    @Deprecated
    public boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: OrderedRealmCollectionImpl.java */
    /* loaded from: classes5.dex */
    public class a extends OsResults.Iterator<E> {
        a() {
            super(l.this.e);
        }

        @Override // io.realm.internal.OsResults.Iterator
        protected E convertRowToObject(UncheckedRow uncheckedRow) {
            return (E) l.this.a.a((Class<RealmModel>) l.this.b, l.this.c, uncheckedRow);
        }
    }

    public OrderedRealmCollectionSnapshot<E> createSnapshot() {
        String str = this.c;
        if (str != null) {
            return new OrderedRealmCollectionSnapshot<>(this.a, this.e, str);
        }
        return new OrderedRealmCollectionSnapshot<>(this.a, this.e, this.b);
    }

    public Realm getRealm() {
        this.a.checkIfValid();
        BaseRealm baseRealm = this.a;
        if (baseRealm instanceof Realm) {
            return (Realm) baseRealm;
        }
        throw new IllegalStateException("This method is only available for typed Realms");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: OrderedRealmCollectionImpl.java */
    /* loaded from: classes5.dex */
    public class b extends OsResults.ListIterator<E> {
        b(int i) {
            super(l.this.e, i);
        }

        @Override // io.realm.internal.OsResults.Iterator
        protected E convertRowToObject(UncheckedRow uncheckedRow) {
            return (E) l.this.a.a((Class<RealmModel>) l.this.b, l.this.c, uncheckedRow);
        }
    }

    RealmResults<E> a(OsResults osResults) {
        RealmResults<E> realmResults;
        String str = this.c;
        if (str != null) {
            realmResults = new RealmResults<>(this.a, osResults, str);
        } else {
            realmResults = new RealmResults<>(this.a, osResults, this.b);
        }
        realmResults.load();
        return realmResults;
    }

    private o c() {
        return new o(this.a.getSchema());
    }
}
