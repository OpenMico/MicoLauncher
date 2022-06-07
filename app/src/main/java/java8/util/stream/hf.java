package java8.util.stream;

import java.util.Comparator;
import java8.util.Objects;
import java8.util.Spliterator;
import java8.util.Spliterators;
import java8.util.function.Consumer;
import java8.util.function.DoubleConsumer;
import java8.util.function.IntConsumer;
import java8.util.function.LongConsumer;
import java8.util.stream.DoubleStream;
import java8.util.stream.IntStream;
import java8.util.stream.LongStream;
import java8.util.stream.Stream;
import java8.util.stream.gn;

/* compiled from: Streams.java */
/* loaded from: classes5.dex */
public final class hf {

    /* compiled from: Streams.java */
    /* loaded from: classes5.dex */
    public static final class f implements Spliterator.OfInt {
        private int a;
        private final int b;
        private int c;

        @Override // java8.util.Spliterator
        public int characteristics() {
            return 17749;
        }

        @Override // java8.util.Spliterator
        public Comparator<? super Integer> getComparator() {
            return null;
        }

        public f(int i, int i2, boolean z) {
            this(i, i2, z ? 1 : 0);
        }

        private f(int i, int i2, int i3) {
            this.a = i;
            this.b = i2;
            this.c = i3;
        }

        @Override // java8.util.Spliterator.OfInt
        public boolean tryAdvance(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            int i = this.a;
            if (i < this.b) {
                this.a = i + 1;
                intConsumer.accept(i);
                return true;
            } else if (this.c <= 0) {
                return false;
            } else {
                this.c = 0;
                intConsumer.accept(i);
                return true;
            }
        }

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Integer> consumer) {
            return Spliterators.OfInt.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator.OfInt
        public void forEachRemaining(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            int i = this.a;
            int i2 = this.b;
            int i3 = this.c;
            this.a = i2;
            this.c = 0;
            while (i < i2) {
                i++;
                intConsumer.accept(i);
            }
            if (i3 > 0) {
                intConsumer.accept(i);
            }
        }

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Integer> consumer) {
            Spliterators.OfInt.forEachRemaining(this, consumer);
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return (this.b - this.a) + this.c;
        }

        @Override // java8.util.Spliterator
        public long getExactSizeIfKnown() {
            return Spliterators.getExactSizeIfKnown(this);
        }

        @Override // java8.util.Spliterator
        public boolean hasCharacteristics(int i) {
            return Spliterators.hasCharacteristics(this, i);
        }

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
        public Spliterator.OfInt trySplit() {
            long estimateSize = estimateSize();
            if (estimateSize <= 1) {
                return null;
            }
            int i = this.a;
            int a = a(estimateSize) + i;
            this.a = a;
            return new f(i, a, 0);
        }

        private int a(long j) {
            return (int) (j / (j < 16777216 ? 2 : 8));
        }
    }

    /* compiled from: Streams.java */
    /* loaded from: classes5.dex */
    public static final class g implements Spliterator.OfLong {
        private long a;
        private final long b;
        private int c;

        @Override // java8.util.Spliterator
        public int characteristics() {
            return 17749;
        }

        @Override // java8.util.Spliterator
        public Comparator<? super Long> getComparator() {
            return null;
        }

        public g(long j, long j2, boolean z) {
            this(j, j2, z ? 1 : 0);
        }

        private g(long j, long j2, int i) {
            this.a = j;
            this.b = j2;
            this.c = i;
        }

        @Override // java8.util.Spliterator.OfLong
        public boolean tryAdvance(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer);
            long j = this.a;
            if (j < this.b) {
                this.a = 1 + j;
                longConsumer.accept(j);
                return true;
            } else if (this.c <= 0) {
                return false;
            } else {
                this.c = 0;
                longConsumer.accept(j);
                return true;
            }
        }

        @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Long> consumer) {
            return Spliterators.OfLong.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator.OfLong
        public void forEachRemaining(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer);
            long j = this.a;
            long j2 = this.b;
            int i = this.c;
            this.a = j2;
            this.c = 0;
            while (j < j2) {
                j = 1 + j;
                longConsumer.accept(j);
            }
            if (i > 0) {
                longConsumer.accept(j);
            }
        }

        @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Long> consumer) {
            Spliterators.OfLong.forEachRemaining(this, consumer);
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return (this.b - this.a) + this.c;
        }

        @Override // java8.util.Spliterator
        public long getExactSizeIfKnown() {
            return Spliterators.getExactSizeIfKnown(this);
        }

        @Override // java8.util.Spliterator
        public boolean hasCharacteristics(int i) {
            return Spliterators.hasCharacteristics(this, i);
        }

        @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
        public Spliterator.OfLong trySplit() {
            long estimateSize = estimateSize();
            if (estimateSize <= 1) {
                return null;
            }
            long j = this.a;
            long a = j + a(estimateSize);
            this.a = a;
            return new g(j, a, 0);
        }

        private long a(long j) {
            return j / (j < 16777216 ? 2L : 8L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Streams.java */
    /* loaded from: classes5.dex */
    public static abstract class a<T, S extends Spliterator<T>> implements Spliterator<T> {
        int a;

        @Override // java8.util.Spliterator
        public int characteristics() {
            return 17488;
        }

        @Override // java8.util.Spliterator
        public S trySplit() {
            return null;
        }

        private a() {
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return (-this.a) - 1;
        }

        @Override // java8.util.Spliterator
        public long getExactSizeIfKnown() {
            return Spliterators.getExactSizeIfKnown(this);
        }

        @Override // java8.util.Spliterator
        public boolean hasCharacteristics(int i) {
            return Spliterators.hasCharacteristics(this, i);
        }

        @Override // java8.util.Spliterator
        public Comparator<? super T> getComparator() {
            return Spliterators.getComparator(this);
        }
    }

    /* compiled from: Streams.java */
    /* loaded from: classes5.dex */
    public static final class h<T> extends a<T, Spliterator<T>> implements Stream.Builder<T> {
        T b;
        gn<T> c;

        public h() {
            super();
        }

        public h(T t) {
            super();
            this.b = t;
            this.a = -2;
        }

        @Override // java8.util.stream.Stream.Builder, java8.util.function.Consumer
        public void accept(T t) {
            if (this.a == 0) {
                this.b = t;
                this.a++;
            } else if (this.a > 0) {
                if (this.c == null) {
                    this.c = new gn<>();
                    this.c.accept(this.b);
                    this.a++;
                }
                this.c.accept(t);
            } else {
                throw new IllegalStateException();
            }
        }

        @Override // java8.util.stream.Stream.Builder
        public Stream.Builder<T> add(T t) {
            accept(t);
            return this;
        }

        @Override // java8.util.stream.Stream.Builder
        public Stream<T> build() {
            int i = this.a;
            if (i >= 0) {
                this.a = (-this.a) - 1;
                return i < 2 ? StreamSupport.stream((Spliterator) this, false) : StreamSupport.stream((Spliterator) this.c.f_(), false);
            }
            throw new IllegalStateException();
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            if (this.a != -2) {
                return false;
            }
            consumer.accept((T) this.b);
            this.a = -1;
            return true;
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            if (this.a == -2) {
                consumer.accept((T) this.b);
                this.a = -1;
            }
        }
    }

    /* compiled from: Streams.java */
    /* loaded from: classes5.dex */
    public static final class d extends a<Integer, Spliterator.OfInt> implements Spliterator.OfInt, IntStream.Builder {
        int b;
        gn.c c;

        public d() {
            super();
        }

        public d(int i) {
            super();
            this.b = i;
            this.a = -2;
        }

        @Override // java8.util.stream.IntStream.Builder, java8.util.function.IntConsumer
        public void accept(int i) {
            if (this.a == 0) {
                this.b = i;
                this.a++;
            } else if (this.a > 0) {
                if (this.c == null) {
                    this.c = new gn.c();
                    this.c.accept(this.b);
                    this.a++;
                }
                this.c.accept(i);
            } else {
                throw new IllegalStateException();
            }
        }

        @Override // java8.util.stream.IntStream.Builder
        public IntStream.Builder add(int i) {
            accept(i);
            return this;
        }

        @Override // java8.util.stream.IntStream.Builder
        public IntStream build() {
            int i = this.a;
            if (i >= 0) {
                this.a = (-this.a) - 1;
                return i < 2 ? StreamSupport.intStream(this, false) : StreamSupport.intStream(this.c.d(), false);
            }
            throw new IllegalStateException();
        }

        @Override // java8.util.Spliterator.OfInt
        public boolean tryAdvance(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            if (this.a != -2) {
                return false;
            }
            intConsumer.accept(this.b);
            this.a = -1;
            return true;
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Integer> consumer) {
            return Spliterators.OfInt.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator.OfInt
        public void forEachRemaining(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            if (this.a == -2) {
                intConsumer.accept(this.b);
                this.a = -1;
            }
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Integer> consumer) {
            Spliterators.OfInt.forEachRemaining(this, consumer);
        }
    }

    /* compiled from: Streams.java */
    /* loaded from: classes5.dex */
    public static final class e extends a<Long, Spliterator.OfLong> implements Spliterator.OfLong, LongStream.Builder {
        long b;
        gn.d c;

        public e() {
            super();
        }

        public e(long j) {
            super();
            this.b = j;
            this.a = -2;
        }

        @Override // java8.util.stream.LongStream.Builder, java8.util.function.LongConsumer
        public void accept(long j) {
            if (this.a == 0) {
                this.b = j;
                this.a++;
            } else if (this.a > 0) {
                if (this.c == null) {
                    this.c = new gn.d();
                    this.c.accept(this.b);
                    this.a++;
                }
                this.c.accept(j);
            } else {
                throw new IllegalStateException();
            }
        }

        @Override // java8.util.stream.LongStream.Builder
        public LongStream.Builder add(long j) {
            accept(j);
            return this;
        }

        @Override // java8.util.stream.LongStream.Builder
        public LongStream build() {
            int i = this.a;
            if (i >= 0) {
                this.a = (-this.a) - 1;
                return i < 2 ? StreamSupport.longStream(this, false) : StreamSupport.longStream(this.c.d(), false);
            }
            throw new IllegalStateException();
        }

        @Override // java8.util.Spliterator.OfLong
        public boolean tryAdvance(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer);
            if (this.a != -2) {
                return false;
            }
            longConsumer.accept(this.b);
            this.a = -1;
            return true;
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Long> consumer) {
            return Spliterators.OfLong.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator.OfLong
        public void forEachRemaining(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer);
            if (this.a == -2) {
                longConsumer.accept(this.b);
                this.a = -1;
            }
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Long> consumer) {
            Spliterators.OfLong.forEachRemaining(this, consumer);
        }
    }

    /* compiled from: Streams.java */
    /* loaded from: classes5.dex */
    public static final class c extends a<Double, Spliterator.OfDouble> implements Spliterator.OfDouble, DoubleStream.Builder {
        double b;
        gn.b c;

        public c() {
            super();
        }

        public c(double d) {
            super();
            this.b = d;
            this.a = -2;
        }

        @Override // java8.util.stream.DoubleStream.Builder, java8.util.function.DoubleConsumer
        public void accept(double d) {
            if (this.a == 0) {
                this.b = d;
                this.a++;
            } else if (this.a > 0) {
                if (this.c == null) {
                    this.c = new gn.b();
                    this.c.accept(this.b);
                    this.a++;
                }
                this.c.accept(d);
            } else {
                throw new IllegalStateException();
            }
        }

        @Override // java8.util.stream.DoubleStream.Builder
        public DoubleStream.Builder add(double d) {
            accept(d);
            return this;
        }

        @Override // java8.util.stream.DoubleStream.Builder
        public DoubleStream build() {
            int i = this.a;
            if (i >= 0) {
                this.a = (-this.a) - 1;
                return i < 2 ? StreamSupport.doubleStream(this, false) : StreamSupport.doubleStream(this.c.d(), false);
            }
            throw new IllegalStateException();
        }

        @Override // java8.util.Spliterator.OfDouble
        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            Objects.requireNonNull(doubleConsumer);
            if (this.a != -2) {
                return false;
            }
            doubleConsumer.accept(this.b);
            this.a = -1;
            return true;
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Double> consumer) {
            return Spliterators.OfDouble.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator.OfDouble
        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            Objects.requireNonNull(doubleConsumer);
            if (this.a == -2) {
                doubleConsumer.accept(this.b);
                this.a = -1;
            }
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Double> consumer) {
            Spliterators.OfDouble.forEachRemaining(this, consumer);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Streams.java */
    /* loaded from: classes5.dex */
    public static abstract class b<T, T_SPLITR extends Spliterator<T>> implements Spliterator<T> {
        protected final T_SPLITR a;
        protected final T_SPLITR b;
        boolean c = true;
        final boolean d;

        public b(T_SPLITR t_splitr, T_SPLITR t_splitr2) {
            this.a = t_splitr;
            this.b = t_splitr2;
            boolean z = true;
            this.d = t_splitr.estimateSize() + t_splitr2.estimateSize() >= 0 ? false : z;
        }

        @Override // java8.util.Spliterator
        public T_SPLITR trySplit() {
            T_SPLITR t_splitr = this.c ? this.a : (T_SPLITR) this.b.trySplit();
            this.c = false;
            return t_splitr;
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super T> consumer) {
            if (!this.c) {
                return this.b.tryAdvance(consumer);
            }
            boolean tryAdvance = this.a.tryAdvance(consumer);
            if (tryAdvance) {
                return tryAdvance;
            }
            this.c = false;
            return this.b.tryAdvance(consumer);
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super T> consumer) {
            if (this.c) {
                this.a.forEachRemaining(consumer);
            }
            this.b.forEachRemaining(consumer);
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            if (!this.c) {
                return this.b.estimateSize();
            }
            long estimateSize = this.a.estimateSize() + this.b.estimateSize();
            if (estimateSize >= 0) {
                return estimateSize;
            }
            return Long.MAX_VALUE;
        }

        @Override // java8.util.Spliterator
        public int characteristics() {
            if (!this.c) {
                return this.b.characteristics();
            }
            return this.a.characteristics() & this.b.characteristics() & (~((this.d ? 16448 : 0) | 5));
        }

        @Override // java8.util.Spliterator
        public Comparator<? super T> getComparator() {
            if (!this.c) {
                return this.b.getComparator();
            }
            throw new IllegalStateException();
        }

        /* compiled from: Streams.java */
        /* loaded from: classes5.dex */
        static class e<T> extends b<T, Spliterator<T>> {
            public e(Spliterator<T> spliterator, Spliterator<T> spliterator2) {
                super(spliterator, spliterator2);
            }

            @Override // java8.util.Spliterator
            public long getExactSizeIfKnown() {
                return Spliterators.getExactSizeIfKnown(this);
            }

            @Override // java8.util.Spliterator
            public boolean hasCharacteristics(int i) {
                return Spliterators.hasCharacteristics(this, i);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* compiled from: Streams.java */
        /* loaded from: classes5.dex */
        public static abstract class d<T, T_CONS, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>> extends b<T, T_SPLITR> implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR> {
            @Override // java8.util.stream.hf.b, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            private d(T_SPLITR t_splitr, T_SPLITR t_splitr2) {
                super(t_splitr, t_splitr2);
            }

            @Override // java8.util.Spliterator.OfPrimitive
            public boolean tryAdvance(T_CONS t_cons) {
                if (!this.c) {
                    return ((Spliterator.OfPrimitive) this.b).tryAdvance((Spliterator.OfPrimitive) t_cons);
                }
                boolean tryAdvance = ((Spliterator.OfPrimitive) this.a).tryAdvance((Spliterator.OfPrimitive) t_cons);
                if (tryAdvance) {
                    return tryAdvance;
                }
                this.c = false;
                return ((Spliterator.OfPrimitive) this.b).tryAdvance((Spliterator.OfPrimitive) t_cons);
            }

            @Override // java8.util.Spliterator.OfPrimitive
            public void forEachRemaining(T_CONS t_cons) {
                if (this.c) {
                    ((Spliterator.OfPrimitive) this.a).forEachRemaining((Spliterator.OfPrimitive) t_cons);
                }
                ((Spliterator.OfPrimitive) this.b).forEachRemaining((Spliterator.OfPrimitive) t_cons);
            }

            @Override // java8.util.Spliterator
            public long getExactSizeIfKnown() {
                return Spliterators.getExactSizeIfKnown(this);
            }

            @Override // java8.util.Spliterator
            public boolean hasCharacteristics(int i) {
                return Spliterators.hasCharacteristics(this, i);
            }
        }

        /* compiled from: Streams.java */
        /* renamed from: java8.util.stream.hf$b$b */
        /* loaded from: classes5.dex */
        static class C0362b extends d<Integer, IntConsumer, Spliterator.OfInt> implements Spliterator.OfInt {
            @Override // java8.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                super.forEachRemaining((C0362b) intConsumer);
            }

            @Override // java8.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
                return super.tryAdvance((C0362b) intConsumer);
            }

            @Override // java8.util.stream.hf.b.d, java8.util.stream.hf.b, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
                return (Spliterator.OfInt) super.trySplit();
            }

            public C0362b(Spliterator.OfInt ofInt, Spliterator.OfInt ofInt2) {
                super(ofInt, ofInt2);
            }
        }

        /* compiled from: Streams.java */
        /* loaded from: classes5.dex */
        public static class c extends d<Long, LongConsumer, Spliterator.OfLong> implements Spliterator.OfLong {
            @Override // java8.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                super.forEachRemaining((c) longConsumer);
            }

            @Override // java8.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
                return super.tryAdvance((c) longConsumer);
            }

            @Override // java8.util.stream.hf.b.d, java8.util.stream.hf.b, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
                return (Spliterator.OfLong) super.trySplit();
            }

            public c(Spliterator.OfLong ofLong, Spliterator.OfLong ofLong2) {
                super(ofLong, ofLong2);
            }
        }

        /* compiled from: Streams.java */
        /* loaded from: classes5.dex */
        static class a extends d<Double, DoubleConsumer, Spliterator.OfDouble> implements Spliterator.OfDouble {
            @Override // java8.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                super.forEachRemaining((a) doubleConsumer);
            }

            @Override // java8.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
                return super.tryAdvance((a) doubleConsumer);
            }

            @Override // java8.util.stream.hf.b.d, java8.util.stream.hf.b, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
                return (Spliterator.OfDouble) super.trySplit();
            }

            public a(Spliterator.OfDouble ofDouble, Spliterator.OfDouble ofDouble2) {
                super(ofDouble, ofDouble2);
            }
        }
    }

    public static Runnable a(final Runnable runnable, final Runnable runnable2) {
        return new Runnable() { // from class: java8.util.stream.hf.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    runnable.run();
                    runnable2.run();
                } catch (Throwable th) {
                    try {
                        runnable2.run();
                    } catch (Throwable unused) {
                    }
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    } else if (th instanceof Error) {
                        throw ((Error) th);
                    } else {
                        throw new IllegalStateException(th);
                    }
                }
            }
        };
    }

    public static Runnable a(final BaseStream<?, ?> baseStream, final BaseStream<?, ?> baseStream2) {
        return new Runnable() { // from class: java8.util.stream.hf.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    baseStream.close();
                    baseStream2.close();
                } catch (Throwable th) {
                    try {
                        baseStream2.close();
                    } catch (Throwable unused) {
                    }
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    } else if (th instanceof Error) {
                        throw ((Error) th);
                    } else {
                        throw new IllegalStateException(th);
                    }
                }
            }
        };
    }
}
