package java8.util.stream;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java8.util.Objects;
import java8.util.Spliterator;
import java8.util.concurrent.ForkJoinPool;
import java8.util.function.IntFunction;
import java8.util.stream.Sink;
import java8.util.stream.gd;
import java8.util.stream.gr;

/* compiled from: DistinctOps.java */
/* loaded from: classes5.dex */
final class da {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: DistinctOps.java */
    /* renamed from: java8.util.stream.da$1  reason: invalid class name */
    /* loaded from: classes5.dex */
    public static class AnonymousClass1 extends gd.b<T, T> {
        AnonymousClass1(a aVar, gq gqVar, int i) {
            super(aVar, gqVar, i);
        }

        <P_IN> Node<T> b(gb<T> gbVar, Spliterator<P_IN> spliterator) {
            return fn.a((Collection) gc.a(db.a(), dc.a(), dd.a()).b(gbVar, spliterator));
        }

        @Override // java8.util.stream.gd.b, java8.util.stream.a
        <P_IN> Node<T> a(gb<T> gbVar, Spliterator<P_IN> spliterator, IntFunction<T[]> intFunction) {
            a aVar;
            if (gp.DISTINCT.a(gbVar.c())) {
                return gbVar.a(spliterator, false, intFunction);
            }
            if (gp.ORDERED.a(gbVar.c())) {
                return b(gbVar, spliterator);
            }
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(512, 0.75f, ForkJoinPool.getCommonPoolParallelism() + 1);
            ee.a(de.a(atomicBoolean, concurrentHashMap), false).b(gbVar, spliterator);
            Set keySet = concurrentHashMap.keySet();
            if (atomicBoolean.get()) {
                int size = keySet.size();
                if (size >= 127) {
                    aVar = new a(keySet, size);
                } else {
                    HashSet hashSet = new HashSet(Math.max(((int) ((size + 1) / 0.75f)) + 1, 16));
                    hashSet.addAll(keySet);
                    hashSet.add(null);
                    aVar = hashSet;
                }
            } else {
                aVar = keySet;
            }
            return fn.a((Collection) aVar);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ void a(AtomicBoolean atomicBoolean, ConcurrentMap concurrentMap, Object obj) {
            if (obj == null) {
                atomicBoolean.set(true);
            } else {
                concurrentMap.putIfAbsent(obj, Boolean.TRUE);
            }
        }

        @Override // java8.util.stream.a
        <P_IN> Spliterator<T> a(gb<T> gbVar, Spliterator<P_IN> spliterator) {
            if (gp.DISTINCT.a(gbVar.c())) {
                return gbVar.b(spliterator);
            }
            if (gp.ORDERED.a(gbVar.c())) {
                return b(gbVar, spliterator).f_();
            }
            return new gr.d(gbVar.b(spliterator));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // java8.util.stream.a
        public Sink<T> a(int i, Sink<T> sink) {
            Objects.requireNonNull(sink);
            if (gp.DISTINCT.a(i)) {
                return sink;
            }
            if (gp.SORTED.a(i)) {
                return new Sink.ChainedReference<T, T>(sink) { // from class: java8.util.stream.da.1.1
                    boolean a;
                    T b;

                    @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                    public void begin(long j) {
                        this.a = false;
                        this.b = null;
                        this.downstream.begin(-1L);
                    }

                    @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                    public void end() {
                        this.a = false;
                        this.b = null;
                        this.downstream.end();
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java8.util.function.Consumer
                    public void accept(T t) {
                        if (t != 0) {
                            Object obj = this.b;
                            if (obj == null || !t.equals(obj)) {
                                Sink sink2 = this.downstream;
                                this.b = t;
                                sink2.accept((Sink) t);
                            }
                        } else if (!this.a) {
                            this.a = true;
                            Sink sink3 = this.downstream;
                            this.b = null;
                            sink3.accept((Sink) null);
                        }
                    }
                };
            }
            return new Sink.ChainedReference<T, T>(sink) { // from class: java8.util.stream.da.1.2
                Set<T> a;

                @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                public void begin(long j) {
                    this.a = new HashSet();
                    this.downstream.begin(-1L);
                }

                @Override // java8.util.stream.Sink.ChainedReference, java8.util.stream.Sink
                public void end() {
                    this.a = null;
                    this.downstream.end();
                }

                @Override // java8.util.function.Consumer
                public void accept(T t) {
                    if (this.a.add(t)) {
                        this.downstream.accept((Sink) t);
                    }
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> gd<T, T> a(a<?, T, ?> aVar) {
        return new AnonymousClass1(aVar, gq.REFERENCE, gp.l | gp.s);
    }

    /* compiled from: DistinctOps.java */
    /* loaded from: classes5.dex */
    static final class a<E> extends AbstractSet<E> {
        final Set<E> a;
        final int b;

        a(Set<E> set, int i) {
            this.a = set;
            this.b = i + 1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<E> iterator() {
            return new Iterator<E>() { // from class: java8.util.stream.da.a.1
                boolean a = false;
                Iterator<E> b;

                {
                    this.b = a.this.a.iterator();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    if (!this.a) {
                        return true;
                    }
                    return this.b.hasNext();
                }

                @Override // java.util.Iterator
                public E next() {
                    if (this.a) {
                        return this.b.next();
                    }
                    this.a = true;
                    return null;
                }

                @Override // java.util.Iterator
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.b;
        }
    }
}
