package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public abstract class FluentIterable<E> implements Iterable<E> {
    private final Optional<Iterable<E>> a;

    public FluentIterable() {
        this.a = Optional.absent();
    }

    FluentIterable(Iterable<E> iterable) {
        Preconditions.checkNotNull(iterable);
        this.a = Optional.fromNullable(this == iterable ? null : iterable);
    }

    private Iterable<E> a() {
        return this.a.or((Optional<Iterable<E>>) this);
    }

    public static <E> FluentIterable<E> from(final Iterable<E> iterable) {
        return iterable instanceof FluentIterable ? (FluentIterable) iterable : new FluentIterable<E>(iterable) { // from class: com.google.common.collect.FluentIterable.1
            @Override // java.lang.Iterable
            public Iterator<E> iterator() {
                return iterable.iterator();
            }
        };
    }

    @Beta
    public static <E> FluentIterable<E> from(E[] eArr) {
        return from(Arrays.asList(eArr));
    }

    @Deprecated
    public static <E> FluentIterable<E> from(FluentIterable<E> fluentIterable) {
        return (FluentIterable) Preconditions.checkNotNull(fluentIterable);
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2) {
        return a(iterable, iterable2);
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2, Iterable<? extends T> iterable3) {
        return a(iterable, iterable2, iterable3);
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2, Iterable<? extends T> iterable3, Iterable<? extends T> iterable4) {
        return a(iterable, iterable2, iterable3, iterable4);
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends T>... iterableArr) {
        return a((Iterable[]) Arrays.copyOf(iterableArr, iterableArr.length));
    }

    @Beta
    public static <T> FluentIterable<T> concat(final Iterable<? extends Iterable<? extends T>> iterable) {
        Preconditions.checkNotNull(iterable);
        return new FluentIterable<T>() { // from class: com.google.common.collect.FluentIterable.2
            @Override // java.lang.Iterable
            public Iterator<T> iterator() {
                return Iterators.concat(Iterators.transform(iterable.iterator(), Iterables.a()));
            }
        };
    }

    private static <T> FluentIterable<T> a(final Iterable<? extends T>... iterableArr) {
        for (Iterable<? extends T> iterable : iterableArr) {
            Preconditions.checkNotNull(iterable);
        }
        return new FluentIterable<T>() { // from class: com.google.common.collect.FluentIterable.3
            @Override // java.lang.Iterable
            public Iterator<T> iterator() {
                return Iterators.concat(new b<Iterator<? extends T>>(iterableArr.length) { // from class: com.google.common.collect.FluentIterable.3.1
                    /* renamed from: b */
                    public Iterator<? extends T> a(int i) {
                        return iterableArr[i].iterator();
                    }
                });
            }
        };
    }

    @Beta
    public static <E> FluentIterable<E> of() {
        return from(ImmutableList.of());
    }

    @Beta
    public static <E> FluentIterable<E> of(@NullableDecl E e, E... eArr) {
        return from(Lists.asList(e, eArr));
    }

    public String toString() {
        return Iterables.toString(a());
    }

    public final int size() {
        return Iterables.size(a());
    }

    public final boolean contains(@NullableDecl Object obj) {
        return Iterables.contains(a(), obj);
    }

    public final FluentIterable<E> cycle() {
        return from(Iterables.cycle(a()));
    }

    @Beta
    public final FluentIterable<E> append(Iterable<? extends E> iterable) {
        return concat(a(), iterable);
    }

    @Beta
    public final FluentIterable<E> append(E... eArr) {
        return concat(a(), Arrays.asList(eArr));
    }

    public final FluentIterable<E> filter(Predicate<? super E> predicate) {
        return from(Iterables.filter(a(), predicate));
    }

    @GwtIncompatible
    public final <T> FluentIterable<T> filter(Class<T> cls) {
        return from(Iterables.filter((Iterable<?>) a(), (Class) cls));
    }

    public final boolean anyMatch(Predicate<? super E> predicate) {
        return Iterables.any(a(), predicate);
    }

    public final boolean allMatch(Predicate<? super E> predicate) {
        return Iterables.all(a(), predicate);
    }

    public final Optional<E> firstMatch(Predicate<? super E> predicate) {
        return Iterables.tryFind(a(), predicate);
    }

    public final <T> FluentIterable<T> transform(Function<? super E, T> function) {
        return from(Iterables.transform(a(), function));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> FluentIterable<T> transformAndConcat(Function<? super E, ? extends Iterable<? extends T>> function) {
        return concat(transform(function));
    }

    public final Optional<E> first() {
        Iterator<E> it = a().iterator();
        return it.hasNext() ? Optional.of(it.next()) : Optional.absent();
    }

    public final Optional<E> last() {
        E next;
        Iterable<E> a = a();
        if (a instanceof List) {
            List list = (List) a;
            if (list.isEmpty()) {
                return Optional.absent();
            }
            return Optional.of(list.get(list.size() - 1));
        }
        Iterator<E> it = a.iterator();
        if (!it.hasNext()) {
            return Optional.absent();
        }
        if (a instanceof SortedSet) {
            return Optional.of(((SortedSet) a).last());
        }
        do {
            next = it.next();
        } while (it.hasNext());
        return Optional.of(next);
    }

    public final FluentIterable<E> skip(int i) {
        return from(Iterables.skip(a(), i));
    }

    public final FluentIterable<E> limit(int i) {
        return from(Iterables.limit(a(), i));
    }

    public final boolean isEmpty() {
        return !a().iterator().hasNext();
    }

    public final ImmutableList<E> toList() {
        return ImmutableList.copyOf(a());
    }

    public final ImmutableList<E> toSortedList(Comparator<? super E> comparator) {
        return Ordering.from(comparator).immutableSortedCopy(a());
    }

    public final ImmutableSet<E> toSet() {
        return ImmutableSet.copyOf(a());
    }

    public final ImmutableSortedSet<E> toSortedSet(Comparator<? super E> comparator) {
        return ImmutableSortedSet.copyOf(comparator, a());
    }

    public final ImmutableMultiset<E> toMultiset() {
        return ImmutableMultiset.copyOf(a());
    }

    public final <V> ImmutableMap<E, V> toMap(Function<? super E, V> function) {
        return Maps.toMap(a(), function);
    }

    public final <K> ImmutableListMultimap<K, E> index(Function<? super E, K> function) {
        return Multimaps.index(a(), function);
    }

    public final <K> ImmutableMap<K, E> uniqueIndex(Function<? super E, K> function) {
        return Maps.uniqueIndex(a(), function);
    }

    @GwtIncompatible
    public final E[] toArray(Class<E> cls) {
        return (E[]) Iterables.toArray(a(), cls);
    }

    @CanIgnoreReturnValue
    public final <C extends Collection<? super E>> C copyInto(C c) {
        Preconditions.checkNotNull(c);
        Iterable<E> a = a();
        if (a instanceof Collection) {
            c.addAll(Collections2.a(a));
        } else {
            for (E e : a) {
                c.add(e);
            }
        }
        return c;
    }

    @Beta
    public final String join(Joiner joiner) {
        return joiner.join(this);
    }

    public final E get(int i) {
        return (E) Iterables.get(a(), i);
    }
}
