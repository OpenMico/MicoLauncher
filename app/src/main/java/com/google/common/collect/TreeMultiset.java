package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.cc;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.xiaomi.infra.galaxy.fds.Common;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class TreeMultiset<E> extends m<E> implements Serializable {
    @GwtIncompatible
    private static final long serialVersionUID = 1;
    private final transient c<b<E>> a;
    private final transient as<E> b;
    private final transient b<E> c;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public enum a {
        SIZE {
            @Override // com.google.common.collect.TreeMultiset.a
            int a(b<?> bVar) {
                return ((b) bVar).b;
            }

            @Override // com.google.common.collect.TreeMultiset.a
            long b(@NullableDecl b<?> bVar) {
                if (bVar == null) {
                    return 0L;
                }
                return ((b) bVar).d;
            }
        },
        DISTINCT {
            @Override // com.google.common.collect.TreeMultiset.a
            int a(b<?> bVar) {
                return 1;
            }

            @Override // com.google.common.collect.TreeMultiset.a
            long b(@NullableDecl b<?> bVar) {
                if (bVar == null) {
                    return 0L;
                }
                return ((b) bVar).c;
            }
        };

        abstract int a(b<?> bVar);

        abstract long b(@NullableDecl b<?> bVar);
    }

    @Override // com.google.common.collect.m, com.google.common.collect.SortedMultiset, com.google.common.collect.cf
    public /* bridge */ /* synthetic */ Comparator comparator() {
        return super.comparator();
    }

    @Override // com.google.common.collect.h, java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public /* bridge */ /* synthetic */ boolean contains(@NullableDecl Object obj) {
        return super.contains(obj);
    }

    @Override // com.google.common.collect.m, com.google.common.collect.SortedMultiset
    public /* bridge */ /* synthetic */ SortedMultiset descendingMultiset() {
        return super.descendingMultiset();
    }

    @Override // com.google.common.collect.m, com.google.common.collect.h, com.google.common.collect.Multiset
    public /* bridge */ /* synthetic */ NavigableSet elementSet() {
        return super.elementSet();
    }

    @Override // com.google.common.collect.h, com.google.common.collect.Multiset
    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    @Override // com.google.common.collect.m, com.google.common.collect.SortedMultiset
    public /* bridge */ /* synthetic */ Multiset.Entry firstEntry() {
        return super.firstEntry();
    }

    @Override // com.google.common.collect.h, java.util.AbstractCollection, java.util.Collection
    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    @Override // com.google.common.collect.m, com.google.common.collect.SortedMultiset
    public /* bridge */ /* synthetic */ Multiset.Entry lastEntry() {
        return super.lastEntry();
    }

    @Override // com.google.common.collect.m, com.google.common.collect.SortedMultiset
    public /* bridge */ /* synthetic */ Multiset.Entry pollFirstEntry() {
        return super.pollFirstEntry();
    }

    @Override // com.google.common.collect.m, com.google.common.collect.SortedMultiset
    public /* bridge */ /* synthetic */ Multiset.Entry pollLastEntry() {
        return super.pollLastEntry();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.m, com.google.common.collect.SortedMultiset
    public /* bridge */ /* synthetic */ SortedMultiset subMultiset(@NullableDecl Object obj, BoundType boundType, @NullableDecl Object obj2, BoundType boundType2) {
        return super.subMultiset(obj, boundType, obj2, boundType2);
    }

    public static <E extends Comparable> TreeMultiset<E> create() {
        return new TreeMultiset<>(Ordering.natural());
    }

    public static <E> TreeMultiset<E> create(@NullableDecl Comparator<? super E> comparator) {
        return comparator == null ? new TreeMultiset<>(Ordering.natural()) : new TreeMultiset<>(comparator);
    }

    public static <E extends Comparable> TreeMultiset<E> create(Iterable<? extends E> iterable) {
        TreeMultiset<E> create = create();
        Iterables.addAll(create, iterable);
        return create;
    }

    TreeMultiset(c<b<E>> cVar, as<E> asVar, b<E> bVar) {
        super(asVar.a());
        this.a = cVar;
        this.b = asVar;
        this.c = bVar;
    }

    TreeMultiset(Comparator<? super E> comparator) {
        super(comparator);
        this.b = as.a((Comparator) comparator);
        this.c = new b<>(null, 1);
        b<E> bVar = this.c;
        b(bVar, bVar);
        this.a = new c<>();
    }

    private long a(a aVar) {
        b<E> a2 = this.a.a();
        long b2 = aVar.b(a2);
        if (this.b.b()) {
            b2 -= a(aVar, a2);
        }
        return this.b.c() ? b2 - b(aVar, a2) : b2;
    }

    private long a(a aVar, @NullableDecl b<E> bVar) {
        if (bVar == null) {
            return 0L;
        }
        int compare = comparator().compare(this.b.d(), ((b) bVar).a);
        if (compare < 0) {
            return a(aVar, ((b) bVar).f);
        }
        if (compare != 0) {
            return aVar.b(((b) bVar).f) + aVar.a(bVar) + a(aVar, ((b) bVar).g);
        }
        switch (this.b.e()) {
            case OPEN:
                return aVar.a(bVar) + aVar.b(((b) bVar).f);
            case CLOSED:
                return aVar.b(((b) bVar).f);
            default:
                throw new AssertionError();
        }
    }

    private long b(a aVar, @NullableDecl b<E> bVar) {
        if (bVar == null) {
            return 0L;
        }
        int compare = comparator().compare(this.b.f(), ((b) bVar).a);
        if (compare > 0) {
            return b(aVar, ((b) bVar).g);
        }
        if (compare != 0) {
            return aVar.b(((b) bVar).g) + aVar.a(bVar) + b(aVar, ((b) bVar).f);
        }
        switch (this.b.g()) {
            case OPEN:
                return aVar.a(bVar) + aVar.b(((b) bVar).g);
            case CLOSED:
                return aVar.b(((b) bVar).g);
            default:
                throw new AssertionError();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public int size() {
        return Ints.saturatedCast(a(a.SIZE));
    }

    @Override // com.google.common.collect.h
    int c() {
        return Ints.saturatedCast(a(a.DISTINCT));
    }

    static int a(@NullableDecl b<?> bVar) {
        if (bVar == null) {
            return 0;
        }
        return ((b) bVar).c;
    }

    @Override // com.google.common.collect.Multiset
    public int count(@NullableDecl Object obj) {
        try {
            b<E> a2 = this.a.a();
            if (this.b.c(obj) && a2 != null) {
                return a2.a((Comparator<? super Comparator<? super E>>) comparator(), (Comparator<? super E>) obj);
            }
            return 0;
        } catch (ClassCastException | NullPointerException unused) {
            return 0;
        }
    }

    @Override // com.google.common.collect.h, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int add(@NullableDecl E e, int i) {
        t.a(i, "occurrences");
        if (i == 0) {
            return count(e);
        }
        Preconditions.checkArgument(this.b.c(e));
        b<E> a2 = this.a.a();
        if (a2 == null) {
            comparator().compare(e, e);
            b<E> bVar = new b<>(e, i);
            b<E> bVar2 = this.c;
            b(bVar2, bVar, bVar2);
            this.a.a(a2, bVar);
            return 0;
        }
        int[] iArr = new int[1];
        this.a.a(a2, a2.a(comparator(), e, i, iArr));
        return iArr[0];
    }

    @Override // com.google.common.collect.h, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int remove(@NullableDecl Object obj, int i) {
        t.a(i, "occurrences");
        if (i == 0) {
            return count(obj);
        }
        b<E> a2 = this.a.a();
        int[] iArr = new int[1];
        try {
            if (this.b.c(obj) && a2 != null) {
                this.a.a(a2, a2.b(comparator(), obj, i, iArr));
                return iArr[0];
            }
            return 0;
        } catch (ClassCastException | NullPointerException unused) {
            return 0;
        }
    }

    @Override // com.google.common.collect.h, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int setCount(@NullableDecl E e, int i) {
        t.a(i, "count");
        boolean z = true;
        if (!this.b.c(e)) {
            if (i != 0) {
                z = false;
            }
            Preconditions.checkArgument(z);
            return 0;
        }
        b<E> a2 = this.a.a();
        if (a2 == null) {
            if (i > 0) {
                add(e, i);
            }
            return 0;
        }
        int[] iArr = new int[1];
        this.a.a(a2, a2.c(comparator(), e, i, iArr));
        return iArr[0];
    }

    @Override // com.google.common.collect.h, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public boolean setCount(@NullableDecl E e, int i, int i2) {
        t.a(i2, "newCount");
        t.a(i, "oldCount");
        Preconditions.checkArgument(this.b.c(e));
        b<E> a2 = this.a.a();
        if (a2 != null) {
            int[] iArr = new int[1];
            this.a.a(a2, a2.a(comparator(), e, i, i2, iArr));
            return iArr[0] == i;
        } else if (i != 0) {
            return false;
        } else {
            if (i2 > 0) {
                add(e, i2);
            }
            return true;
        }
    }

    @Override // com.google.common.collect.h, java.util.AbstractCollection, java.util.Collection
    public void clear() {
        if (this.b.b() || this.b.c()) {
            Iterators.b(b());
            return;
        }
        b<E> bVar = ((b) this.c).i;
        while (true) {
            b<E> bVar2 = this.c;
            if (bVar != bVar2) {
                b<E> bVar3 = ((b) bVar).i;
                ((b) bVar).b = 0;
                ((b) bVar).f = null;
                ((b) bVar).g = null;
                ((b) bVar).h = null;
                ((b) bVar).i = null;
                bVar = bVar3;
            } else {
                b(bVar2, bVar2);
                this.a.b();
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Multiset.Entry<E> b(final b<E> bVar) {
        return new Multisets.a<E>() { // from class: com.google.common.collect.TreeMultiset.1
            @Override // com.google.common.collect.Multiset.Entry
            public E getElement() {
                return (E) bVar.a();
            }

            @Override // com.google.common.collect.Multiset.Entry
            public int getCount() {
                int b2 = bVar.b();
                return b2 == 0 ? TreeMultiset.this.count(getElement()) : b2;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NullableDecl
    public b<E> i() {
        b<E> bVar;
        if (this.a.a() == null) {
            return null;
        }
        if (this.b.b()) {
            E d = this.b.d();
            bVar = this.a.a().b((Comparator<? super Comparator>) comparator(), (Comparator) d);
            if (bVar == null) {
                return null;
            }
            if (this.b.e() == BoundType.OPEN && comparator().compare(d, bVar.a()) == 0) {
                bVar = ((b) bVar).i;
            }
        } else {
            bVar = ((b) this.c).i;
        }
        if (bVar == this.c || !this.b.c(bVar.a())) {
            return null;
        }
        return bVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NullableDecl
    public b<E> j() {
        b<E> bVar;
        if (this.a.a() == null) {
            return null;
        }
        if (this.b.c()) {
            E f = this.b.f();
            bVar = this.a.a().c((Comparator<? super Comparator>) comparator(), (Comparator) f);
            if (bVar == null) {
                return null;
            }
            if (this.b.g() == BoundType.OPEN && comparator().compare(f, bVar.a()) == 0) {
                bVar = ((b) bVar).h;
            }
        } else {
            bVar = ((b) this.c).h;
        }
        if (bVar == this.c || !this.b.c(bVar.a())) {
            return null;
        }
        return bVar;
    }

    @Override // com.google.common.collect.h
    Iterator<E> a() {
        return Multisets.a(b());
    }

    @Override // com.google.common.collect.h
    Iterator<Multiset.Entry<E>> b() {
        return new Iterator<Multiset.Entry<E>>() { // from class: com.google.common.collect.TreeMultiset.2
            b<E> a;
            @NullableDecl
            Multiset.Entry<E> b;

            {
                this.a = TreeMultiset.this.i();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                if (this.a == null) {
                    return false;
                }
                if (!TreeMultiset.this.b.b(this.a.a())) {
                    return true;
                }
                this.a = null;
                return false;
            }

            /* renamed from: a */
            public Multiset.Entry<E> next() {
                if (hasNext()) {
                    Multiset.Entry<E> b2 = TreeMultiset.this.b(this.a);
                    this.b = b2;
                    if (((b) this.a).i == TreeMultiset.this.c) {
                        this.a = null;
                    } else {
                        this.a = ((b) this.a).i;
                    }
                    return b2;
                }
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public void remove() {
                t.a(this.b != null);
                TreeMultiset.this.setCount(this.b.getElement(), 0);
                this.b = null;
            }
        };
    }

    @Override // com.google.common.collect.m
    Iterator<Multiset.Entry<E>> f() {
        return new Iterator<Multiset.Entry<E>>() { // from class: com.google.common.collect.TreeMultiset.3
            b<E> a;
            Multiset.Entry<E> b = null;

            {
                this.a = TreeMultiset.this.j();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                if (this.a == null) {
                    return false;
                }
                if (!TreeMultiset.this.b.a((as) this.a.a())) {
                    return true;
                }
                this.a = null;
                return false;
            }

            /* renamed from: a */
            public Multiset.Entry<E> next() {
                if (hasNext()) {
                    Multiset.Entry<E> b2 = TreeMultiset.this.b(this.a);
                    this.b = b2;
                    if (((b) this.a).h == TreeMultiset.this.c) {
                        this.a = null;
                    } else {
                        this.a = ((b) this.a).h;
                    }
                    return b2;
                }
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public void remove() {
                t.a(this.b != null);
                TreeMultiset.this.setCount(this.b.getElement(), 0);
                this.b = null;
            }
        };
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.google.common.collect.Multiset
    public Iterator<E> iterator() {
        return Multisets.a((Multiset) this);
    }

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> headMultiset(@NullableDecl E e, BoundType boundType) {
        return new TreeMultiset(this.a, this.b.a(as.b(comparator(), e, boundType)), this.c);
    }

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> tailMultiset(@NullableDecl E e, BoundType boundType) {
        return new TreeMultiset(this.a, this.b.a(as.a(comparator(), e, boundType)), this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class c<T> {
        @NullableDecl
        private T a;

        private c() {
        }

        @NullableDecl
        public T a() {
            return this.a;
        }

        public void a(@NullableDecl T t, T t2) {
            if (this.a == t) {
                this.a = t2;
                return;
            }
            throw new ConcurrentModificationException();
        }

        void b() {
            this.a = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b<E> {
        @NullableDecl
        private final E a;
        private int b;
        private int c;
        private long d;
        private int e;
        @NullableDecl
        private b<E> f;
        @NullableDecl
        private b<E> g;
        @NullableDecl
        private b<E> h;
        @NullableDecl
        private b<E> i;

        b(@NullableDecl E e, int i) {
            Preconditions.checkArgument(i > 0);
            this.a = e;
            this.b = i;
            this.d = i;
            this.c = 1;
            this.e = 1;
            this.f = null;
            this.g = null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int a(Comparator<? super E> comparator, E e) {
            int compare = comparator.compare(e, (E) this.a);
            if (compare < 0) {
                b<E> bVar = this.f;
                if (bVar == null) {
                    return 0;
                }
                return bVar.a((Comparator<? super Comparator<? super E>>) comparator, (Comparator<? super E>) e);
            } else if (compare <= 0) {
                return this.b;
            } else {
                b<E> bVar2 = this.g;
                if (bVar2 == null) {
                    return 0;
                }
                return bVar2.a((Comparator<? super Comparator<? super E>>) comparator, (Comparator<? super E>) e);
            }
        }

        private b<E> a(E e, int i) {
            this.g = new b<>(e, i);
            TreeMultiset.b(this, this.g, this.i);
            this.e = Math.max(2, this.e);
            this.c++;
            this.d += i;
            return this;
        }

        private b<E> b(E e, int i) {
            this.f = new b<>(e, i);
            TreeMultiset.b(this.h, this.f, this);
            this.e = Math.max(2, this.e);
            this.c++;
            this.d += i;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        b<E> a(Comparator<? super E> comparator, @NullableDecl E e, int i, int[] iArr) {
            int compare = comparator.compare(e, (E) this.a);
            boolean z = true;
            if (compare < 0) {
                b<E> bVar = this.f;
                if (bVar == null) {
                    iArr[0] = 0;
                    return b((b<E>) e, i);
                }
                int i2 = bVar.e;
                this.f = bVar.a(comparator, e, i, iArr);
                if (iArr[0] == 0) {
                    this.c++;
                }
                this.d += i;
                return this.f.e == i2 ? this : g();
            } else if (compare > 0) {
                b<E> bVar2 = this.g;
                if (bVar2 == null) {
                    iArr[0] = 0;
                    return a((b<E>) e, i);
                }
                int i3 = bVar2.e;
                this.g = bVar2.a(comparator, e, i, iArr);
                if (iArr[0] == 0) {
                    this.c++;
                }
                this.d += i;
                return this.g.e == i3 ? this : g();
            } else {
                int i4 = this.b;
                iArr[0] = i4;
                long j = i;
                if (i4 + j > 2147483647L) {
                    z = false;
                }
                Preconditions.checkArgument(z);
                this.b += i;
                this.d += j;
                return this;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        b<E> b(Comparator<? super E> comparator, @NullableDecl E e, int i, int[] iArr) {
            int compare = comparator.compare(e, (E) this.a);
            if (compare < 0) {
                b<E> bVar = this.f;
                if (bVar == null) {
                    iArr[0] = 0;
                    return this;
                }
                this.f = bVar.b(comparator, e, i, iArr);
                if (iArr[0] > 0) {
                    if (i >= iArr[0]) {
                        this.c--;
                        this.d -= iArr[0];
                    } else {
                        this.d -= i;
                    }
                }
                return iArr[0] == 0 ? this : g();
            } else if (compare > 0) {
                b<E> bVar2 = this.g;
                if (bVar2 == null) {
                    iArr[0] = 0;
                    return this;
                }
                this.g = bVar2.b(comparator, e, i, iArr);
                if (iArr[0] > 0) {
                    if (i >= iArr[0]) {
                        this.c--;
                        this.d -= iArr[0];
                    } else {
                        this.d -= i;
                    }
                }
                return g();
            } else {
                int i2 = this.b;
                iArr[0] = i2;
                if (i >= i2) {
                    return c();
                }
                this.b = i2 - i;
                this.d -= i;
                return this;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        b<E> c(Comparator<? super E> comparator, @NullableDecl E e, int i, int[] iArr) {
            int compare = comparator.compare(e, (E) this.a);
            if (compare < 0) {
                b<E> bVar = this.f;
                if (bVar == null) {
                    iArr[0] = 0;
                    return i > 0 ? b((b<E>) e, i) : this;
                }
                this.f = bVar.c(comparator, e, i, iArr);
                if (i == 0 && iArr[0] != 0) {
                    this.c--;
                } else if (i > 0 && iArr[0] == 0) {
                    this.c++;
                }
                this.d += i - iArr[0];
                return g();
            } else if (compare > 0) {
                b<E> bVar2 = this.g;
                if (bVar2 == null) {
                    iArr[0] = 0;
                    return i > 0 ? a((b<E>) e, i) : this;
                }
                this.g = bVar2.c(comparator, e, i, iArr);
                if (i == 0 && iArr[0] != 0) {
                    this.c--;
                } else if (i > 0 && iArr[0] == 0) {
                    this.c++;
                }
                this.d += i - iArr[0];
                return g();
            } else {
                int i2 = this.b;
                iArr[0] = i2;
                if (i == 0) {
                    return c();
                }
                this.d += i - i2;
                this.b = i;
                return this;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        b<E> a(Comparator<? super E> comparator, @NullableDecl E e, int i, int i2, int[] iArr) {
            int compare = comparator.compare(e, (E) this.a);
            if (compare < 0) {
                b<E> bVar = this.f;
                if (bVar == null) {
                    iArr[0] = 0;
                    return (i != 0 || i2 <= 0) ? this : b((b<E>) e, i2);
                }
                this.f = bVar.a(comparator, e, i, i2, iArr);
                if (iArr[0] == i) {
                    if (i2 == 0 && iArr[0] != 0) {
                        this.c--;
                    } else if (i2 > 0 && iArr[0] == 0) {
                        this.c++;
                    }
                    this.d += i2 - iArr[0];
                }
                return g();
            } else if (compare > 0) {
                b<E> bVar2 = this.g;
                if (bVar2 == null) {
                    iArr[0] = 0;
                    return (i != 0 || i2 <= 0) ? this : a((b<E>) e, i2);
                }
                this.g = bVar2.a(comparator, e, i, i2, iArr);
                if (iArr[0] == i) {
                    if (i2 == 0 && iArr[0] != 0) {
                        this.c--;
                    } else if (i2 > 0 && iArr[0] == 0) {
                        this.c++;
                    }
                    this.d += i2 - iArr[0];
                }
                return g();
            } else {
                int i3 = this.b;
                iArr[0] = i3;
                if (i == i3) {
                    if (i2 == 0) {
                        return c();
                    }
                    this.d += i2 - i3;
                    this.b = i2;
                }
                return this;
            }
        }

        private b<E> c() {
            int i = this.b;
            this.b = 0;
            TreeMultiset.b(this.h, this.i);
            b<E> bVar = this.f;
            if (bVar == null) {
                return this.g;
            }
            b<E> bVar2 = this.g;
            if (bVar2 == null) {
                return bVar;
            }
            if (bVar.e >= bVar2.e) {
                b<E> bVar3 = this.h;
                bVar3.f = bVar.j(bVar3);
                bVar3.g = this.g;
                bVar3.c = this.c - 1;
                bVar3.d = this.d - i;
                return bVar3.g();
            }
            b<E> bVar4 = this.i;
            bVar4.g = bVar2.i(bVar4);
            bVar4.f = this.f;
            bVar4.c = this.c - 1;
            bVar4.d = this.d - i;
            return bVar4.g();
        }

        private b<E> i(b<E> bVar) {
            b<E> bVar2 = this.f;
            if (bVar2 == null) {
                return this.g;
            }
            this.f = bVar2.i(bVar);
            this.c--;
            this.d -= bVar.b;
            return g();
        }

        private b<E> j(b<E> bVar) {
            b<E> bVar2 = this.g;
            if (bVar2 == null) {
                return this.f;
            }
            this.g = bVar2.j(bVar);
            this.c--;
            this.d -= bVar.b;
            return g();
        }

        private void d() {
            this.c = TreeMultiset.a((b<?>) this.f) + 1 + TreeMultiset.a((b<?>) this.g);
            this.d = this.b + k(this.f) + k(this.g);
        }

        private void e() {
            this.e = Math.max(l(this.f), l(this.g)) + 1;
        }

        private void f() {
            d();
            e();
        }

        private b<E> g() {
            int h = h();
            if (h == -2) {
                if (this.g.h() > 0) {
                    this.g = this.g.j();
                }
                return i();
            } else if (h != 2) {
                e();
                return this;
            } else {
                if (this.f.h() < 0) {
                    this.f = this.f.i();
                }
                return j();
            }
        }

        private int h() {
            return l(this.f) - l(this.g);
        }

        private b<E> i() {
            Preconditions.checkState(this.g != null);
            b<E> bVar = this.g;
            this.g = bVar.f;
            bVar.f = this;
            bVar.d = this.d;
            bVar.c = this.c;
            f();
            bVar.e();
            return bVar;
        }

        private b<E> j() {
            Preconditions.checkState(this.f != null);
            b<E> bVar = this.f;
            this.f = bVar.g;
            bVar.g = this;
            bVar.d = this.d;
            bVar.c = this.c;
            f();
            bVar.e();
            return bVar;
        }

        private static long k(@NullableDecl b<?> bVar) {
            if (bVar == null) {
                return 0L;
            }
            return ((b) bVar).d;
        }

        private static int l(@NullableDecl b<?> bVar) {
            if (bVar == null) {
                return 0;
            }
            return ((b) bVar).e;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        @NullableDecl
        public b<E> b(Comparator<? super E> comparator, E e) {
            int compare = comparator.compare(e, (E) this.a);
            if (compare < 0) {
                b<E> bVar = this.f;
                return bVar == null ? this : (b) MoreObjects.firstNonNull(bVar.b((Comparator<? super Comparator<? super E>>) comparator, (Comparator<? super E>) e), this);
            } else if (compare == 0) {
                return this;
            } else {
                b<E> bVar2 = this.g;
                if (bVar2 == null) {
                    return null;
                }
                return bVar2.b((Comparator<? super Comparator<? super E>>) comparator, (Comparator<? super E>) e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        @NullableDecl
        public b<E> c(Comparator<? super E> comparator, E e) {
            int compare = comparator.compare(e, (E) this.a);
            if (compare > 0) {
                b<E> bVar = this.g;
                return bVar == null ? this : (b) MoreObjects.firstNonNull(bVar.c((Comparator<? super Comparator<? super E>>) comparator, (Comparator<? super E>) e), this);
            } else if (compare == 0) {
                return this;
            } else {
                b<E> bVar2 = this.f;
                if (bVar2 == null) {
                    return null;
                }
                return bVar2.c((Comparator<? super Comparator<? super E>>) comparator, (Comparator<? super E>) e);
            }
        }

        E a() {
            return this.a;
        }

        int b() {
            return this.b;
        }

        public String toString() {
            return Multisets.immutableEntry(a(), b()).toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> void b(b<T> bVar, b<T> bVar2) {
        ((b) bVar).i = bVar2;
        ((b) bVar2).h = bVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> void b(b<T> bVar, b<T> bVar2, b<T> bVar3) {
        b(bVar, bVar2);
        b(bVar2, bVar3);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(elementSet().comparator());
        cc.a(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Comparator comparator = (Comparator) objectInputStream.readObject();
        cc.a(m.class, "comparator").a((cc.a) this, (Object) comparator);
        cc.a(TreeMultiset.class, Common.RANGE).a((cc.a) this, (Object) as.a(comparator));
        cc.a(TreeMultiset.class, "rootReference").a((cc.a) this, (Object) new c());
        b bVar = new b(null, 1);
        cc.a(TreeMultiset.class, "header").a((cc.a) this, (Object) bVar);
        b(bVar, bVar);
        cc.a(this, objectInputStream);
    }
}
