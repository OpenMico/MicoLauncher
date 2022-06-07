package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.concurrent.CopyOnWriteArrayList;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class Lists {
    private Lists() {
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    @CanIgnoreReturnValue
    @SafeVarargs
    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList(E... eArr) {
        Preconditions.checkNotNull(eArr);
        ArrayList<E> arrayList = new ArrayList<>(a(eArr.length));
        Collections.addAll(arrayList, eArr);
        return arrayList;
    }

    @CanIgnoreReturnValue
    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof Collection) {
            return new ArrayList<>(Collections2.a(iterable));
        }
        return newArrayList(iterable.iterator());
    }

    @CanIgnoreReturnValue
    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> it) {
        ArrayList<E> newArrayList = newArrayList();
        Iterators.addAll(newArrayList, it);
        return newArrayList;
    }

    @VisibleForTesting
    static int a(int i2) {
        t.a(i2, "arraySize");
        return Ints.saturatedCast(i2 + 5 + (i2 / 10));
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayListWithCapacity(int i2) {
        t.a(i2, "initialArraySize");
        return new ArrayList<>(i2);
    }

    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayListWithExpectedSize(int i2) {
        return new ArrayList<>(a(i2));
    }

    @GwtCompatible(serializable = true)
    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList<>();
    }

    @GwtCompatible(serializable = true)
    public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> iterable) {
        LinkedList<E> newLinkedList = newLinkedList();
        Iterables.addAll(newLinkedList, iterable);
        return newLinkedList;
    }

    @GwtIncompatible
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList<>();
    }

    @GwtIncompatible
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(Iterable<? extends E> iterable) {
        return new CopyOnWriteArrayList<>(iterable instanceof Collection ? Collections2.a(iterable) : newArrayList(iterable));
    }

    public static <E> List<E> asList(@NullableDecl E e2, E[] eArr) {
        return new c(e2, eArr);
    }

    public static <E> List<E> asList(@NullableDecl E e2, @NullableDecl E e3, E[] eArr) {
        return new l(e2, e3, eArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class c<E> extends AbstractList<E> implements Serializable, RandomAccess {
        private static final long serialVersionUID = 0;
        @NullableDecl
        final E first;
        final E[] rest;

        c(@NullableDecl E e, E[] eArr) {
            this.first = e;
            this.rest = (E[]) ((Object[]) Preconditions.checkNotNull(eArr));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return IntMath.saturatedAdd(this.rest.length, 1);
        }

        @Override // java.util.AbstractList, java.util.List
        public E get(int i) {
            Preconditions.checkElementIndex(i, size());
            return i == 0 ? this.first : this.rest[i - 1];
        }
    }

    /* loaded from: classes2.dex */
    private static class l<E> extends AbstractList<E> implements Serializable, RandomAccess {
        private static final long serialVersionUID = 0;
        @NullableDecl
        final E first;
        final E[] rest;
        @NullableDecl
        final E second;

        l(@NullableDecl E e, @NullableDecl E e2, E[] eArr) {
            this.first = e;
            this.second = e2;
            this.rest = (E[]) ((Object[]) Preconditions.checkNotNull(eArr));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return IntMath.saturatedAdd(this.rest.length, 2);
        }

        @Override // java.util.AbstractList, java.util.List
        public E get(int i) {
            switch (i) {
                case 0:
                    return this.first;
                case 1:
                    return this.second;
                default:
                    Preconditions.checkElementIndex(i, size());
                    return this.rest[i - 2];
            }
        }
    }

    public static <B> List<List<B>> cartesianProduct(List<? extends List<? extends B>> list) {
        return s.a(list);
    }

    @SafeVarargs
    public static <B> List<List<B>> cartesianProduct(List<? extends B>... listArr) {
        return cartesianProduct(Arrays.asList(listArr));
    }

    public static <F, T> List<T> transform(List<F> list, Function<? super F, ? extends T> function) {
        return list instanceof RandomAccess ? new j(list, function) : new k(list, function);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class k<F, T> extends AbstractSequentialList<T> implements Serializable {
        private static final long serialVersionUID = 0;
        final List<F> fromList;
        final Function<? super F, ? extends T> function;

        k(List<F> list, Function<? super F, ? extends T> function) {
            this.fromList = (List) Preconditions.checkNotNull(list);
            this.function = (Function) Preconditions.checkNotNull(function);
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public void clear() {
            this.fromList.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.fromList.size();
        }

        @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
        public ListIterator<T> listIterator(int i) {
            return new cq<F, T>(this.fromList.listIterator(i)) { // from class: com.google.common.collect.Lists.k.1
                @Override // com.google.common.collect.cp
                T a(F f) {
                    return (T) k.this.function.apply(f);
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class j<F, T> extends AbstractList<T> implements Serializable, RandomAccess {
        private static final long serialVersionUID = 0;
        final List<F> fromList;
        final Function<? super F, ? extends T> function;

        j(List<F> list, Function<? super F, ? extends T> function) {
            this.fromList = (List) Preconditions.checkNotNull(list);
            this.function = (Function) Preconditions.checkNotNull(function);
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public void clear() {
            this.fromList.clear();
        }

        @Override // java.util.AbstractList, java.util.List
        public T get(int i) {
            return (T) this.function.apply((F) this.fromList.get(i));
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
        public Iterator<T> iterator() {
            return listIterator();
        }

        @Override // java.util.AbstractList, java.util.List
        public ListIterator<T> listIterator(int i) {
            return new cq<F, T>(this.fromList.listIterator(i)) { // from class: com.google.common.collect.Lists.j.1
                @Override // com.google.common.collect.cp
                T a(F f) {
                    return (T) j.this.function.apply(f);
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean isEmpty() {
            return this.fromList.isEmpty();
        }

        @Override // java.util.AbstractList, java.util.List
        public T remove(int i) {
            return (T) this.function.apply((F) this.fromList.remove(i));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.fromList.size();
        }
    }

    public static <T> List<List<T>> partition(List<T> list, int i2) {
        Preconditions.checkNotNull(list);
        Preconditions.checkArgument(i2 > 0);
        return list instanceof RandomAccess ? new f(list, i2) : new d(list, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class d<T> extends AbstractList<List<T>> {
        final List<T> a;
        final int b;

        d(List<T> list, int i) {
            this.a = list;
            this.b = i;
        }

        /* renamed from: a */
        public List<T> get(int i) {
            Preconditions.checkElementIndex(i, size());
            int i2 = this.b;
            int i3 = i * i2;
            return this.a.subList(i3, Math.min(i2 + i3, this.a.size()));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return IntMath.divide(this.a.size(), this.b, RoundingMode.CEILING);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean isEmpty() {
            return this.a.isEmpty();
        }
    }

    /* loaded from: classes2.dex */
    private static class f<T> extends d<T> implements RandomAccess {
        f(List<T> list, int i) {
            super(list, i);
        }
    }

    public static ImmutableList<Character> charactersOf(String str) {
        return new i((String) Preconditions.checkNotNull(str));
    }

    @Beta
    public static List<Character> charactersOf(CharSequence charSequence) {
        return new b((CharSequence) Preconditions.checkNotNull(charSequence));
    }

    /* loaded from: classes2.dex */
    public static final class i extends ImmutableList<Character> {
        private final String string;

        @Override // com.google.common.collect.ImmutableCollection
        public boolean a() {
            return false;
        }

        i(String str) {
            this.string = str;
        }

        @Override // com.google.common.collect.ImmutableList, java.util.List
        public int indexOf(@NullableDecl Object obj) {
            if (obj instanceof Character) {
                return this.string.indexOf(((Character) obj).charValue());
            }
            return -1;
        }

        @Override // com.google.common.collect.ImmutableList, java.util.List
        public int lastIndexOf(@NullableDecl Object obj) {
            if (obj instanceof Character) {
                return this.string.lastIndexOf(((Character) obj).charValue());
            }
            return -1;
        }

        @Override // com.google.common.collect.ImmutableList, java.util.List
        public ImmutableList<Character> subList(int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2, size());
            return Lists.charactersOf(this.string.substring(i, i2));
        }

        /* renamed from: a */
        public Character get(int i) {
            Preconditions.checkElementIndex(i, size());
            return Character.valueOf(this.string.charAt(i));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.string.length();
        }
    }

    /* loaded from: classes2.dex */
    private static final class b extends AbstractList<Character> {
        private final CharSequence a;

        b(CharSequence charSequence) {
            this.a = charSequence;
        }

        /* renamed from: a */
        public Character get(int i) {
            Preconditions.checkElementIndex(i, size());
            return Character.valueOf(this.a.charAt(i));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.a.length();
        }
    }

    public static <T> List<T> reverse(List<T> list) {
        if (list instanceof ImmutableList) {
            return ((ImmutableList) list).reverse();
        }
        if (list instanceof h) {
            return ((h) list).a();
        }
        if (list instanceof RandomAccess) {
            return new g(list);
        }
        return new h(list);
    }

    /* loaded from: classes2.dex */
    public static class h<T> extends AbstractList<T> {
        private final List<T> a;

        h(List<T> list) {
            this.a = (List) Preconditions.checkNotNull(list);
        }

        List<T> a() {
            return this.a;
        }

        private int a(int i) {
            int size = size();
            Preconditions.checkElementIndex(i, size);
            return (size - 1) - i;
        }

        public int b(int i) {
            int size = size();
            Preconditions.checkPositionIndex(i, size);
            return size - i;
        }

        @Override // java.util.AbstractList, java.util.List
        public void add(int i, @NullableDecl T t) {
            this.a.add(b(i), t);
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public void clear() {
            this.a.clear();
        }

        @Override // java.util.AbstractList, java.util.List
        public T remove(int i) {
            return this.a.remove(a(i));
        }

        @Override // java.util.AbstractList
        protected void removeRange(int i, int i2) {
            subList(i, i2).clear();
        }

        @Override // java.util.AbstractList, java.util.List
        public T set(int i, @NullableDecl T t) {
            return this.a.set(a(i), t);
        }

        @Override // java.util.AbstractList, java.util.List
        public T get(int i) {
            return this.a.get(a(i));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.a.size();
        }

        @Override // java.util.AbstractList, java.util.List
        public List<T> subList(int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2, size());
            return Lists.reverse(this.a.subList(b(i2), b(i)));
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
        public Iterator<T> iterator() {
            return listIterator();
        }

        @Override // java.util.AbstractList, java.util.List
        public ListIterator<T> listIterator(int i) {
            final ListIterator<T> listIterator = this.a.listIterator(b(i));
            return new ListIterator<T>() { // from class: com.google.common.collect.Lists.h.1
                boolean a;

                @Override // java.util.ListIterator
                public void add(T t) {
                    listIterator.add(t);
                    listIterator.previous();
                    this.a = false;
                }

                @Override // java.util.ListIterator, java.util.Iterator
                public boolean hasNext() {
                    return listIterator.hasPrevious();
                }

                @Override // java.util.ListIterator
                public boolean hasPrevious() {
                    return listIterator.hasNext();
                }

                @Override // java.util.ListIterator, java.util.Iterator
                public T next() {
                    if (hasNext()) {
                        this.a = true;
                        return (T) listIterator.previous();
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.ListIterator
                public int nextIndex() {
                    return h.this.b(listIterator.nextIndex());
                }

                @Override // java.util.ListIterator
                public T previous() {
                    if (hasPrevious()) {
                        this.a = true;
                        return (T) listIterator.next();
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.ListIterator
                public int previousIndex() {
                    return nextIndex() - 1;
                }

                @Override // java.util.ListIterator, java.util.Iterator
                public void remove() {
                    t.a(this.a);
                    listIterator.remove();
                    this.a = false;
                }

                @Override // java.util.ListIterator
                public void set(T t) {
                    Preconditions.checkState(this.a);
                    listIterator.set(t);
                }
            };
        }
    }

    /* loaded from: classes2.dex */
    public static class g<T> extends h<T> implements RandomAccess {
        g(List<T> list) {
            super(list);
        }
    }

    public static int a(List<?> list) {
        Iterator<?> it = list.iterator();
        int i2 = 1;
        while (it.hasNext()) {
            Object next = it.next();
            i2 = ~(~((i2 * 31) + (next == null ? 0 : next.hashCode())));
        }
        return i2;
    }

    public static boolean a(List<?> list, @NullableDecl Object obj) {
        if (obj == Preconditions.checkNotNull(list)) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        List list2 = (List) obj;
        int size = list.size();
        if (size != list2.size()) {
            return false;
        }
        if (!(list instanceof RandomAccess) || !(list2 instanceof RandomAccess)) {
            return Iterators.elementsEqual(list.iterator(), list2.iterator());
        }
        for (int i2 = 0; i2 < size; i2++) {
            if (!Objects.equal(list.get(i2), list2.get(i2))) {
                return false;
            }
        }
        return true;
    }

    public static <E> boolean a(List<E> list, int i2, Iterable<? extends E> iterable) {
        ListIterator<E> listIterator = list.listIterator(i2);
        boolean z = false;
        for (E e2 : iterable) {
            listIterator.add(e2);
            z = true;
        }
        return z;
    }

    public static int b(List<?> list, @NullableDecl Object obj) {
        if (list instanceof RandomAccess) {
            return d(list, obj);
        }
        ListIterator<?> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            if (Objects.equal(obj, listIterator.next())) {
                return listIterator.previousIndex();
            }
        }
        return -1;
    }

    private static int d(List<?> list, @NullableDecl Object obj) {
        int size = list.size();
        int i2 = 0;
        if (obj == null) {
            while (i2 < size) {
                if (list.get(i2) == null) {
                    return i2;
                }
                i2++;
            }
            return -1;
        }
        while (i2 < size) {
            if (obj.equals(list.get(i2))) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public static int c(List<?> list, @NullableDecl Object obj) {
        if (list instanceof RandomAccess) {
            return e(list, obj);
        }
        ListIterator<?> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            if (Objects.equal(obj, listIterator.previous())) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }

    private static int e(List<?> list, @NullableDecl Object obj) {
        if (obj == null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                if (list.get(size) == null) {
                    return size;
                }
            }
            return -1;
        }
        for (int size2 = list.size() - 1; size2 >= 0; size2--) {
            if (obj.equals(list.get(size2))) {
                return size2;
            }
        }
        return -1;
    }

    public static <E> ListIterator<E> a(List<E> list, int i2) {
        return new a(list).listIterator(i2);
    }

    public static <E> List<E> a(List<E> list, int i2, int i3) {
        List list2;
        if (list instanceof RandomAccess) {
            list2 = new e<E>(list) { // from class: com.google.common.collect.Lists.1
                @Override // java.util.AbstractList, java.util.List
                public ListIterator<E> listIterator(int i4) {
                    return this.a.listIterator(i4);
                }
            };
        } else {
            list2 = new a<E>(list) { // from class: com.google.common.collect.Lists.2
                @Override // java.util.AbstractList, java.util.List
                public ListIterator<E> listIterator(int i4) {
                    return this.a.listIterator(i4);
                }
            };
        }
        return list2.subList(i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class a<E> extends AbstractList<E> {
        final List<E> a;

        a(List<E> list) {
            this.a = (List) Preconditions.checkNotNull(list);
        }

        @Override // java.util.AbstractList, java.util.List
        public void add(int i, E e) {
            this.a.add(i, e);
        }

        @Override // java.util.AbstractList, java.util.List
        public boolean addAll(int i, Collection<? extends E> collection) {
            return this.a.addAll(i, collection);
        }

        @Override // java.util.AbstractList, java.util.List
        public E get(int i) {
            return this.a.get(i);
        }

        @Override // java.util.AbstractList, java.util.List
        public E remove(int i) {
            return this.a.remove(i);
        }

        @Override // java.util.AbstractList, java.util.List
        public E set(int i, E e) {
            return this.a.set(i, e);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(Object obj) {
            return this.a.contains(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.a.size();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class e<E> extends a<E> implements RandomAccess {
        e(List<E> list) {
            super(list);
        }
    }

    public static <T> List<T> a(Iterable<T> iterable) {
        return (List) iterable;
    }
}
