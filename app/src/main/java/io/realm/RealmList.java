package io.realm;

import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.realm.internal.InvalidRow;
import io.realm.internal.OsList;
import io.realm.internal.OsResults;
import io.realm.internal.RealmObjectProxy;
import io.realm.rx.CollectionChange;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class RealmList<E> extends AbstractList<E> implements OrderedRealmCollection<E> {
    private final i<E> a;
    private List<E> b;
    @Nullable
    protected String className;
    @Nullable
    protected Class<E> clazz;
    protected final BaseRealm realm;

    @Override // io.realm.RealmCollection
    public boolean isLoaded() {
        return true;
    }

    @Override // io.realm.RealmCollection
    public boolean load() {
        return true;
    }

    public RealmList() {
        this.realm = null;
        this.a = null;
        this.b = new ArrayList();
    }

    public RealmList(E... eArr) {
        if (eArr != null) {
            this.realm = null;
            this.a = null;
            this.b = new ArrayList(eArr.length);
            Collections.addAll(this.b, eArr);
            return;
        }
        throw new IllegalArgumentException("The objects argument cannot be null");
    }

    public RealmList(Class<E> cls, OsList osList, BaseRealm baseRealm) {
        this.clazz = cls;
        this.a = a(baseRealm, osList, cls, null);
        this.realm = baseRealm;
    }

    public RealmList(String str, OsList osList, BaseRealm baseRealm) {
        this.realm = baseRealm;
        this.className = str;
        this.a = a(baseRealm, osList, null, str);
    }

    public OsList a() {
        return this.a.b();
    }

    @Override // io.realm.RealmCollection, io.realm.internal.ManageableObject
    public boolean isValid() {
        BaseRealm baseRealm = this.realm;
        if (baseRealm == null) {
            return true;
        }
        if (baseRealm.isClosed()) {
            return false;
        }
        return b();
    }

    @Override // io.realm.RealmCollection
    public RealmList<E> freeze() {
        if (!isManaged()) {
            throw new UnsupportedOperationException("This method is only available in managed mode.");
        } else if (isValid()) {
            BaseRealm freeze = this.realm.freeze();
            OsList freeze2 = a().freeze(freeze.sharedRealm);
            String str = this.className;
            if (str != null) {
                return new RealmList<>(str, freeze2, freeze);
            }
            return new RealmList<>(this.clazz, freeze2, freeze);
        } else {
            throw new IllegalStateException("Only valid, managed RealmLists can be frozen.");
        }
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isFrozen() {
        BaseRealm baseRealm = this.realm;
        return baseRealm != null && baseRealm.isFrozen();
    }

    @Override // io.realm.RealmCollection, io.realm.internal.ManageableObject
    public boolean isManaged() {
        return this.realm != null;
    }

    private boolean b() {
        i<E> iVar = this.a;
        return iVar != null && iVar.c();
    }

    @Override // java.util.AbstractList, java.util.List
    public void add(int i, @Nullable E e) {
        if (isManaged()) {
            c();
            this.a.c(i, e);
        } else {
            this.b.add(i, e);
        }
        this.modCount++;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(@Nullable E e) {
        if (isManaged()) {
            c();
            this.a.c(e);
        } else {
            this.b.add(e);
        }
        this.modCount++;
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public E set(int i, @Nullable E e) {
        if (!isManaged()) {
            return this.b.set(i, e);
        }
        c();
        return this.a.d(i, e);
    }

    public void move(int i, int i2) {
        if (isManaged()) {
            c();
            this.a.a(i, i2);
            return;
        }
        int size = this.b.size();
        if (i < 0 || size <= i) {
            throw new IndexOutOfBoundsException("Invalid index " + i + ", size is " + size);
        } else if (i2 < 0 || size <= i2) {
            throw new IndexOutOfBoundsException("Invalid index " + i2 + ", size is " + size);
        } else {
            this.b.add(i2, this.b.remove(i));
        }
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        if (isManaged()) {
            c();
            this.a.f();
        } else {
            this.b.clear();
        }
        this.modCount++;
    }

    @Override // java.util.AbstractList, java.util.List
    public E remove(int i) {
        E e;
        if (isManaged()) {
            c();
            e = get(i);
            this.a.e(i);
        } else {
            e = this.b.remove(i);
        }
        this.modCount++;
        return e;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(@Nullable Object obj) {
        if (!isManaged() || this.realm.isInTransaction()) {
            return super.remove(obj);
        }
        throw new IllegalStateException("Objects can only be removed from inside a write transaction.");
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean removeAll(Collection<?> collection) {
        if (!isManaged() || this.realm.isInTransaction()) {
            return super.removeAll(collection);
        }
        throw new IllegalStateException("Objects can only be removed from inside a write transaction.");
    }

    @Override // io.realm.OrderedRealmCollection
    public boolean deleteFirstFromRealm() {
        if (!isManaged()) {
            throw new UnsupportedOperationException("This method is only available in managed mode.");
        } else if (this.a.e()) {
            return false;
        } else {
            deleteFromRealm(0);
            this.modCount++;
            return true;
        }
    }

    @Override // io.realm.OrderedRealmCollection
    public boolean deleteLastFromRealm() {
        if (!isManaged()) {
            throw new UnsupportedOperationException("This method is only available in managed mode.");
        } else if (this.a.e()) {
            return false;
        } else {
            this.a.g();
            this.modCount++;
            return true;
        }
    }

    @Override // java.util.AbstractList, java.util.List
    @Nullable
    public E get(int i) {
        if (!isManaged()) {
            return this.b.get(i);
        }
        c();
        return this.a.b(i);
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E first() {
        return a(true, (boolean) null);
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E first(@Nullable E e) {
        return a(false, (boolean) e);
    }

    @Nullable
    private E a(boolean z, @Nullable E e) {
        if (isManaged()) {
            c();
            if (!this.a.e()) {
                return get(0);
            }
        } else {
            List<E> list = this.b;
            if (list != null && !list.isEmpty()) {
                return this.b.get(0);
            }
        }
        if (!z) {
            return e;
        }
        throw new IndexOutOfBoundsException("The list is empty.");
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E last() {
        return b(true, null);
    }

    @Override // io.realm.OrderedRealmCollection
    @Nullable
    public E last(@Nullable E e) {
        return b(false, e);
    }

    @Nullable
    private E b(boolean z, @Nullable E e) {
        if (isManaged()) {
            c();
            if (!this.a.e()) {
                return get(this.a.d() - 1);
            }
        } else {
            List<E> list = this.b;
            if (list != null && !list.isEmpty()) {
                List<E> list2 = this.b;
                return list2.get(list2.size() - 1);
            }
        }
        if (!z) {
            return e;
        }
        throw new IndexOutOfBoundsException("The list is empty.");
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str) {
        return sort(str, Sort.ASCENDING);
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort) {
        if (isManaged()) {
            return where().sort(str, sort).findAll();
        }
        throw new UnsupportedOperationException("This method is only available in managed mode.");
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort, String str2, Sort sort2) {
        return sort(new String[]{str, str2}, new Sort[]{sort, sort2});
    }

    @Override // io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String[] strArr, Sort[] sortArr) {
        if (isManaged()) {
            return where().sort(strArr, sortArr).findAll();
        }
        throw new UnsupportedOperationException("This method is only available in managed mode.");
    }

    @Override // io.realm.OrderedRealmCollection
    public void deleteFromRealm(int i) {
        if (isManaged()) {
            c();
            this.a.f(i);
            this.modCount++;
            return;
        }
        throw new UnsupportedOperationException("This method is only available in managed mode.");
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        if (!isManaged()) {
            return this.b.size();
        }
        c();
        return this.a.d();
    }

    @Override // io.realm.RealmCollection
    public RealmQuery<E> where() {
        if (isManaged()) {
            c();
            if (this.a.a()) {
                return RealmQuery.a(this);
            }
            throw new UnsupportedOperationException("This feature is available only when the element type is implementing RealmModel.");
        }
        throw new UnsupportedOperationException("This method is only available in managed mode.");
    }

    @Override // io.realm.RealmCollection
    @Nullable
    public Number min(String str) {
        return where().min(str);
    }

    @Override // io.realm.RealmCollection
    @Nullable
    public Number max(String str) {
        return where().max(str);
    }

    @Override // io.realm.RealmCollection
    public Number sum(String str) {
        return where().sum(str);
    }

    @Override // io.realm.RealmCollection
    public double average(String str) {
        return where().average(str);
    }

    @Override // io.realm.RealmCollection
    @Nullable
    public Date maxDate(String str) {
        return where().maximumDate(str);
    }

    @Override // io.realm.RealmCollection
    @Nullable
    public Date minDate(String str) {
        return where().minimumDate(str);
    }

    @Override // io.realm.RealmCollection
    public boolean deleteAllFromRealm() {
        if (isManaged()) {
            c();
            if (this.a.e()) {
                return false;
            }
            this.a.h();
            this.modCount++;
            return true;
        }
        throw new UnsupportedOperationException("This method is only available in managed mode.");
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, io.realm.RealmCollection
    public boolean contains(@Nullable Object obj) {
        if (!isManaged()) {
            return this.b.contains(obj);
        }
        this.realm.checkIfValid();
        if (!(obj instanceof RealmObjectProxy) || ((RealmObjectProxy) obj).realmGet$proxyState().getRow$realm() != InvalidRow.INSTANCE) {
            return super.contains(obj);
        }
        return false;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    @Nonnull
    public Iterator<E> iterator() {
        if (isManaged()) {
            return new a();
        }
        return super.iterator();
    }

    @Override // java.util.AbstractList, java.util.List
    @Nonnull
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractList, java.util.List
    @Nonnull
    public ListIterator<E> listIterator(int i) {
        if (isManaged()) {
            return new b(i);
        }
        return super.listIterator(i);
    }

    public void c() {
        this.realm.checkIfValid();
    }

    @Override // io.realm.OrderedRealmCollection
    public OrderedRealmCollectionSnapshot<E> createSnapshot() {
        if (isManaged()) {
            c();
            if (!this.a.a()) {
                throw new UnsupportedOperationException("This feature is available only when the element type is implementing RealmModel.");
            } else if (this.className != null) {
                BaseRealm baseRealm = this.realm;
                return new OrderedRealmCollectionSnapshot<>(baseRealm, OsResults.createFromQuery(baseRealm.sharedRealm, this.a.b().getQuery()), this.className);
            } else {
                BaseRealm baseRealm2 = this.realm;
                return new OrderedRealmCollectionSnapshot<>(baseRealm2, OsResults.createFromQuery(baseRealm2.sharedRealm, this.a.b().getQuery()), this.clazz);
            }
        } else {
            throw new UnsupportedOperationException("This method is only available in managed mode.");
        }
    }

    public Realm getRealm() {
        BaseRealm baseRealm = this.realm;
        if (baseRealm == null) {
            return null;
        }
        baseRealm.checkIfValid();
        BaseRealm baseRealm2 = this.realm;
        if (baseRealm2 instanceof Realm) {
            return (Realm) baseRealm2;
        }
        throw new IllegalStateException("This method is only available for typed Realms");
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (!isManaged()) {
            sb.append("RealmList<?>@[");
            int size = size();
            while (i < size) {
                E e = get(i);
                if (e instanceof RealmModel) {
                    sb.append(System.identityHashCode(e));
                } else if (e instanceof byte[]) {
                    sb.append("byte[");
                    sb.append(((byte[]) e).length);
                    sb.append("]");
                } else {
                    sb.append(e);
                }
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                i++;
            }
            if (size() > 0) {
                sb.setLength(sb.length() - 1);
            }
            sb.append("]");
        } else {
            sb.append("RealmList<");
            String str = this.className;
            if (str != null) {
                sb.append(str);
            } else if (a((Class<?>) this.clazz)) {
                sb.append(this.realm.getSchema().b((Class<? extends RealmModel>) this.clazz).getClassName());
            } else {
                Class<E> cls = this.clazz;
                if (cls == byte[].class) {
                    sb.append(cls.getSimpleName());
                } else {
                    sb.append(cls.getName());
                }
            }
            sb.append(">@[");
            if (!b()) {
                sb.append("invalid");
            } else if (a((Class<?>) this.clazz)) {
                while (i < size()) {
                    sb.append(((RealmObjectProxy) get(i)).realmGet$proxyState().getRow$realm().getObjectKey());
                    sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                    i++;
                }
                if (size() > 0) {
                    sb.setLength(sb.length() - 1);
                }
            } else {
                while (i < size()) {
                    E e2 = get(i);
                    if (e2 instanceof byte[]) {
                        sb.append("byte[");
                        sb.append(((byte[]) e2).length);
                        sb.append("]");
                    } else {
                        sb.append(e2);
                    }
                    sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                    i++;
                }
                if (size() > 0) {
                    sb.setLength(sb.length() - 1);
                }
            }
            sb.append("]");
        }
        return sb.toString();
    }

    public Flowable<RealmList<E>> asFlowable() {
        BaseRealm baseRealm = this.realm;
        if (baseRealm instanceof Realm) {
            return baseRealm.configuration.getRxFactory().from((Realm) this.realm, this);
        }
        if (baseRealm instanceof DynamicRealm) {
            return baseRealm.configuration.getRxFactory().from((DynamicRealm) this.realm, this);
        }
        throw new UnsupportedOperationException(this.realm.getClass() + " does not support RxJava2.");
    }

    public Observable<CollectionChange<RealmList<E>>> asChangesetObservable() {
        BaseRealm baseRealm = this.realm;
        if (baseRealm instanceof Realm) {
            return baseRealm.configuration.getRxFactory().changesetsFrom((Realm) this.realm, this);
        }
        if (baseRealm instanceof DynamicRealm) {
            return baseRealm.configuration.getRxFactory().changesetsFrom((DynamicRealm) baseRealm, this);
        }
        throw new UnsupportedOperationException(this.realm.getClass() + " does not support RxJava2.");
    }

    private void a(@Nullable Object obj, boolean z) {
        if (!z || obj != null) {
            this.realm.checkIfValid();
            this.realm.sharedRealm.capabilities.checkCanDeliverNotification("Listeners cannot be used on current thread.");
            return;
        }
        throw new IllegalArgumentException("Listener should not be null");
    }

    public void addChangeListener(OrderedRealmCollectionChangeListener<RealmList<E>> orderedRealmCollectionChangeListener) {
        a((Object) orderedRealmCollectionChangeListener, true);
        this.a.b().addListener((OsList) this, (OrderedRealmCollectionChangeListener<OsList>) orderedRealmCollectionChangeListener);
    }

    public void removeChangeListener(OrderedRealmCollectionChangeListener<RealmList<E>> orderedRealmCollectionChangeListener) {
        a((Object) orderedRealmCollectionChangeListener, true);
        this.a.b().removeListener((OsList) this, (OrderedRealmCollectionChangeListener<OsList>) orderedRealmCollectionChangeListener);
    }

    public void addChangeListener(RealmChangeListener<RealmList<E>> realmChangeListener) {
        a((Object) realmChangeListener, true);
        this.a.b().addListener((OsList) this, (RealmChangeListener<OsList>) realmChangeListener);
    }

    public void removeChangeListener(RealmChangeListener<RealmList<E>> realmChangeListener) {
        a((Object) realmChangeListener, true);
        this.a.b().removeListener((OsList) this, (RealmChangeListener<OsList>) realmChangeListener);
    }

    public void removeAllChangeListeners() {
        a((Object) null, false);
        this.a.b().removeAllListeners();
    }

    /* loaded from: classes5.dex */
    public class a implements Iterator<E> {
        int a;
        int b;
        int c;

        private a() {
            RealmList.this = r1;
            this.a = 0;
            this.b = -1;
            this.c = RealmList.this.modCount;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            RealmList.this.c();
            a();
            return this.a != RealmList.this.size();
        }

        @Override // java.util.Iterator
        @Nullable
        public E next() {
            RealmList.this.c();
            a();
            int i = this.a;
            try {
                E e = (E) RealmList.this.get(i);
                this.b = i;
                this.a = i + 1;
                return e;
            } catch (IndexOutOfBoundsException unused) {
                a();
                throw new NoSuchElementException("Cannot access index " + i + " when size is " + RealmList.this.size() + ". Remember to check hasNext() before using next().");
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            RealmList.this.c();
            if (this.b >= 0) {
                a();
                try {
                    RealmList.this.remove(this.b);
                    if (this.b < this.a) {
                        this.a--;
                    }
                    this.b = -1;
                    this.c = RealmList.this.modCount;
                } catch (IndexOutOfBoundsException unused) {
                    throw new ConcurrentModificationException();
                }
            } else {
                throw new IllegalStateException("Cannot call remove() twice. Must call next() in between.");
            }
        }

        final void a() {
            if (RealmList.this.modCount != this.c) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /* loaded from: classes5.dex */
    public class b extends RealmList<E>.a implements ListIterator<E> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b(int i) {
            super();
            RealmList.this = r4;
            if (i < 0 || i > r4.size()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Starting location must be a valid index: [0, ");
                sb.append(r4.size() - 1);
                sb.append("]. Index was ");
                sb.append(i);
                throw new IndexOutOfBoundsException(sb.toString());
            }
            this.a = i;
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.a != 0;
        }

        @Override // java.util.ListIterator
        @Nullable
        public E previous() {
            a();
            int i = this.a - 1;
            try {
                E e = (E) RealmList.this.get(i);
                this.a = i;
                this.b = i;
                return e;
            } catch (IndexOutOfBoundsException unused) {
                a();
                throw new NoSuchElementException("Cannot access index less than zero. This was " + i + ". Remember to check hasPrevious() before using previous().");
            }
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.a;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.a - 1;
        }

        @Override // java.util.ListIterator
        public void set(@Nullable E e) {
            RealmList.this.realm.checkIfValid();
            if (this.b >= 0) {
                a();
                try {
                    RealmList.this.set(this.b, e);
                    this.c = RealmList.this.modCount;
                } catch (IndexOutOfBoundsException unused) {
                    throw new ConcurrentModificationException();
                }
            } else {
                throw new IllegalStateException();
            }
        }

        @Override // java.util.ListIterator
        public void add(@Nullable E e) {
            RealmList.this.realm.checkIfValid();
            a();
            try {
                int i = this.a;
                RealmList.this.add(i, e);
                this.b = -1;
                this.a = i + 1;
                this.c = RealmList.this.modCount;
            } catch (IndexOutOfBoundsException unused) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private static boolean a(Class<?> cls) {
        return RealmModel.class.isAssignableFrom(cls);
    }

    private i<E> a(BaseRealm baseRealm, OsList osList, @Nullable Class<E> cls, @Nullable String str) {
        if (cls == null || a((Class<?>) cls)) {
            return new n(baseRealm, osList, cls, str);
        }
        if (cls == String.class) {
            return new p(baseRealm, osList, cls);
        }
        if (cls == Long.class || cls == Integer.class || cls == Short.class || cls == Byte.class) {
            return new h(baseRealm, osList, cls);
        }
        if (cls == Boolean.class) {
            return new b(baseRealm, osList, cls);
        }
        if (cls == byte[].class) {
            return new a(baseRealm, osList, cls);
        }
        if (cls == Double.class) {
            return new d(baseRealm, osList, cls);
        }
        if (cls == Float.class) {
            return new e(baseRealm, osList, cls);
        }
        if (cls == Date.class) {
            return new c(baseRealm, osList, cls);
        }
        throw new IllegalArgumentException("Unexpected value class: " + cls.getName());
    }
}
