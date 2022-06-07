package java8.util.concurrent;

import com.xiaomi.mico.settingslib.core.MicoSettings;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.util.Comparator;
import java.util.Random;
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
import java8.util.stream.StreamSupport;

/* loaded from: classes5.dex */
public class ThreadLocalRandom extends Random {
    private static final long serialVersionUID = 9123313859120073139L;
    private boolean initialized = true;
    private static final ObjectStreamField[] serialPersistentFields = {new ObjectStreamField("rnd", Long.TYPE), new ObjectStreamField(MicoSettings.Secure.KEY_INITIALIZED, Boolean.TYPE)};
    private static final ThreadLocal<Double> a = new ThreadLocal<>();
    private static final ThreadLocalRandom b = new ThreadLocalRandom();

    private ThreadLocalRandom() {
    }

    public static ThreadLocalRandom current() {
        if (c.f() == 0) {
            c.a();
        }
        return b;
    }

    @Override // java.util.Random
    public void setSeed(long j) {
        if (this.initialized) {
            throw new UnsupportedOperationException();
        }
    }

    private final long a() {
        return c.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final long a(long j, long j2) {
        long a2 = c.a(a());
        if (j >= j2) {
            return a2;
        }
        long j3 = j2 - j;
        long j4 = j3 - 1;
        if ((j3 & j4) == 0) {
            return (a2 & j4) + j;
        }
        if (j3 > 0) {
            while (true) {
                long j5 = a2 >>> 1;
                long j6 = j5 + j4;
                long j7 = j5 % j3;
                if (j6 - j7 >= 0) {
                    return j7 + j;
                }
                a2 = c.a(a());
            }
        } else {
            while (true) {
                if (a2 >= j && a2 < j2) {
                    return a2;
                }
                a2 = c.a(a());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int a(int i, int i2) {
        int b2 = c.b(a());
        if (i >= i2) {
            return b2;
        }
        int i3 = i2 - i;
        int i4 = i3 - 1;
        if ((i3 & i4) == 0) {
            return (b2 & i4) + i;
        }
        if (i3 > 0) {
            int i5 = b2 >>> 1;
            while (true) {
                int i6 = i5 + i4;
                int i7 = i5 % i3;
                if (i6 - i7 >= 0) {
                    return i7 + i;
                }
                i5 = c.b(a()) >>> 1;
            }
        } else {
            while (true) {
                if (b2 >= i && b2 < i2) {
                    return b2;
                }
                b2 = c.b(a());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final double a(double d, double d2) {
        double nextLong = (nextLong() >>> 11) * 1.1102230246251565E-16d;
        if (d >= d2) {
            return nextLong;
        }
        double d3 = (nextLong * (d2 - d)) + d;
        return d3 >= d2 ? Double.longBitsToDouble(Double.doubleToLongBits(d2) - 1) : d3;
    }

    @Override // java.util.Random
    public int nextInt() {
        return c.b(a());
    }

    @Override // java.util.Random
    public int nextInt(int i) {
        if (i > 0) {
            int b2 = c.b(a());
            int i2 = i - 1;
            if ((i & i2) == 0) {
                return b2 & i2;
            }
            while (true) {
                int i3 = b2 >>> 1;
                int i4 = i3 + i2;
                int i5 = i3 % i;
                if (i4 - i5 >= 0) {
                    return i5;
                }
                b2 = c.b(a());
            }
        } else {
            throw new IllegalArgumentException("bound must be positive");
        }
    }

    public int nextInt(int i, int i2) {
        if (i < i2) {
            return a(i, i2);
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    @Override // java.util.Random
    public long nextLong() {
        return c.a(a());
    }

    public long nextLong(long j) {
        if (j > 0) {
            long a2 = c.a(a());
            long j2 = j - 1;
            if ((j & j2) == 0) {
                return a2 & j2;
            }
            while (true) {
                long j3 = a2 >>> 1;
                long j4 = j3 + j2;
                long j5 = j3 % j;
                if (j4 - j5 >= 0) {
                    return j5;
                }
                a2 = c.a(a());
            }
        } else {
            throw new IllegalArgumentException("bound must be positive");
        }
    }

    public long nextLong(long j, long j2) {
        if (j < j2) {
            return a(j, j2);
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    @Override // java.util.Random
    public double nextDouble() {
        return (c.a(a()) >>> 11) * 1.1102230246251565E-16d;
    }

    public double nextDouble(double d) {
        if (d > 0.0d) {
            double a2 = (c.a(a()) >>> 11) * 1.1102230246251565E-16d * d;
            return a2 < d ? a2 : Double.longBitsToDouble(Double.doubleToLongBits(d) - 1);
        }
        throw new IllegalArgumentException("bound must be positive");
    }

    public double nextDouble(double d, double d2) {
        if (d < d2) {
            return a(d, d2);
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    @Override // java.util.Random
    public boolean nextBoolean() {
        return c.b(a()) < 0;
    }

    @Override // java.util.Random
    public float nextFloat() {
        return (c.b(a()) >>> 8) * 5.9604645E-8f;
    }

    @Override // java.util.Random
    public double nextGaussian() {
        Double d = a.get();
        if (d != null) {
            a.set(null);
            return d.doubleValue();
        }
        while (true) {
            double nextDouble = (nextDouble() * 2.0d) - 1.0d;
            double nextDouble2 = (nextDouble() * 2.0d) - 1.0d;
            double d2 = (nextDouble * nextDouble) + (nextDouble2 * nextDouble2);
            if (d2 < 1.0d && d2 != 0.0d) {
                double sqrt = StrictMath.sqrt((StrictMath.log(d2) * (-2.0d)) / d2);
                a.set(Double.valueOf(nextDouble2 * sqrt));
                return nextDouble * sqrt;
            }
        }
    }

    @Override // java.util.Random
    public IntStream ints(long j) {
        if (j >= 0) {
            return StreamSupport.intStream(new b(0L, j, Integer.MAX_VALUE, 0), false);
        }
        throw new IllegalArgumentException("size must be non-negative");
    }

    @Override // java.util.Random
    public IntStream ints() {
        return StreamSupport.intStream(new b(0L, Long.MAX_VALUE, Integer.MAX_VALUE, 0), false);
    }

    @Override // java.util.Random
    public IntStream ints(long j, int i, int i2) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        } else if (i < i2) {
            return StreamSupport.intStream(new b(0L, j, i, i2), false);
        } else {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
    }

    @Override // java.util.Random
    public IntStream ints(int i, int i2) {
        if (i < i2) {
            return StreamSupport.intStream(new b(0L, Long.MAX_VALUE, i, i2), false);
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    @Override // java.util.Random
    public LongStream longs(long j) {
        if (j >= 0) {
            return StreamSupport.longStream(new c(0L, j, Long.MAX_VALUE, 0L), false);
        }
        throw new IllegalArgumentException("size must be non-negative");
    }

    @Override // java.util.Random
    public LongStream longs() {
        return StreamSupport.longStream(new c(0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L), false);
    }

    @Override // java.util.Random
    public LongStream longs(long j, long j2, long j3) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        } else if (j2 < j3) {
            return StreamSupport.longStream(new c(0L, j, j2, j3), false);
        } else {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
    }

    @Override // java.util.Random
    public LongStream longs(long j, long j2) {
        if (j < j2) {
            return StreamSupport.longStream(new c(0L, Long.MAX_VALUE, j, j2), false);
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    @Override // java.util.Random
    public DoubleStream doubles(long j) {
        if (j >= 0) {
            return StreamSupport.doubleStream(new a(0L, j, Double.MAX_VALUE, 0.0d), false);
        }
        throw new IllegalArgumentException("size must be non-negative");
    }

    @Override // java.util.Random
    public DoubleStream doubles() {
        return StreamSupport.doubleStream(new a(0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0d), false);
    }

    @Override // java.util.Random
    public DoubleStream doubles(long j, double d, double d2) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        } else if (d < d2) {
            return StreamSupport.doubleStream(new a(0L, j, d, d2), false);
        } else {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
    }

    @Override // java.util.Random
    public DoubleStream doubles(double d, double d2) {
        if (d < d2) {
            return StreamSupport.doubleStream(new a(0L, Long.MAX_VALUE, d, d2), false);
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    /* loaded from: classes5.dex */
    private static final class b implements Spliterator.OfInt {
        long a;
        final long b;
        final int c;
        final int d;

        @Override // java8.util.Spliterator
        public int characteristics() {
            return 17728;
        }

        b(long j, long j2, int i, int i2) {
            this.a = j;
            this.b = j2;
            this.c = i;
            this.d = i2;
        }

        /* renamed from: a */
        public b trySplit() {
            long j = this.a;
            long j2 = (this.b + j) >>> 1;
            if (j2 <= j) {
                return null;
            }
            this.a = j2;
            return new b(j, j2, this.c, this.d);
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.b - this.a;
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
        public Comparator<? super Integer> getComparator() {
            return Spliterators.getComparator(this);
        }

        @Override // java8.util.Spliterator.OfInt
        public boolean tryAdvance(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            long j = this.a;
            if (j >= this.b) {
                return false;
            }
            intConsumer.accept(ThreadLocalRandom.current().a(this.c, this.d));
            this.a = j + 1;
            return true;
        }

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Integer> consumer) {
            return Spliterators.OfInt.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator.OfInt
        public void forEachRemaining(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            long j = this.a;
            long j2 = this.b;
            if (j < j2) {
                this.a = j2;
                int i = this.c;
                int i2 = this.d;
                ThreadLocalRandom current = ThreadLocalRandom.current();
                do {
                    intConsumer.accept(current.a(i, i2));
                    j++;
                } while (j < j2);
            }
        }

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Integer> consumer) {
            Spliterators.OfInt.forEachRemaining(this, consumer);
        }
    }

    /* loaded from: classes5.dex */
    private static final class c implements Spliterator.OfLong {
        long a;
        final long b;
        final long c;
        final long d;

        @Override // java8.util.Spliterator
        public int characteristics() {
            return 17728;
        }

        c(long j, long j2, long j3, long j4) {
            this.a = j;
            this.b = j2;
            this.c = j3;
            this.d = j4;
        }

        /* renamed from: a */
        public c trySplit() {
            long j = this.a;
            long j2 = (this.b + j) >>> 1;
            if (j2 <= j) {
                return null;
            }
            this.a = j2;
            return new c(j, j2, this.c, this.d);
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.b - this.a;
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
        public Comparator<? super Long> getComparator() {
            return Spliterators.getComparator(this);
        }

        @Override // java8.util.Spliterator.OfLong
        public boolean tryAdvance(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer);
            long j = this.a;
            if (j >= this.b) {
                return false;
            }
            longConsumer.accept(ThreadLocalRandom.current().a(this.c, this.d));
            this.a = j + 1;
            return true;
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
            if (j < j2) {
                this.a = j2;
                long j3 = this.c;
                long j4 = this.d;
                ThreadLocalRandom current = ThreadLocalRandom.current();
                do {
                    longConsumer.accept(current.a(j3, j4));
                    j++;
                } while (j < j2);
            }
        }

        @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Long> consumer) {
            Spliterators.OfLong.forEachRemaining(this, consumer);
        }
    }

    /* loaded from: classes5.dex */
    private static final class a implements Spliterator.OfDouble {
        long a;
        final long b;
        final double c;
        final double d;

        @Override // java8.util.Spliterator
        public int characteristics() {
            return 17728;
        }

        a(long j, long j2, double d, double d2) {
            this.a = j;
            this.b = j2;
            this.c = d;
            this.d = d2;
        }

        /* renamed from: a */
        public a trySplit() {
            long j = this.a;
            long j2 = (this.b + j) >>> 1;
            if (j2 <= j) {
                return null;
            }
            this.a = j2;
            return new a(j, j2, this.c, this.d);
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.b - this.a;
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
        public Comparator<? super Double> getComparator() {
            return Spliterators.getComparator(this);
        }

        @Override // java8.util.Spliterator.OfDouble
        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            Objects.requireNonNull(doubleConsumer);
            long j = this.a;
            if (j >= this.b) {
                return false;
            }
            doubleConsumer.accept(ThreadLocalRandom.current().a(this.c, this.d));
            this.a = j + 1;
            return true;
        }

        @Override // java8.util.Spliterator.OfDouble, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Double> consumer) {
            return Spliterators.OfDouble.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator.OfDouble
        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            Objects.requireNonNull(doubleConsumer);
            long j = this.a;
            long j2 = this.b;
            if (j < j2) {
                this.a = j2;
                double d = this.c;
                double d2 = this.d;
                ThreadLocalRandom current = ThreadLocalRandom.current();
                do {
                    doubleConsumer.accept(current.a(d, d2));
                    j++;
                } while (j < j2);
            }
        }

        @Override // java8.util.Spliterator.OfDouble, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Double> consumer) {
            Spliterators.OfDouble.forEachRemaining(this, consumer);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        putFields.put("rnd", c.e());
        putFields.put(MicoSettings.Secure.KEY_INITIALIZED, true);
        objectOutputStream.writeFields();
    }

    private Object readResolve() {
        return current();
    }
}
